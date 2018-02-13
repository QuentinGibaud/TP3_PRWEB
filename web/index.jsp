<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr-fr">
	<head>
		<title>... Connexion ...</title>
		<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
		<meta name="description" content="Exercice PHP" />
		<link href="login.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<form action="Identify" method="post" >
			<div id="connexion">
				<fieldset>
					<table border="0" cellspacing="0" cellpadding="2" width="100%">
						<tr><td colspan="2"><span style="margin-top:20px;">&nbsp;</span></td></tr>
						
						<tr>
							<td style="text-align:center"><img src="./Logo.jpg" alt="Logo" width="150" /></td>
							<td class="style0C" valign="middle">Planetarium</td>
						</tr>

						<tr><td colspan="2">&nbsp;</td></tr>

						<tr>
							<td colspan="2" class="style0C">Veuillez vous identifier</td>
						</tr>
						
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						
						<tr>
							<td class="style0R">Login</td>
							<td class="style0L"><input type="text" name="login" class="param" /></td>
						</tr>
						
						<tr>
							<td class="style0R" style="text-align:right;">Mot de passe</td>
							<td class="style0L"><input type="password" name="password" class="param" /></td>
						</tr>
						
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						
						<tr><td colspan="2">&nbsp;</td></tr>
						
						<tr>
							<td colspan="2" class="style0C">
								<input type="submit" value="Valider" name="Valider" class="stylebouton" />
							</td>
						</tr>
						
						<tr><td colspan="2"><span style="margin-top:20px;">&nbsp;</span></td></tr>
					</table>
				</fieldset>
			</div>
		</form>
	</body>
</html>

