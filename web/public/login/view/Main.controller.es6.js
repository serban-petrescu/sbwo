import Controller from "sap/ui/core/mvc/Controller";
import Device from "sap/ui/Device";
import ResourceModel from "sap/ui/model/resource/ResourceModel";
import JSONModel from "sap/ui/model/json/JSONModel";

/**
 * Login controller class.
 * @class
 */
export default Controller.extend("spet.sbwo.web.public.login.view.Main", {
    onInit() {
        this.initializeModels();
        this.initializeStyleClass();
        window.document.title = this.getView().getModel("i18n").getProperty("pageTitle");
    },

    initializeModels() {
        let oModel = new ResourceModel({
            bundleUrl: "i18n/i18n.properties"
        });
        this.getView().setModel(oModel, "i18n");
        this.getView().setModel(new JSONModel({
            error: window.location.hash === "#/error",
            localhost: /localhost:\d+/.test(window.location.host),
        }), "view");
    },

    initializeStyleClass() {
        if (!Device.support.touch) {
            this.getView().addStyleClass("sapUiSizeCompact");
        } else {
            this.getView().addStyleClass("sapUiSizeCozy");
        }
    },

    onLogin() {
        this.byId("hbxMain").$().parent().submit();
    }
});
