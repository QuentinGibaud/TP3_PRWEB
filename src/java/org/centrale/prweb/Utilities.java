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
 * Contient les méthodes statiques utiles pour ce TP
 * @author Quentin GIBAUD
 */
public class Utilities {
    
    /**
     * Méthode launchJSP
     * Permet de renvoyer une page.jsp
     * @param request : la requête envoyé
     * @param response : la réponse à envoyer au serveur
     * @param page : la page à afficher
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
     * Méthode getEmail
     * Renvoie l'e-mail de la personne qui s'est connecté
     * @param request
     * @return e-mail de la personne qui s'est connecté avec le login et le mot de passe
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
}
