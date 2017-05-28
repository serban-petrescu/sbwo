sap.ui.define([
    "./Base",
    "sap/m/StandardTile",
    "sap/ui/model/Sorter",
    "spet/sbwo/web/model/tiles"
], function(Base, StandardTile, Sorter, tiles) {
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
            return tiles(this.getResourceBundle());
        },

        buildTile: function(sId, oContext) {
            var sNumberPath = null;
            if (oContext.getProperty("count")) {
                sNumberPath = "/counts/" + oContext.getProperty("count");
            }
            else if (oContext.getProperty("number")) {
                sNumberPath = "number";
            }

            if (sNumberPath) {
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
                        path: sNumberPath,
                        model: "view",
                        type: "sap.ui.model.type.Integer",
                        formatOptions: {style: "short"}
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
                this.put("/private/api/rest/user/tiles/update", {tiles: oResult}, this.onTilesRead);
            }
        },

        resetTiles: function() {
            this.getModel("view").setProperty("/tiles", this.getDefaultTiles());
        },

        readTiles: function() {
            this.get("/private/api/rest/user/tiles/read", this.onTilesRead);
        },

        readCounts: function() {
            var fnSuccess = function(oCounts) {
                this.getModel("view").setProperty("/counts", oCounts);
            };
            this.get("/private/api/rest/utility/count", fnSuccess);
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
