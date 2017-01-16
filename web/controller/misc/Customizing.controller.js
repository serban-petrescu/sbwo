sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/m/MessageBox"
], function(Base, MessageBox) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.misc.Customizing", {
		onInit: function() {
			var oBundle = this.getResourceBundle(),
				sClass = this.getOwnerComponent().getContentDensityClass();
				
			this.buildViewModel({
				busy: false,
				destinations: {
					location: {
						title: oBundle.getText("btnCustomizingUploadLocationsText"),
						icon: "sap-icon://excel-attachment",
						tooltip: oBundle.getText("btnCustomizingUploadLocationsText"),
						dialog: {
							target: "/private/api/rest/import/locations/{0}/{1}",
							text: oBundle.getText("btnCustomizingUploadLocationsDescription"),
							separator: ",",
							header: false
						}
					}
				}
			});
			
			this.getOwnerComponent().getModel().securityTokenAvailable().then(function(sToken) {
				this.getModel("view").setProperty("/securityToken", sToken);
			}.bind(this));
			
			this.byId("pagCustomizing").getDependents().forEach(function(oD){oD.addStyleClass(sClass);});
		},
		
		formatUploadUrl: function(sPattern, sSeparator, bHeader) {
			if (sPattern) {
				return jQuery.sap.formatMessage(sPattern, [encodeURIComponent(sSeparator), bHeader + ""]);
			}
			else {
				return "";
			}
		},
		
		onOpenUploadSheet: function(oEvent){ 
			this.byId("actUploadDestination").openBy(oEvent.getSource());
		},
		
		onOpenUploadDialog: function(oEvent){
			var sPath = oEvent.getSource().getBindingContext("view").getPath(),
				oDialog = this.byId("dlgUpload");
			oDialog.bindElement({
				model:	"view",
				path:	sPath + "/dialog"
			});
			oDialog.open();
		},
		
		onCloseUploadDialog: function() {
			this.byId("dlgUpload").close();
		},
		
		onUploadStart: function() {
			this.getModel("view").setProperty("/busy", true);
			this.byId("fupCustomizing").upload();
		},
		
		onUploadComplete: function(oEvent){
			var oBundle = this.getResourceBundle();
			this.getModel("view").setProperty("/busy", false);
			if (oEvent.getParameter("status") === 204) {
				MessageBox.success(oBundle.getText("txtCustomizingUploadSuccessText"));
			}
			else {
				this.onInternalServerError(oEvent.getParameter("responseRaw"));
			}
			this.getModel().refresh();
			this.byId("dlgUpload").close();
		}
	});

});