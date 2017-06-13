sap.ui.define(["sap/ui/core/mvc/Controller", "sap/ui/model/Filter", "sap/ui/model/FilterOperator"], function (_Controller, _Filter, _FilterOperator) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Controller2 = _interopRequireDefault(_Controller);

    var _Filter2 = _interopRequireDefault(_Filter);

    var _FilterOperator2 = _interopRequireDefault(_FilterOperator);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Controller2.default.extend("spet.sbwo.web.private.controller.vh.Base", {
        _callback: null,

        getDialog: function getDialog() {
            return this.byId("dlgMain");
        },
        getList: function getList() {
            return this.byId("lstMain");
        },
        getSearchAttribute: function getSearchAttribute() {
            return "Search";
        },
        getSearchField: function getSearchField() {
            return this.byId("seaMain");
        },
        onInit: function onInit() {
            this.getDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },
        search: function search(oEvent) {
            if (oEvent.getParameter("refreshButtonPressed")) {
                this.refresh();
            } else {
                var sAttribute = this.getSearchAttribute(),
                    oBinding = this.getList().getBinding("items"),
                    fnMap = function fnMap(sPart) {
                    return new _Filter2.default(sAttribute, _FilterOperator2.default.Contains, (sPart || "").toUpperCase());
                },
                    aFilters = oEvent.getParameter("query").split(" ").map(fnMap);
                if (aFilters.length) {
                    oBinding.filter(new _Filter2.default({
                        filters: aFilters,
                        and: true
                    }));
                } else {
                    oBinding.filter(aFilters);
                }
            }
        },
        open: function open(fnCallback, oListener) {
            this._callback = fnCallback && oListener ? fnCallback.bind(oListener) : fnCallback;
            this.getSearchField().setValue("");
            this.getList().getBinding("items").filter([]);
            this.getDialog().open();
        },
        close: function close() {
            this.getDialog().close();
            if (this._callback) {
                this._callback();
                this._callback = null;
            }
        },
        select: function select(oEvent) {
            this.getDialog().close();
            if (this._callback) {
                this._callback(oEvent.getSource().getBindingContext().getObject());
                this._callback = null;
            }
        },
        refresh: function refresh() {
            this.getList().getBinding("items").refresh();
        }
    });
    return exports.default;
});
//# sourceMappingURL=Base.js.map
