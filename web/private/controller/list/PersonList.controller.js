sap.ui.define(["./Base"], function (_Base) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.list.PersonList", {
        getEntityRoute: function getEntityRoute() {
            return "person";
        },
        getListRoute: function getListRoute() {
            return "person-list";
        }
    });
    return exports.default;
});
//# sourceMappingURL=PersonList.controller.js.map
