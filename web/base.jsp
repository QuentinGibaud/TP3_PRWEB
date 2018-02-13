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
        <div id="header">
            <div id="imageHeader">
                <img src="ECN.jpg" alt="ECN Logo" height="75" />
            </div>
            <div id="titleHeader">
                Ecole Centrale Nantes
            </div>
        </div>

        <div id="nav">
            <div class="blocNav">
                <div class="blocNavHeader" style="background-color:pink;color:white;"><a href="Disconnect?connexionId=<%=request.getAttribute("connexionId")%>">Déconnexion</a></div>
                <div class="blocNavHeader" style="background-color:red;color:white;">Actualités</div>
                <div class="blocNavElement">
                    Economie<br/>
                    Finance<br/>
                    Sciences<br/>
                    Marine<br/>
                </div>
            </div>

            <div class="blocNav">
                <div class="blocNavHeader" style="background-color:blue;color:white;">Jeux</div>
                <div class="blocNavElement">
                    DA Inquisition<br/>
                    Skyrim<br/>
                    The Witcher 3<br/>
                    Dota2<br/>
                </div>
            </div>
        </div>

        <div id="Centre">
            <iframe name="PageCentrale" src="page.jsp" >
            </iframe>
        </div>

        <div id="Footer">
            Equipe pédagogique InfoMaths - Ecole Centrale de Nantes - France
        </div>
    </body>
</html>
