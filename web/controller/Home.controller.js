sap.ui.define([
	"./Base",
	"sap/m/StandardTile",
	"sap/ui/model/Sorter"
], function(Base, StandardTile, Sorter) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.Home", {
		onInit: function() {
			this.buildViewModel({
				edit: false,
				tiles: {},
				counts: {}
			});
			this.readTiles();
			this.getRouter().getTargets().getTarget("home").attachDisplay(this.readCounts, this);
		},
		
		getDefaultTiles: function() {
			var oBundle = this.getResourceBundle();
			return {
				"person-create" :{
					name:	"person-create",
					title:	oBundle.getText("sttHomePersonCreateTitle"),
					icon:	"sap-icon://add-contact",
					type:	"Create",
					order:	1,
					visible: true,
					navinfo: ["person-create", {}]
				},
				"import" :{
					name:	"import",
					title:	oBundle.getText("sttHomeImportTitle"),
					icon:	"sap-icon://add-contact",
					type:	"None",
					order:	5,
					visible: true,
					navinfo: ["import", {}]
				},
				"customizing": {
					name:	"customizing",
					title:	oBundle.getText("sttHomeCustomizingTitle"),
					icon:	"sap-icon://customize",
					type:	"None",
					order:	3,
					visible: true,
					navinfo: ["customizing", {}]
				},
				"person-list": {
					name:	"person-list",
					title:	oBundle.getText("sttHomePersonListTitle"),
					icon:	"sap-icon://customer-briefing",
					type:	"None",
					order:	1,
					visible: true,
					count:	"person",
					unit:	oBundle.getText("sttHomePersonNumberUnit"),
					navinfo: ["person-list", {}]
				},
				"deleted-list": {
					name:	"deleted-list",
					title:	oBundle.getText("sttHomeTrashCanTitle"),
					icon:	"sap-icon://delete",
					type:	"None",
					order:	4,
					visible: true,
					count:	"deleted",
					unit:	oBundle.getText("sttHomeDeletedNumberUnit"),
					navinfo: ["deleted-list", {}]
				}
			};
		},
		
		buildTile: function(sId, oContext) {
			if (oContext.getProperty("count")) {
				return new StandardTile(sId, {
					title:	oContext.getProperty("title"),
					icon:	oContext.getProperty("icon"),
					type:	oContext.getProperty("type"),
					press:	this.onPressTile.bind(this),
					visible: {
						path: "visible",
						model: "view"
					},
					number: {
						path: "/counts/" + oContext.getProperty("count"),
						model: "view"
					},
					numberUnit: oContext.getProperty("unit")
				});
			}
			else {
				return new StandardTile(sId, {
					title:	oContext.getProperty("title"),
					icon:	oContext.getProperty("icon"),
					type:	oContext.getProperty("type"),
					press:	this.onPressTile.bind(this),
					visible: {
						path: "visible",
						model: "view"
					}
				});
			}
		},
		
		onTileDelete: function(oEvent) {
			var oTile = oEvent.getParameter("tile"),
				oModel = this.getModel("view"),
				sPath = oTile.getBindingContext("view").getPath() + "/visible";
			oModel.setProperty(sPath, false);
		},
		
		onTileMove: function(oEvent) {
			var oContainer = oEvent.getSource(),
				aTiles = oContainer.getTiles(),
				oModel = this.getModel("view"),
				i,
				sPath;
			for (i = 0; i < aTiles.length; ++i) {
				sPath = aTiles[i].getBindingContext("view").getPath() + "/order";
				oModel.setProperty(sPath, i);
			}
		},
		
		onEditButtonPressed: function() {
			var oModel = this.getModel("view"),
				oTiles,
				oResult = {},
				sKey;
			if (!oModel.getProperty("/edit")) {
				oTiles = oModel.getProperty("/tiles") || {};
				for (sKey in oTiles) {
					if (oTiles.hasOwnProperty(sKey)) {
						oResult[sKey] = {
							visible: oTiles[sKey].visible,
							order: oTiles[sKey].order
						};
					}
				}
				jQuery.ajax({
					method: "PUT",
					url: "/private/api/rest/user/tiles/update",
					contentType: "application/json",
					data: JSON.stringify({tiles: oResult}),
					success: this.onTilesRead.bind(this)
				});
			}
		},
		
		resetTiles: function() {
			this.getModel("view").setProperty("/tiles", this.getDefaultTiles());
		},
		
		readTiles: function() {
			jQuery.ajax({
				method: "GET",
				url: "/private/api/rest/user/tiles/read",
				success: this.onTilesRead.bind(this)
			});
		},
		
		readCounts: function() {
			var oModel = this.getModel("view");
			jQuery.ajax({
				method: "GET",
				url:	"/private/api/rest/utility/count",
				success: function(oCounts) {
					oModel.setProperty("/counts", oCounts);
				}
			});
		},
		
		onTilesRead: function(oData) {
			var oModel = this.getModel("view"),
				oContainer = this.byId("tlcHome");
			if (oData && oData.tiles) {
				oModel.setProperty("/tiles", jQuery.extend(true, this.getDefaultTiles(), oData.tiles));
				oContainer.getBinding("tiles").sort(new Sorter({path: "order"}));
			}
		},
		
		onPressTile: function(oEvent) {
			var oContext = oEvent.getSource().getBindingContext("view");
			this.getRouter().navTo.apply(this.getRouter(), oContext.getProperty("navinfo"));
		}
	});

});