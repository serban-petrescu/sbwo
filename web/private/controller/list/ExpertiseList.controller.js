sap.ui.define(["./BaseWithTabs", "spet/sbwo/web/private/model/formatter"], function (_BaseWithTabs, _formatter) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _BaseWithTabs2 = _interopRequireDefault(_BaseWithTabs);

    var _formatter2 = _interopRequireDefault(_formatter);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _BaseWithTabs2.default.extend("spet.sbwo.web.private.controller.list.ExpertiseList", {
        getEntityRoute: function getEntityRoute() {
            return "expertise";
        },
        getListRoute: function getListRoute() {
            return "expertise-list";
        },
        getUserAttribute: function getUserAttribute() {
            return "Responsible";
        },
        getDefaultSortSettings: function getDefaultSortSettings() {
            return [{
                path: "NextHearing",
                descending: true,
                group: false
            }];
        },
        getFormatterForColumn: function getFormatterForColumn(sColumn) {
            return sColumn === "Status" ? _formatter2.default.expertiseStatus : null;
        }
    });
    return exports.default;
});
//# sourceMappingURL=ExpertiseList.controller.js.map
