sap.ui.define([
	"./Base",
	"spet/sbwo/web/controller/facade/ExpertiseEdit",
	"sap/m/MessageBox"
], function(Base, ExpertiseEdit, MessageBox) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/expertise";
	
	return Base.extend("spet.sbwo.web.controller.entity.Expertise", jQuery.extend({}, ExpertiseEdit, {
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.getRouter().getRoute("expertise").attachPatternMatched(this.onRouteMatched, this);
		},
		
		getReadApiUrl: function(sId){
			return sBaseApiPath + "/read/" + sId;
		},
		
		getUpdateApiUrl: function(sId) {
			return sBaseApiPath + "/update/" + sId;
		},
		
		getRestoreApiUrl: function(sId) {
			return sBaseApiPath + "/restore/" + sId;
		},
		
		getDeleteApiUrl: function(sId) {
			return sBaseApiPath + "/delete/" + sId;
		},
		
		getEntityListRoute: function() {
			return "expertise-list";
		},
		
		onReadSuccess: function() {
			Base.prototype.onReadSuccess.apply(this, arguments);
			this.getModel("view").setProperty("/case", null);
		},
		
		getCaseNumberInput: function() {
			return this.byId("inpCaseNumber");
		},
		
		onCaseNotFound: function() {
			if (this.getModel("view").getProperty("/edit")) {
				ExpertiseEdit.onCaseNotFound.apply(this, arguments);
			}
			else {
				if (this.byId("dlgCourtCase").isOpen()) {
					this.byId("dlgCourtCase").close();
				}
				MessageBox.warning(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
			}
		},
		
		onCaseLoaded: function(oData) {
			if (this.getModel("view").getProperty("/edit")) {
				ExpertiseEdit.onCaseLoaded.apply(this, arguments);
				this.saveDraft();
			}
			else {
				this.getModel("view").setProperty("/case", oData);
			}
		},
		
		onCourtLoaded: function() {
			ExpertiseEdit.onCourtLoaded.apply(this, arguments);
			this.saveDraft();
		},
		
		onCourtClear: function() {
			ExpertiseEdit.onCourtClear.apply(this, arguments);
			this.saveDraft();
		},
		
		onOpenCourtCaseDialog: function() {
			if (this.getModel("view").getProperty("/case") === null) {
				this.onLoadCase();
			}
			this.byId("dlgCourtCase").open();
		},
		
		onCloseCourtCaseDialog: function() {
			this.byId("dlgCourtCase").close();
		},
		
		onOpenLocationMapDialog: function() {
			this.byId("dlgLocationMap").open();
		},
		
		onCloseLocationMapDialog: function() {
			this.byId("dlgLocationMap").close();
		}
		
	}));

});