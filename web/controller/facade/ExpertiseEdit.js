sap.ui.define([
	"./BaseEdit",
	"sap/ui/model/Filter",
	"spet/sbwo/web/util/vhManager"
], function(BaseEdit, Filter, vhManager) {
	"use strict";
	
	return jQuery.extend({}, BaseEdit, {
		
		getCaseNumberInput: function() {
			// must be overriden by target controllers
		},
		
		getNextHearing: function(oData) {
			var iMaxTs = -1;
			if (oData && oData.hearings) {
				for (var i = 0; i < oData.hearings.length; ++i) {
					iMaxTs = Math.max(oData.hearings[i].date, iMaxTs);
				}
			}
			return iMaxTs === -1 ? null : iMaxTs;
		},
	
		onLoadCase: function() {
			var sNumber = this.getModel("data").getProperty("/number");
			this.getModel("view").setProperty("/case", null);
			if (sNumber) {
				this.get("/private/api/rest/expertise/case?number=" + encodeURIComponent(sNumber), function(oData) {
					if (oData) {
						this.onCaseLoaded(oData);
					}
					else {
						this.onCaseNotFound();
					}
				});
			}
		},
		
		onCaseLoaded: function(oData) {
			this.getModel("view").setProperty("/case", oData);
			this.getModel("data").setProperty("/nextHearing", this.getNextHearing(oData));
			if (oData.matter) {
				this.getModel("data").setProperty("/title", oData.matter.substring(0, 100));
			}
		},
		
		onCaseNotFound: function() {
			var oInput = this.getCaseNumberInput();
			if (oInput) {
				oInput.setValueState("Warning");
				oInput.setValueStateText(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
			}
		},
		
		onCourtLoaded: function(oData) {
			var oModel = this.getModel("data");
			oModel.setProperty("/court/code", oData.Code);
			oModel.setProperty("/court/id", oData.Id);
			oModel.setProperty("/court/name", oData.Name);
		},
		
		onCourtClear: function() {
			var oModel = this.getModel("data");
			oModel.setProperty("/court", {
				id: null, 
				name: "",
				code: ""
			});
		},
		
		onCaseNumberChanged: function(oEvent) {
			var oModel = this.getModel("data"),
				sOldCode = oModel.getProperty("/court/code"),
				fnUpdateCourt = function(oData) {
					if (oData && oData.results && oData.results.length) {
						this.onCourtLoaded(oData);
					}
					else {
						this.onCourtClear();
					}
				}, 
				sNewCode;
				
			this.onValueChanged(oEvent);
			sNewCode = oModel.getProperty("/court/code");
			
			if (sOldCode !== sNewCode) {
				if (sNewCode) {
					this.getModel().read("/Courts", {
						filters: [new Filter("Code", "EQ", sNewCode)],
						success: fnUpdateCourt.bind(this)
					});
				}
				else {
					this.onCourtClear();
				}
			}
			
			this.onLoadCase();
		},
		
		onOpenCourtValueHelp: function() {
			vhManager.get("Court").open(this.onCourtLoaded, this);
		}
	});
});