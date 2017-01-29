sap.ui.define([
	"spet/sbwo/web/controller/entity/Base"
], function(Base) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/user";
	
	return Base.extend("spet.sbwo.web.controller.misc.UserSettings", {
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.getRouter().getRoute("user-settings").attachPatternMatched(this.onRouteMatched, this);
		},
		
		getReadApiUrl: function(){
			return sBaseApiPath + "/preference/read";
		},
		
		getUpdateApiUrl: function() {
			return sBaseApiPath + "/preference/update";
		},
		
		formatLanguageText: function(sCode) {
			var oBundle = this.getResourceBundle();
			switch((sCode || "").toLowerCase()) {
			case "en": return oBundle.getText("txtLanguageEnglishText");
			case "ro": return oBundle.getText("txtLanguageRomanianText");
			default: return "";
			}
		},
		
		formatThemeText: function(sId) {
			var oBundle = this.getResourceBundle();
			switch((sId || "").toLowerCase()) {
			case "sap_belize": return oBundle.getText("txtThemeBelizeText");
			case "sap_bluecrystal": return oBundle.getText("txtThemeBlueCrystalText");
			case "sap_hcb": return oBundle.getText("txtThemeHighContrastBlackText");
			default: return "";
			}
		}
	});

});