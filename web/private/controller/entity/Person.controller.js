sap.ui.define(["./Base", "spet/sbwo/web/private/controller/mixin/PersonEdit", "jquery.sap.global"], function (_Base, _PersonEdit, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _PersonEdit2 = _interopRequireDefault(_PersonEdit);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.entity.Person", _jquerySap2.default.extend({}, _PersonEdit2.default, {
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("person").attachPatternMatched(this.onRouteMatched, this);
        },
        getBaseApiUrl: function getBaseApiUrl() {
            return "/private/api/rest/person";
        },
        getEntityListRoute: function getEntityListRoute() {
            return "person-list";
        },
        formatExportPath: function formatExportPath(sId) {
            return this.getBaseApiUrl() + "/export/" + sId;
        },
        onOpenLocationMapDialog: function onOpenLocationMapDialog() {
            this.byId("dlgLocationMap").open();
        },
        onCloseLocationMapDialog: function onCloseLocationMapDialog() {
            this.byId("dlgLocationMap").close();
        }
    }));
    return exports.default;
});
//# sourceMappingURL=Person.controller.js.map
