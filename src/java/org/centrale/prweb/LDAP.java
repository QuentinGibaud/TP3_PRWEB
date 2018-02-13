/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.centrale.prweb;

import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author kwyhr
 */
public class LDAP {

    private static String ldapBasedn;
    private static String ldapHost;
    private static String ldapsecurityprotocol;

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
     * Get a String configuration from the resource file
     *
     * @param res
     * @param element
     * @return
     */
    private static String getResourceElement(ResourceBundle res, String element) {
        return getResourceElement(res, element, "");
    }

    private static void init() {
        if (ldapBasedn == null) {
            try {
                ResourceBundle parametre = ResourceBundle.getBundle(LDAP.class.getPackage().getName() + ".ldap");
                // USE config parameters
                ldapHost = getResourceElement(parametre, "ldapHost");
                ldapBasedn = getResourceElement(parametre, "ldapBasedn");
                ldapsecurityprotocol = getResourceElement(parametre, "ldapsecurityprotocol");
            } catch (Exception ex) {
                Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Build LDAP properties for a BIND
     *
     * @param field
     * @param fieldContent
     * @param password
     * @return
     */
    private static Properties getLDAPProperties(String field, String fieldContent, String password) {
        Properties env = new Properties();
        if (ldapHost == null) {
            // Base informations not loaded
            init();
        }

        String packageName = LDAP.class.getPackage().getName();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        if (fieldContent != null) {
            env.put(Context.SECURITY_PRINCIPAL, field + "=" + fieldContent + "," + ldapBasedn);
            env.put(Context.SECURITY_CREDENTIALS, password);
            if (!ldapsecurityprotocol.equals("")) {
                env.put(Context.SECURITY_PROTOCOL, ldapsecurityprotocol);
                env.put("java.naming.ldap.factory.socket", packageName + ".MySSLSocketFactory");
            }
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
        } else {
            env.put(Context.SECURITY_AUTHENTICATION, "none");
            if (!ldapsecurityprotocol.equals("")) {
                env.put(Context.SECURITY_PROTOCOL, ldapsecurityprotocol);
                env.put("java.naming.ldap.factory.socket", packageName + ".MySSLSocketFactory");
            }
        }
        return env;
    }

    /**
     * Identify with a LDAP BIND
     *
     * @param login
     * @param password
     * @return
     */
    public static boolean identifyLDAPUID(String login, String password) {
        boolean isIdentified = false;
        if ((login != null) && (password != null) && (!login.equals("")) && (!password.equals(""))) {
            try {
                Properties env = getLDAPProperties("uid", login, password);
                DirContext ctx = new InitialDirContext(env);
                isIdentified = true;
                ctx.close();

            } catch (CommunicationException ex) {
                Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AuthenticationException ex) {
                // Non reconnu
                Logger.getLogger(LDAP.class.getName()).log(Level.INFO, null, ex);
            } catch (NamingException ex) {
                // Non reconnu
                Logger.getLogger(LDAP.class.getName()).log(Level.INFO, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isIdentified;
    }

    /**
     * Search in LDAP
     *
     * @param login
     * @param password
     * @return
     */
    public static HashMap<String, String> searchLDAP(String login, String password) {
        HashMap<String, String> result = new HashMap<String, String>();
        if ((login != null) && (password != null) && (!login.equals("")) && (!password.equals(""))) {
            try {
                Properties env;
                env = getLDAPProperties("uid", login, password);
                DirContext ctx = new InitialDirContext(env);

                StringBuilder searchConstraint = new StringBuilder();
                searchConstraint.append("(uid=");
                searchConstraint.append(login);
                searchConstraint.append(")");

                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
                constraints.setCountLimit(1);
                NamingEnumeration results = ctx.search(ldapBasedn, searchConstraint.toString(), constraints);
                if (results != null) {
                    if (results.hasMore()) {
                        // Get all attributes
                        SearchResult si = (SearchResult) (results.next());
                        Attributes attrs = si.getAttributes();
                        NamingEnumeration<? extends Attribute> theAttributes = attrs.getAll();

                        // On passe en revue
                        while (theAttributes.hasMore()) {
                            Attribute anAttribute = theAttributes.next();

                            String infoName = anAttribute.getID();
                            StringBuilder informations = new StringBuilder();
                            if (anAttribute.size() == 1) {
                                informations.append(anAttribute.get());
                            } else {
                                NamingEnumeration theValues = anAttribute.getAll();
                                int i = 0;
                                while (theValues.hasMore()) {
                                    if (i > 0) {
                                        informations.append(" + ");
                                    }
                                    informations.append(theValues.next());
                                    i++;
                                }
                            }
                            result.put(infoName, informations.toString());
                        }
                    }
                }
                ctx.close();
            } catch (CommunicationException ex) {
                Logger.getLogger(LDAP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AuthenticationException ex) {
                // Non reconnu
                Logger.getLogger(LDAP.class.getName()).log(Level.INFO, null, ex);
            } catch (NamingException ex) {
                // Non reconnu
                Logger.getLogger(LDAP.class.getName()).log(Level.INFO, null, ex);
            } catch (Exception ex) {
                // Non reconnu
                Logger.getLogger(LDAP.class.getName()).log(Level.INFO, null, ex);
            }
        }
        return result;
    }
}
