sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/Device",
	"sap/ui/model/resource/ResourceModel",
	"sap/ui/model/json/JSONModel"
], function(Controller, Device, ResourceModel, JSONModel) {
	
	/**
	 * Login controller class.
	 * @class
	 */
	return Controller.extend("login.view.Main", {
		onInit: function() {
			var oModel = new ResourceModel({bundleUrl: "i18n/i18n.properties"});
			window.document.title = oModel.getProperty("pageTitle");
			this.getView().setModel(oModel, "i18n");
			this.getView().setModel(new JSONModel({
				error: window.location.hash === "#/error",
				localhost: /localhost:\d+/.test(window.location.host),
			}), "view");
			
			if (!Device.support.touch) {
				this.getView().addStyleClass("sapUiSizeCompact");
			}
			else {
				this.getView().addStyleClass("sapUiSizeCozy");
			}
		},
		
		onLogin: function() {
			var oForm = this.byId("hbxMain").$().parent();
			oForm.submit();
		}
	});
});