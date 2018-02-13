<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr-fr">
    <head>
        <title>un site web</title>
        <link href="style.css" type="text/css" rel="stylesheet" />
        <meta charset="UTF-8" />
        <script src="outils.js" type="text/javascript"></script>
    </head>
    <body>
        <%@page import="org.centrale.prweb.Utilities" %> 
        <div id="header">
            <div id="imageHeader">
                <img src="ECN.jpg" alt="ECN Logo" height="75" />
            </div>
            <div id="titleHeader">
                Ecole Centrale Nantes
            </div>
            <div id="mailHeader">
                <%=Utilities.getEmail(request) %>
            </div>
        </div>

        <div id="nav">
            <div class="blocNav">
                <div class="blocNavHeader" style="background-color:pink;color:white;"><a href="Disconnect?connexionId=<%=request.getAttribute("connexionId")%>">Déconnexion</a></div>
                <div class="blocNavHeader" style="background-color:red;color:white;">Actualités</div>
                <div class="blocNavElement">
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=economie" target='PageCentrale'>Economie<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=finance" target='PageCentrale'>Finance<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=sciences" target='PageCentrale'>Sciences<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=marine" target='PageCentrale'>Marine<br/></a>
                </div>
            </div>

            <div class="blocNav">
                <div class="blocNavHeader" style="background-color:blue;color:white;">Jeux</div>
                <div class="blocNavElement">
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=da_inquisition" target='PageCentrale'>DA Inquisition<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=skyrim" target='PageCentrale'>Skyrim<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=the_witcher" target='PageCentrale'>The Witcher 3<br/></a>
                    <a href="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=dota2" target='PageCentrale'>Dota2<br/></a>
                </div>
            </div>
        </div>

        <div id="Centre">
            <iframe name="PageCentrale" src="Manager?connexionId=<%=request.getAttribute("connexionId")%>&value=classic" >
            </iframe>
        </div>

        <div id="Footer">
            Equipe pédagogique InfoMaths - Ecole Centrale de Nantes - France
        </div>
    </body>
</html>
