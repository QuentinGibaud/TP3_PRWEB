<%-- 
    Document   : iframe
    Created on : 13 févr. 2018, 18:27:22
    Author     : Quentin GIBAUD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>test HTML</title>
    </head>
    <body>
        <%@page import="org.centrale.prweb.Utilities" %> 
        <p>Vous venez à l'instant de cliquer sur l'un des éléments du navigateur !!! Il s'agit de ... :</p>
        <%=Utilities.getMessageInFrame(request)%>
    </body>
</html>
