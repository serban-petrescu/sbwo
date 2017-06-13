sap.ui.define(["./Base", "sap/ui/model/Filter"], function (_Base, _Filter) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _Filter2 = _interopRequireDefault(_Filter);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.list.BaseWithTabs", {
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this._buildTabsModel();
        },
        getUserAttribute: function getUserAttribute() {
            return "User";
        },
        getAllFilters: function getAllFilters() {
            var aFilters = _Base2.default.prototype.getAllFilters.apply(this, arguments),
                sKey = this.getModel("view").getProperty("/currentTab");
            if (sKey !== "all") {
                aFilters.push(new _Filter2.default(this.getUserAttribute(), "EQ", parseInt(sKey, 10)));
            }
            return aFilters;
        },
        onTabChanged: function onTabChanged() {
            this._filterList();
        },
        _buildTabsModel: function _buildTabsModel() {
            var oUserModel = this.getModel("user"),
                iUserId = oUserModel.getProperty("/id"),
                oViewModel = this.getModel("view"),
                fnMapOther = function fnMapOther(oOther) {
                return {
                    key: oOther.id,
                    title: oOther.username
                };
            },
                aOthers = oUserModel.getProperty("/others"),
                aStatic = [{
                key: "all",
                title: this.getResourceBundle().getText("itfAllUsersTitle")
            }, {
                key: iUserId,
                title: oUserModel.getProperty("/username")
            }],
                aTabs = aOthers.map(fnMapOther).concat(aStatic);
            oViewModel.setProperty("/tabs", aTabs);
            oViewModel.setProperty("/currentTab", iUserId);
        }
    });
    return exports.default;
});
//# sourceMappingURL=BaseWithTabs.js.map
