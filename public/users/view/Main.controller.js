sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/Device",
	"sap/ui/model/resource/ResourceModel",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageBox"
], function(Controller, Device, ResourceModel, JSONModel, MessageBox) {
	
	var API_BASE_PATH = "/public/rest/user/manage";
	
	/**
	 * Login controller class.
	 * @class
	 */
	return Controller.extend("users.view.Main", {
		onInit: function() {
			var oModel = new ResourceModel({bundleUrl: "i18n/i18n.properties"});
			window.document.title = oModel.getProperty("pageTitle");
			this.getView().setModel(oModel, "i18n");
			this.getView().setModel(new JSONModel({
				users: [],
				current: null,
				newUsername: ""
			}), "view");
			
			if (!Device.support.touch) {
				this.getView().addStyleClass("sapUiSizeCompact");
				this.byId("dlgCreate").addStyleClass("sapUiSizeCompact");
			}
			else {
				this.getView().addStyleClass("sapUiSizeCozy");
				this.byId("dlgCreate").addStyleClass("sapUiSizeCozy");
			}
			
			this.onRefresh();
		},
		
		onRefresh: function() {
			var oModel = this.getView().getModel("view"),
				oList = this.byId("lstUsers");
			jQuery.ajax({
				url: 	API_BASE_PATH + "/list",
				method:	"GET",
				dataType: "json",
				success: function(oData) {
					oModel.setProperty("/users", oData);
					oModel.setProperty("/current", null);
					oList.removeSelections(true);
				},
				error: this.onError.bind(this)
			});
		},
		
		onError: function(oXhr, sStatus, sError) {
			var i18n = this.getView().getModel("i18n");
			MessageBox.error(i18n.getProperty("txtErrorMessageText"), {
				details: sError
			});
		},
		
		onSelect: function(oEvent) {
			var oUser,
				oModel = this.getView().getModel("view"),
				oItem = oEvent.getParameter("listItem");
			if (oEvent.getParameter("selected") && oItem) {
				oUser = oItem.getBindingContext("view").getObject();
				oModel.setProperty("/current", oUser);
			}
			else {
				oModel.setProperty("/current", null);
			}
		},
		
		onToggleActivation: function() {
			var oModel = this.getView().getModel("view");
			jQuery.ajax({
				url: 	API_BASE_PATH + "/activate",
				method:	"POST",
				data: 	{
					username: oModel.getProperty("/current/username"),
					active: !oModel.getProperty("/current/active")
				},
				success: this.onRefresh.bind(this),
				error: this.onError.bind(this)
			});
		},
		
		onResetPassword: function() {
			var oModel = this.getView().getModel("view"),
				i18n = this.getView().getModel("i18n");
			jQuery.ajax({
				url: 	API_BASE_PATH + "/reset",
				method:	"POST",
				data: 	{
					username: oModel.getProperty("/current/username")
				},
				success: function() {
					MessageBox.success(i18n.getProperty("txtPasswordResetText"));
				},
				error: this.onError.bind(this)
			});
		},
		
		onOpenCreateDialog: function() {
			this.getView().getModel("view").setProperty("/newUsername", "");
			this.byId("dlgCreate").open();
		},
		
		onCloseCreateDialog: function() {
			this.byId("dlgCreate").close();
		},
		
		onRegisterUser: function() {
			var oModel = this.getView().getModel("view");
			jQuery.ajax({
				url: 	API_BASE_PATH + "/register",
				method:	"POST",
				data: {username: oModel.getProperty("/newUsername")},
				success: function(oData) {
					this.onCloseCreateDialog();
					this.onRefresh();
				}.bind(this),
				error: this.onError.bind(this)
			});
		},
		
		onNavBack: function() {
			window.history.go(-1);
		}
	});
});