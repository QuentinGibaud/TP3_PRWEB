/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centrale.prweb;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class Utilities
 * Include static methods for the TP
 * @author Quentin GIBAUD
 */
public class Utilities {
    
    /**
     * launchJSP method
     * send a .jsp page
     * @param request 
     * @param response 
     * @param page : the page to send
     */
    public static void launchJSP(HttpServletRequest request, HttpServletResponse response, String page){
        try {
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                response.setHeader("Content-Type", "text/html;charset=UTF-8");
                dispatcher.include(request, response);
            } catch (IOException | ServletException ex) {
                Logger.getLogger(Identify.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    /**
     * getEmail method
     * get the e-mail of the connected person
     * @param request
     * @return mail of the person who is connected with the login and password
     */
    public static String getEmail(HttpServletRequest request) {
        String email = "";
        String login = request.getParameter("login");
        String mdp = request.getParameter("password");
        if ((login !=null) && (mdp != null)){
            HashMap<String, String> infos = LDAP.searchLDAP(login, mdp);
            email = infos.get("mail");
        }
        return email;
    }
    
    /**
     * getMessageInFrame method
     * Return the rest of the message to print in iframe.jsp depending on the value
     * @param request
     * @return
     */
    public static String getMessageInFrame(HttpServletRequest request) {
        String val = request.getParameter("value");
        switch (val) {
            case "economie" :
                return "La page Economie !!!";
            case "finance" :
                return "La page Finance !!!";
            case "sciences" :
                return "La page des Sciences !!!";
            case "marine" :
                return "La page sur la Marine";
            case "da_inquisition" :
                return "Le fameux jeu DA Inquisition !!!";
            case "skyrim" :
                return "Le fameux jeu Skyrim !!!";
            case "the_witcher" :
                return "Le fameux jeu The Witcher !!!";
            case "dota2" :
                return "Le fameux jeu Dota 2 !!!";
            default :
                return "...Ben vous vous êtes planté où j'ai mal fait mon boulot ...";
        }    
    }
    
}
