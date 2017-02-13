sap.ui.define([
	"./Base",
	"spet/sbwo/web/util/Timestamp",
	"sap/ui/model/Filter",
	"spet/sbwo/web/util/vhManager"
], function(Base, Timestamp, Filter, vhManager) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/expertise";
	
	return Base.extend("spet.sbwo.web.controller.wizard.ExpertiseCreate", {
		
		getEntityRoute: function() {
			return "expertise";
		},
		
		getCreateApiPath: function() {
			return sBaseApiPath + "/create";
		},
		
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.getRouter().getRoute("expertise-create").attachPatternMatched(this.onRouteMatched, this);
			this.byId("dlgCourtCase").addStyleClass(this.getOwnerComponent().getContentDensityClass());
		},
		
		getInitialDataModelContent: function() {
			return {
				number: "",
				title: "",
				year: null,
				court: {
					id: null, 
					name: "",
					code: ""
				},
				status: null,
				responsible: {
					id: null
				}
			};
		},
		
		getInitialViewModelContent: function() {
			return {
				draft: "Clear",
				"case": null,
				wizard: {
					step: 0,
					validity: [true, true, true]
				}
			};
		},
		
		restoreDraft: function() {
			Base.prototype.restoreDraft.apply(this, arguments);
			this.onLoadCase(this.byId("inpCaseNumber"));
		},
		
		onLoadCase: function(oInput) {
			var oDataModel = this.getModel("data"),
				oViewModel = this.getModel("view"),
				sNumber = oDataModel.getProperty("/number");
			oViewModel.setProperty("/case", null);
			if (sNumber) {
				this.get(sBaseApiPath + "/case?number=" + encodeURIComponent(sNumber), function(oData){
					if (!oData) {
						if (oInput) {
							oInput.setValueState("Warning");
							oInput.setValueStateText(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
						}
					}
					else {
						oViewModel.setProperty("/case", oData);
					}
				});
			}
		},
		
		onCaseNumberChanged: function(oEvent) {
			var oModel = this.getModel("data"),
				sOldCode = oModel.getProperty("/court/code"),
				sNewCode,
				fnSaveDraft = this.saveDraft.bind(this),
				fnUpdateCourt = function(oData) {
					if (oData && oData.results && oData.results.length) {
						oModel.setProperty("/court/code", oData.results[0].Code);
						oModel.setProperty("/court/id", oData.results[0].Id);
						oModel.setProperty("/court/name", oData.results[0].Name);
						fnSaveDraft();
					}
				},
				fnReadCourt = function(oM, sCode) {
					oM.read("/Courts", {
						filters: [new Filter("Code", "EQ", sCode)],
						success: fnUpdateCourt
					});
				},
				fnResetCourt = function() {
					oModel.setProperty("/court", {
						id: null, 
						name: "",
						code: ""
					});
				};
				
			this.onValueChanged(oEvent);
			sNewCode = oModel.getProperty("/court/code");
			
			if (sOldCode !== sNewCode) {
				if (sNewCode) {
					fnReadCourt(this.getModel(), sNewCode);
				}
				else {
					fnResetCourt();
				}
				fnSaveDraft();
			}
			
			this.onLoadCase(oEvent.getSource());
		},
		
		onOpenCourtCaseDialog: function() {
			this.byId("dlgCourtCase").open();
		},
		
		onCloseCourtCaseDialog: function() {
			this.byId("dlgCourtCase").close();
		},
		
		onOpenCourtValueHelp: function() {
			vhManager.get("Court").open(function(oData){
				if (oData) {
					var oModel = this.getModel("data");
					oModel.setProperty("/court/code", oData.Code);
					oModel.setProperty("/court/id", oData.Id);
					oModel.setProperty("/court/name", oData.Name);
					this.saveDraft();
				}
			}, this);
		}
	});

});