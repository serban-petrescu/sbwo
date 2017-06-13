import Base from "./Base";
import StandardTile from "sap/m/StandardTile";
import Sorter from "sap/ui/model/Sorter";
import tiles from "spet/sbwo/web/private/model/tiles";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.Home", {
    onInit() {
        this.buildViewModel({
            edit: false,
            tiles: {},
            counts: {}
        });
        this.readTiles();
        this.getRouter().getTargets().getTarget("home").attachDisplay(this.readCounts, this);
    },

    getDefaultTiles() {
        return tiles(this.getResourceBundle());
    },

    buildTile(sId, oContext) {
        let sNumberPath = null;
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

    onTileDelete(oEvent) {
        let sPath = oEvent.getParameter("tile").getBindingContext("view").getPath() + "/visible";
        this.getModel("view").setProperty(sPath, false);
    },

    onTileMove(oEvent) {
        var aTiles = oEvent.getSource().getTiles(),
            oModel = this.getModel("view");
        for (let i = 0; i < aTiles.length; ++i) {
            oModel.setProperty(aTiles[i].getBindingContext("view").getPath() + "/order", i);
        }
    },

    onEditButtonPressed() {
        let oModel = this.getModel("view"),
            oResult = {};
        if (!oModel.getProperty("/edit")) {
            let oTiles = oModel.getProperty("/tiles") || {};
            for (let sKey of Object.keys(oTiles)) {
                oResult[sKey] = {
                    visible: oTiles[sKey].visible,
                    order: oTiles[sKey].order
                };
            }
            this.put("/private/api/rest/user/tiles/update", {tiles: oResult}, this.onTilesRead);
        }
    },

    resetTiles() {
        this.getModel("view").setProperty("/tiles", this.getDefaultTiles());
    },

    readTiles() {
        this.get("/private/api/rest/user/tiles/read", this.onTilesRead);
    },

    readCounts() {
        this.get("/private/api/rest/utility/count", oCounts => this.getModel("view").setProperty("/counts", oCounts));
    },

    onTilesRead(oData) {
        let oModel = this.getModel("view"),
            oContainer = this.byId("tlcHome");
        if (oData && oData.tiles) {
            oModel.setProperty("/tiles", jQuery.extend(true, this.getDefaultTiles(), oData.tiles));
            oContainer.getBinding("tiles").sort(new Sorter({path: "order"}));
        }
    },

    onPressTile(oEvent) {
        var oContext = oEvent.getSource().getBindingContext("view");
        this.getRouter().navTo.apply(this.getRouter(), oContext.getProperty("navinfo"));
    }
});
