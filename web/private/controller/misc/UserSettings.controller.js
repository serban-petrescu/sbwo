sap.ui.define(["spet/sbwo/web/private/controller/entity/Base", "sap/m/MessageBox"], function (_Base, _MessageBox) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var USER_BASE_API_PATH = "/private/api/rest/user";

    var LANGUAGE_TO_I18N = new Map([["en", "txtLanguageEnglishText"], ["ro", "txtLanguageRomanianText"]]);

    var THEME_TO_I18N = new Map([["sap_belize", "txtThemeBelizeText"], ["sap_bluecrystal", "txtThemeBlueCrystalText"], ["sap_hcb", "txtThemeHighContrastBlackText"]]);

    var fnI18nMapOrEmpty = function fnI18nMapOrEmpty(oBundle, oMap, sKey) {
        return oMap.has(sKey) ? oBundle.getText(oMap.get(sKey)) : "";
    };
    var fnCodeToKey = function fnCodeToKey(sCode) {
        return (sCode || "").toLowerCase();
    };

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.UserSettings", {
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("user-settings").attachPatternMatched(this.onRouteMatched, this);
            this.byId("dlgChangePassword").addStyleClass(this.getOwnerComponent().getContentDensityClass());
            this.getModel("view").setProperty("/password", { first: "", second: "" });
        },
        getBaseApiUrl: function getBaseApiUrl() {
            return USER_BASE_API_PATH + "/preference";
        },
        formatLanguageText: function formatLanguageText(sCode) {
            return fnI18nMapOrEmpty(this.getResourceBundle(), LANGUAGE_TO_I18N, fnCodeToKey(sCode));
        },
        formatThemeText: function formatThemeText(sCode) {
            return fnI18nMapOrEmpty(this.getResourceBundle(), THEME_TO_I18N, fnCodeToKey(sCode));
        },
        onSaveSuccess: function onSaveSuccess() {
            _Base2.default.prototype.onSaveSuccess.apply(this, arguments);
            _MessageBox2.default.information(this.getResourceBundle().getText("txtUserSettingsUpdatedRefreshInfo"));
        },
        onOpenPasswordDialog: function onOpenPasswordDialog() {
            this.byId("dlgChangePassword").open();
        },
        onClosePasswordDialog: function onClosePasswordDialog() {
            this.getModel("view").setProperty("/password", { first: "", second: "" });
            this.byId("dlgChangePassword").close();
        },
        onChangePassword: function onChangePassword() {
            var _this = this;

            var oModel = this.getModel("view"),
                sFirst = oModel.getProperty("/password/first"),
                sSecond = oModel.getProperty("/password/second");
            if (sFirst && sFirst === sSecond && sFirst.length > 6) {
                this.put(USER_BASE_API_PATH + "/password/update", { password: sFirst }, function () {
                    _this.onClosePasswordDialog();
                    _MessageBox2.default.information(_this.getResourceBundle().getText("txtChangePasswordSuccessInfo"));
                });
            } else {
                _MessageBox2.default.error(this.getResourceBundle().getText("txtChangePasswordInvalidError"));
            }
        }
    });
    return exports.default;
});
//# sourceMappingURL=UserSettings.controller.js.map
