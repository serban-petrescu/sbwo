sap.ui.define(["./Base", "sap/m/StandardTile", "sap/ui/model/Sorter", "spet/sbwo/web/private/model/tiles", "jquery.sap.global"], function (_Base, _StandardTile, _Sorter, _tiles, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _StandardTile2 = _interopRequireDefault(_StandardTile);

    var _Sorter2 = _interopRequireDefault(_Sorter);

    var _tiles2 = _interopRequireDefault(_tiles);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.Home", {
        onInit: function onInit() {
            this.buildViewModel({
                edit: false,
                tiles: {},
                counts: {}
            });
            this.readTiles();
            this.getRouter().getTargets().getTarget("home").attachDisplay(this.readCounts, this);
        },
        getDefaultTiles: function getDefaultTiles() {
            return (0, _tiles2.default)(this.getResourceBundle());
        },
        buildTile: function buildTile(sId, oContext) {
            var sNumberPath = null;
            if (oContext.getProperty("count")) {
                sNumberPath = "/counts/" + oContext.getProperty("count");
            } else if (oContext.getProperty("number")) {
                sNumberPath = "number";
            }

            if (sNumberPath) {
                return new _StandardTile2.default(sId, {
                    title: oContext.getProperty("title"),
                    icon: oContext.getProperty("icon"),
                    type: oContext.getProperty("type"),
                    press: this.onPressTile.bind(this),
                    visible: {
                        path: "visible",
                        model: "view"
                    },
                    number: {
                        path: sNumberPath,
                        model: "view",
                        type: "sap.ui.model.type.Integer",
                        formatOptions: { style: "short" }
                    },
                    numberUnit: oContext.getProperty("unit")
                });
            } else {
                return new _StandardTile2.default(sId, {
                    title: oContext.getProperty("title"),
                    icon: oContext.getProperty("icon"),
                    type: oContext.getProperty("type"),
                    press: this.onPressTile.bind(this),
                    visible: {
                        path: "visible",
                        model: "view"
                    }
                });
            }
        },
        onTileDelete: function onTileDelete(oEvent) {
            var sPath = oEvent.getParameter("tile").getBindingContext("view").getPath() + "/visible";
            this.getModel("view").setProperty(sPath, false);
        },
        onTileMove: function onTileMove(oEvent) {
            var aTiles = oEvent.getSource().getTiles(),
                oModel = this.getModel("view");
            for (var i = 0; i < aTiles.length; ++i) {
                oModel.setProperty(aTiles[i].getBindingContext("view").getPath() + "/order", i);
            }
        },
        onEditButtonPressed: function onEditButtonPressed() {
            var oModel = this.getModel("view"),
                oResult = {};
            if (!oModel.getProperty("/edit")) {
                var oTiles = oModel.getProperty("/tiles") || {};
                var _iteratorNormalCompletion = true;
                var _didIteratorError = false;
                var _iteratorError = undefined;

                try {
                    for (var _iterator = Object.keys(oTiles)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                        var sKey = _step.value;

                        oResult[sKey] = {
                            visible: oTiles[sKey].visible,
                            order: oTiles[sKey].order
                        };
                    }
                } catch (err) {
                    _didIteratorError = true;
                    _iteratorError = err;
                } finally {
                    try {
                        if (!_iteratorNormalCompletion && _iterator.return) {
                            _iterator.return();
                        }
                    } finally {
                        if (_didIteratorError) {
                            throw _iteratorError;
                        }
                    }
                }

                this.put("/private/api/rest/user/tiles/update", { tiles: oResult }, this.onTilesRead);
            }
        },
        resetTiles: function resetTiles() {
            this.getModel("view").setProperty("/tiles", this.getDefaultTiles());
        },
        readTiles: function readTiles() {
            this.get("/private/api/rest/user/tiles/read", this.onTilesRead);
        },
        readCounts: function readCounts() {
            var _this = this;

            this.get("/private/api/rest/utility/count", function (oCounts) {
                return _this.getModel("view").setProperty("/counts", oCounts);
            });
        },
        onTilesRead: function onTilesRead(oData) {
            var oModel = this.getModel("view"),
                oContainer = this.byId("tlcHome");
            if (oData && oData.tiles) {
                oModel.setProperty("/tiles", _jquerySap2.default.extend(true, this.getDefaultTiles(), oData.tiles));
                oContainer.getBinding("tiles").sort(new _Sorter2.default({ path: "order" }));
            }
        },
        onPressTile: function onPressTile(oEvent) {
            var oContext = oEvent.getSource().getBindingContext("view");
            this.getRouter().navTo.apply(this.getRouter(), oContext.getProperty("navinfo"));
        }
    });
    return exports.default;
});
//# sourceMappingURL=Home.controller.js.map
