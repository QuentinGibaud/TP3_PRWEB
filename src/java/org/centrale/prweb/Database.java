/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centrale.prweb;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
// import org.apache.tomcat.dbcp.dbcp.BasicDataSource;	// TOMCAT 7
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;	// TOMCAT 8 +

/**
 *
 * @author Kwyhr
 */
public class Database {

    private static BasicDataSource connectionDataSource;
    private static String dbURL;

    /**
     * Get a String configuration from the resource file
     *
     * @param res
     * @param element
     * @param defaultValue
     * @return
     */
    private static String getResourceElement(ResourceBundle res, String element, String defaultValue) {
        String newValue;
        String returnValue;

        returnValue = defaultValue;
        if (res != null) {
            try {
                newValue = res.getString(element);
                if (!newValue.equals("")) {
                    returnValue = newValue;
                }
            } catch (Exception e) {
            }
        }
        return returnValue;
    }

    /**
     * Initialize database informations. Create a datasource.
     *
     * @throws javax.servlet.ServletException
     */
    private static void init() {
        try {
            // get Resource file
            ResourceBundle res = ResourceBundle.getBundle(Database.class.getPackage().getName() + ".databaseConfig");

            // Create datasource
            connectionDataSource = new BasicDataSource();

            // Load variables
            String dbDriver = getResourceElement(res, "driver", "org.postgresql.Driver");
            connectionDataSource.setDriverClassName(dbDriver);

            dbURL = getResourceElement(res, "url", "jdbc:postgresql://127.0.0.1/prweb");
            connectionDataSource.setUrl(dbURL);

            String dbUser = getResourceElement(res, "dbuser", "root");
            connectionDataSource.setUsername(dbUser);
            String dbPass = getResourceElement(res, "dbpass", "");
            if (!dbPass.equals("")) {
                connectionDataSource.setPassword(dbPass);
            }

            // Define datasource pool
            // TOMCAT 7
//            connectionDataSource.setMaxActive(20);
            // TOMCAT 8
            connectionDataSource.setMaxTotal(20);
            connectionDataSource.setMaxIdle(2);

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.INFO, null, ex);
            connectionDataSource = null;
        }
    }

    /**
     * Close connections
     */
    public static void closeAllConnections() {
        if (connectionDataSource != null) {
            try {
                connectionDataSource.close();
                connectionDataSource = null;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Then release driver
        if (dbURL != null) {
            try {
                Driver theDriver = DriverManager.getDriver(dbURL);
                DriverManager.deregisterDriver(theDriver);
                dbURL = null;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * get connection
     *
     * @return
     */
    public static Connection getConnection() {
        if (connectionDataSource == null) {
            // If not initialzed, get informations
            init();
        }

        try {
            // Get connection
            return connectionDataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * saveUser method : add a user to the database
     *
     * @param request
     * @return : connection id
     */
    public static int saveUser(HttpServletRequest request) {
        int res = 0;
        String login = request.getParameter("login");
        String mdp = request.getParameter("password");
        if ((login != null) && (mdp != null)) {
            HashMap<String, String> infos = LDAP.searchLDAP(login, mdp);
            String uid = infos.get("uid");
            String email = infos.get("mail");
            try {
                Database.init();
                //Connection conn = DriverManager.getConnection(connectionDataSource.getUrl(), connectionDataSource.getUsername(), connectionDataSource.getPassword());
                Connection conn = getConnection();
                String query = "INSERT INTO connexion(login, email) VALUES (?,?)";
                PreparedStatement theStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                theStmt.setString(1, uid);
                theStmt.setString(2, email);
                theStmt.executeUpdate();
                ResultSet theRS = theStmt.getGeneratedKeys();
                if (theRS.next()) {
                    res = theRS.getInt(1);
                }
                theRS.close();
                theStmt.close();
                closeAllConnections();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    /**
     * removeUser method remove a user from the database
     *
     * @param connexionId : coonnection id of the user
     */
    public static void removeUser(int connexionId) {
        try {
            Database.init();
            Connection conn = getConnection();
            String query = "DELETE FROM connexion WHERE connexion_id=?";
            PreparedStatement theStmt = conn.prepareStatement(query);
            theStmt.setInt(1, connexionId);
            theStmt.executeUpdate();
            theStmt.close();
            closeAllConnections();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkId(HttpServletRequest request) {
        int idToCheck = Integer.parseInt(request.getParameter("connexionId"));
        boolean res = false;
        try {
            Database.init();
            Connection conn = getConnection();
            String query = "SELECT * FROM connexion WHERE connexion_id=?";
            PreparedStatement theStmt = conn.prepareStatement(query);
            theStmt.setInt(1, idToCheck);
            ResultSet theRS = theStmt.executeQuery();
            if (theRS.next()) {
                res = true;
            }
            theStmt.close();
            closeAllConnections();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
