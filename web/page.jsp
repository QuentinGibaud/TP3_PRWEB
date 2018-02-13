<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr-fr">
	<head>
		<title>test HTML</title>
		<meta charset="UTF-8" />
	</head>
	<body>
            <%@page import="org.centrale.prweb.Utilities" %>
            <div id="header">
                <div id="imageHeader">
                    <img src="ECN.jpg" alt="ECN Logo" height="75" />
                </div>
                <div id="titleHeader">
                    Ecole Centrale de Nantes
                </div>
                <div id="mailHeader">
                    <%=Utilities.getEmail(request) %>
                </div>
            </div>
	</body>
</html>
