sap.ui.define([
    "spet/sbwo/web/controller/entity/Base",
    "sap/m/MessageBox"
], function(Base, MessageBox) {
    "use strict";

    var sBaseApiPath = "/private/api/rest/user";

    return Base.extend("spet.sbwo.web.controller.misc.UserSettings", {
        onInit: function() {
            Base.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("user-settings").attachPatternMatched(this.onRouteMatched, this);
            this.byId("dlgChangePassword").addStyleClass(this.getOwnerComponent().getContentDensityClass());
            this.getModel("view").setProperty("/password", {first: "", second: ""});
        },

        getReadApiUrl: function(){
            return sBaseApiPath + "/preference/read";
        },

        getUpdateApiUrl: function() {
            return sBaseApiPath + "/preference/update";
        },

        formatLanguageText: function(sCode) {
            var oBundle = this.getResourceBundle();
            switch((sCode || "").toLowerCase()) {
            case "en": return oBundle.getText("txtLanguageEnglishText");
            case "ro": return oBundle.getText("txtLanguageRomanianText");
            default: return "";
            }
        },

        formatThemeText: function(sId) {
            var oBundle = this.getResourceBundle();
            switch((sId || "").toLowerCase()) {
            case "sap_belize": return oBundle.getText("txtThemeBelizeText");
            case "sap_bluecrystal": return oBundle.getText("txtThemeBlueCrystalText");
            case "sap_hcb": return oBundle.getText("txtThemeHighContrastBlackText");
            default: return "";
            }
        },

        onSaveSuccess: function() {
            Base.prototype.onSaveSuccess.apply(this, arguments);
            MessageBox.information(this.getResourceBundle().getText("txtUserSettingsUpdatedRefreshInfo"));
        },

        onOpenPasswordDialog: function() {
            this.byId("dlgChangePassword").open();
        },

        onClosePasswordDialog: function() {
            this.getModel("view").setProperty("/password", {first: "", second: ""});
            this.byId("dlgChangePassword").close();
        },

        onChangePassword: function() {
            var oModel = this.getModel("view"),
                sFirst = oModel.getProperty("/password/first"),
                sSecond = oModel.getProperty("/password/second"),
                fnSuccess = function() {
                    this.onClosePasswordDialog();
                    MessageBox.information(this.getResourceBundle().getText("txtChangePasswordSuccessInfo"));
                };
            if (sFirst && sFirst === sSecond && sFirst.length > 6) {
                this.put(sBaseApiPath + "/password/update", {password: sFirst}, fnSuccess);
            }
            else {
                MessageBox.error(this.getResourceBundle().getText("txtChangePasswordInvalidError"));
            }
        }
    });

});
