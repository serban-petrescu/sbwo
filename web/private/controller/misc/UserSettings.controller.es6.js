import Base from "spet/sbwo/web/private/controller/entity/Base";
import MessageBox from "sap/m/MessageBox";

const USER_BASE_API_PATH = "/private/api/rest/user";

const LANGUAGE_TO_I18N = new Map([
    ["en", "txtLanguageEnglishText"],
    ["ro", "txtLanguageRomanianText"]
]);

const THEME_TO_I18N = new Map([
    ["sap_belize", "txtThemeBelizeText"],
    ["sap_bluecrystal", "txtThemeBlueCrystalText"],
    ["sap_hcb", "txtThemeHighContrastBlackText"]
]);

const fnI18nMapOrEmpty = (oBundle, oMap, sKey) => oMap.has(sKey) ? oBundle.getText(oMap.get(sKey)) : "";
const fnCodeToKey = sCode => (sCode || "").toLowerCase();

export default Base.extend("spet.sbwo.web.private.controller.misc.UserSettings", {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.getRouter().getRoute("user-settings").attachPatternMatched(this.onRouteMatched, this);
        this.byId("dlgChangePassword").addStyleClass(this.getOwnerComponent().getContentDensityClass());
        this.getModel("view").setProperty("/password", {first: "", second: ""});
    },

    getBaseApiUrl(){
        return USER_BASE_API_PATH + "/preference";
    },

    formatLanguageText(sCode) {
        return fnI18nMapOrEmpty(this.getResourceBundle(), LANGUAGE_TO_I18N, fnCodeToKey(sCode));
    },

    formatThemeText(sCode) {
        return fnI18nMapOrEmpty(this.getResourceBundle(), THEME_TO_I18N, fnCodeToKey(sCode));
    },

    onSaveSuccess() {
        Base.prototype.onSaveSuccess.apply(this, arguments);
        MessageBox.information(this.getResourceBundle().getText("txtUserSettingsUpdatedRefreshInfo"));
    },

    onOpenPasswordDialog() {
        this.byId("dlgChangePassword").open();
    },

    onClosePasswordDialog() {
        this.getModel("view").setProperty("/password", {first: "", second: ""});
        this.byId("dlgChangePassword").close();
    },

    onChangePassword() {
        let oModel = this.getModel("view"),
            sFirst = oModel.getProperty("/password/first"),
            sSecond = oModel.getProperty("/password/second");
        if (sFirst && sFirst === sSecond && sFirst.length > 6) {
            this.put(USER_BASE_API_PATH + "/password/update", {password: sFirst}, () => {
                this.onClosePasswordDialog();
                MessageBox.information(this.getResourceBundle().getText("txtChangePasswordSuccessInfo"));
            });
        }
        else {
            MessageBox.error(this.getResourceBundle().getText("txtChangePasswordInvalidError"));
        }
    }
});
