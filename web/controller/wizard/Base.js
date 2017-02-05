sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/ui/core/routing/History",
	"sap/ui/model/json/JSONModel",
	"sap/ui/core/routing/HashChanger",
	"spet/sbwo/web/util/DraftService",
	"sap/m/Wizard"
], function(Base, History, JSONModel, HashChanger, DraftService, Wizard) {
	"use strict";
	
	if (Wizard.CONSTANTS) {
		Wizard.CONSTANTS.SCROLL_OFFSET = 0;
	}
	
	return Base.extend("spet.sbwo.web.controller.wizard.Base", {
		_rendered: null,
		
		getEntityRoute: function() {},
		getCreateApiPath: function() {},
		
		onInit: function() {
			this.buildViewModel();
			this.buildDataModel();
			this._rendered = jQuery.Deferred();
		},
		
		onAfterRendering: function() {
			this._rendered.resolve();
		},
		
		buildViewModel: function() {
			this.setModel(new JSONModel(this.getInitialViewModelContent()), "view");
		},
		
		buildDataModel: function() {
			this.setModel(new JSONModel(this.getInitialDataModelContent()), "data");
		},
		
		getWizard: function() {
			return this.byId("wizMain");
		},
		
		getInitialDataModelContent: function() {
			return {};
		},
		
		getInitialViewModelContent: function() {
			return {};
		},
		
		onValueChanged: function() {
			Base.prototype.onValueChanged.apply(this, arguments);
			this._saveDraft();
		},
		
		onRouteMatched: function() {
			this._reset();
			this._restoreDraft();
		},
		
		onReset: function() {
			this._reset();
			DraftService.remove(HashChanger.getInstance().getHash());
		},
		
		onComplete: function() {
			var sPath = this.getCreateApiPath(),
				oData = this.getModel("data").getData(),
				fnSuccess = function(sId) {
					DraftService.remove(HashChanger.getInstance().getHash());
					this.getRouter().navTo(this.getEntityRoute(), {
						id: sId
					}, true);
				};
			this.post(sPath, oData, fnSuccess);
		},
		
		_saveDraft: function() {
			var oModel = this.getModel("view"),
				oData = {
					data: this.getModel("data").getData(),
					wizard: this.getModel("view").getProperty("/wizard")
				},
				sHash = HashChanger.getInstance().getHash();
			oModel.setProperty("/draft", "Saving");
			DraftService.save(sHash, oData).then(function(){
				oModel.setProperty("/draft", "Saved");
			});
			this._updateWizardModel();
		},
		
		_restoreDraft: function() {
			DraftService.load(HashChanger.getInstance().getHash()).then(function(oData){
				if (oData && oData.data) {
					this.getModel("data").setData(oData.data);
					this.getModel("view").setProperty("/wizard", oData.wizard);
					this._updateWizardControl();
				}
			}.bind(this));
		},
		
		_reset: function() {
			var oWizard = this.getWizard(),
				oFirstStep = oWizard.getSteps()[0];
			oWizard.discardProgress(oFirstStep);
			this.getModel("view").setData(this.getInitialViewModelContent());
			this.getModel("data").setData(this.getInitialDataModelContent());
		},
		
		_updateWizardModel: function() {
			this.getModel("view").setProperty("/wizard/step", this.getWizard().getProgress());
		},
		
		_updateWizardControl: function() {
			var oWizard = this.getWizard(),
				oFirstStep = oWizard.getSteps()[0],
				iTargetStep = this.getModel("view").getProperty("/wizard/step");
				
			this._rendered.then(function() {
			oWizard.discardProgress(oFirstStep);
				for (var i = 1; i < iTargetStep; ++i) {
					oWizard.nextStep();
				}
			});
		}
		
	});

});