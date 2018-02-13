//----------------------------------------------------------------------
//  Nom 		: outils.js
//  Description	: Fonctions javascript
//  Auteurs	: Jean-Yves Martin
//  Date		: 28/01/2015
//  Der modif	: 28/01/2015
//----------------------------------------------------------------------

function displayZone(idZone) {
	// On commence par effacer les zones
	var TableauZones = document.getElementsByTagName("div");
	var nbZones = TableauZones.length;
	for (i=0; i < nbZones; i++) {
		var item = TableauZones[i];
		if (item) {
			var classe = item.getAttribute('class');
			if ((classe != null) && (classe == "blocNavElement")) {
				item.style.display = "none";
			}
		}
	}
	
	if ((idZone != null) && (idZone != "")) {
		// On affiche la zone concernée
		var item = document.getElementById(idZone);
		if (item != null) {
			var itemPere = item.parentNode;
			var TableauFils = itemPere.getElementsByTagName("div");
			var nbFils = TableauFils.length;
			for (i=0; i < nbFils; i++) {
				var itemFils = TableauFils[i];
				if (itemFils) {
					var classe = itemFils.getAttribute('class');
					if ((classe != null) && (classe == "blocNavElement")) {
						itemFils.style.display = "block";
					}
				}
			}
		}
	}
}
