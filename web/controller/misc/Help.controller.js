sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"spet/sbwo/web/model/helps",
	"sap/ui/Device"
], function(Base, helps, Device) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.misc.Help", {
		onInit: function() {
			this.buildViewModel({
				helps: helps.pages(this.getResourceBundle())                         
			});
			this.getRouter().getRoute("help").attachPatternMatched(this.onRouteMatched, this);
		},
		
		onSearchMaster: function(oEvent) {
			this.applySearchFilter(oEvent.getParameter("query"), "title", this.byId("lstTopics").getBinding("items"));
		},
		
		onSelectMaster: function(oEvent) {
			var oItem = oEvent.getParameter("listItem") || oEvent.getSource(),
				sId = oItem.getBindingContext("view").getProperty("id");
			this.getRouter().navTo("help", {id: sId}, true);
		},
		
		onGoToMaster: function() {
			this.getRouter().navTo("help", {}, true);
		},
		
		onRouteMatched: function(oEvent) {
			var sId = oEvent.getParameter("arguments").id,
				oList = this.byId("lstTopics");
			if (!sId && !Device.system.phone) {
				sId = oList.getItems()[0].getBindingContext("view").getProperty("id");
				this.getRouter().navTo("help", {id: sId}, true);
			}
			else if (sId) {
				this.getView().bindElement({
					model: "view",
					path: "/helps/" + sId
				});
				this.byId("spcMain").to(this.byId("pagDetail"));
				this.updateSelection();
			}
			else {
				this.getView().unbindElement("view");
				this.byId("spcMain").backToPage(this.byId("pagMaster"));
				oList.removeSelections(true);
			}
		},
		
		updateSelection: function() {
			if (Device.system.phone) {
				return;
			}
			
			var	oList = this.byId("lstTopics"),
				fnGetId = function(oC) {
					return oC.getBindingContext("view").getProperty("id");
				},
				oSelection = oList.getSelectedItem(),
				sTarget = fnGetId(this.getView()),
				aItems,
				i;
				
			if (!oSelection || fnGetId(oSelection) !== sTarget) {
				aItems = oList.getItems();
				for (i = 0; i < aItems.length; ++i) {
					if (fnGetId(aItems[i]) === sTarget) {
						oList.setSelectedItem(aItems[i]);
						break;
					}
				}
			}
		}
	});

});