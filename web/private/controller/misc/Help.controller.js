sap.ui.define(["spet/sbwo/web/private/controller/Base", "spet/sbwo/web/private/model/helps", "sap/ui/Device"], function (_Base, _helps, _Device) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _helps2 = _interopRequireDefault(_helps);

    var _Device2 = _interopRequireDefault(_Device);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.Help", {
        onInit: function onInit() {
            this.buildViewModel({
                helps: _helps2.default.pages(this.getResourceBundle())
            });
            this.getRouter().getRoute("help").attachPatternMatched(this.onRouteMatched, this);
        },
        onSearchMaster: function onSearchMaster(oEvent) {
            this.applySearchFilter(oEvent.getParameter("query"), "title", this.byId("lstTopics").getBinding("items"));
        },
        onSelectMaster: function onSelectMaster(oEvent) {
            var oItem = oEvent.getParameter("listItem") || oEvent.getSource(),
                sId = oItem.getBindingContext("view").getProperty("id");
            this.getRouter().navTo("help", {
                id: sId
            }, true);
        },
        onGoToMaster: function onGoToMaster() {
            this.getRouter().navTo("help", {}, true);
        },
        onRouteMatched: function onRouteMatched(oEvent) {
            var sId = oEvent.getParameter("arguments").id,
                oList = this.byId("lstTopics");
            if (!sId && !_Device2.default.system.phone) {
                sId = oList.getItems()[0].getBindingContext("view").getProperty("id");
                this.getRouter().navTo("help", {
                    id: sId
                }, true);
            } else if (sId) {
                this.getView().bindElement({
                    model: "view",
                    path: "/helps/" + sId
                });
                this.byId("spcMain").to(this.byId("pagDetail"));
                this.updateSelection();
            } else {
                this.getView().unbindElement("view");
                this.byId("spcMain").backToPage(this.byId("pagMaster"));
                oList.removeSelections(true);
            }
        },
        updateSelection: function updateSelection() {
            if (_Device2.default.system.phone) {
                return;
            }

            var oList = this.byId("lstTopics"),
                fnGetId = function fnGetId(oC) {
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
    return exports.default;
});
//# sourceMappingURL=Help.controller.js.map
