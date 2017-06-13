sap.ui.define(["./Base", "jquery.sap.global"], function (_Base, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.list.DeletedEntityList", {
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.buildViewModel({
                selection: false
            });
        },
        getEntityRoute: function getEntityRoute(oContext) {
            return this.getRouteForEntityType(oContext.getProperty("Type"));
        },
        getListRoute: function getListRoute() {
            return "deleted-list";
        },
        onRouteMatched: function onRouteMatched() {
            _Base2.default.prototype.onRouteMatched.apply(this, arguments);
            this.onDataReceived();
        },
        onSelectionChange: function onSelectionChange(oEvent) {
            this.getModel("view").setProperty("/selection", oEvent.getSource().getSelectedItems().length > 0);
        },
        getSelectionEntities: function getSelectionEntities() {
            var aItems = this.byId("lstMain").getSelectedItems(),
                aData = aItems.map(function (oItem) {
                var oContext = oItem.getBindingContext();
                return {
                    id: oContext.getProperty("Id"),
                    type: oContext.getProperty("Type")
                };
            });
            return aData;
        },
        onDeleteSelected: function onDeleteSelected() {
            var _this = this;

            _jquerySap2.default.ajax({
                method: "DELETE",
                url: "/private/api/rest/trash/delete",
                contentType: "application/json",
                data: JSON.stringify(this.getSelectionEntities()),
                success: function success() {
                    return _this.onDataReceived();
                }
            });
        },
        onRestoreSelected: function onRestoreSelected() {
            this.put("/private/api/rest/trash/restore", this.getSelectionEntities(), this.onDataReceived);
        },
        onDeleteAll: function onDeleteAll() {
            this.del("/private/api/rest/trash/delete/all", this.onDataReceived);
        },
        onDataReceived: function onDataReceived() {
            this.getModel().refresh();
            this.clearSelection();
        },
        clearSelection: function clearSelection() {
            this.byId("lstMain").removeSelections(true);
            this.getModel("view").setProperty("/selection", false);
        }
    });
    return exports.default;
});
//# sourceMappingURL=DeletedEntityList.controller.js.map
