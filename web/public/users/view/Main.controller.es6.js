import Controller from "sap/ui/core/mvc/Controller";
import Device from "sap/ui/Device";
import ResourceModel from "sap/ui/model/resource/ResourceModel";
import JSONModel from "sap/ui/model/json/JSONModel";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";

const API_BASE_PATH = "/public/rest/user/manage";

/**
 * Login controller class.
 * @class
 */
export default Controller.extend("spet.sbwo.web.public.users.view.Main", {
    onInit() {
        this.initializeModels();
        this.initializeStyleClass();
        this.onRefresh();
        window.document.title = this.getView().getModel("i18n").getProperty("pageTitle");
    },

    initializeModels() {
        let oModel = new ResourceModel({
            bundleUrl: "i18n/i18n.properties"
        });
        this.getView().setModel(oModel, "i18n");
        this.getView().setModel(new JSONModel({
            users: [],
            current: null,
            newUsername: ""
        }), "view");
    },

    initializeStyleClass() {
        const sClass = Device.support.touch ? "sapUiSizeCozy" : "sapUiSizeCompact";
        this.getView().addStyleClass(sClass);
        this.byId("dlgCreate").addStyleClass(sClass);
    },

    onRefresh() {
        let oModel = this.getView().getModel("view");
        jQuery.ajax({
            url: API_BASE_PATH + "/list",
            method: "GET",
            dataType: "json",
            success: oData => {
                oModel.setProperty("/users", oData);
                oModel.setProperty("/current", null);
                this.byId("lstUsers").removeSelections(true);
            },
            error: this.onError.bind(this)
        });
    },

    onError(oXhr, sStatus, sError) {
        const i18n = this.getView().getModel("i18n");
        MessageBox.error(i18n.getProperty("txtErrorMessageText"), {
            details: sError
        });
    },

    onSelect(oEvent) {
        let oModel = this.getView().getModel("view"),
            oItem = oEvent.getParameter("listItem");
        if (oEvent.getParameter("selected") && oItem) {
            let oUser = oItem.getBindingContext("view").getObject();
            oModel.setProperty("/current", oUser);
        } else {
            oModel.setProperty("/current", null);
        }
    },

    onToggleActivation() {
        let oModel = this.getView().getModel("view");
        jQuery.ajax({
            url: API_BASE_PATH + "/activate",
            method: "POST",
            data: {
                username: oModel.getProperty("/current/username"),
                active: !oModel.getProperty("/current/active")
            },
            success: this.onRefresh.bind(this),
            error: this.onError.bind(this)
        });
    },

    onResetPassword() {
        let oModel = this.getView().getModel("view"),
            i18n = this.getView().getModel("i18n");
        jQuery.ajax({
            url: API_BASE_PATH + "/reset",
            method: "POST",
            data: {
                username: oModel.getProperty("/current/username")
            },
            success: () => MessageBox.success(i18n.getProperty("txtPasswordResetText")),
            error: this.onError.bind(this)
        });
    },

    onOpenCreateDialog() {
        this.getView().getModel("view").setProperty("/newUsername", "");
        this.byId("dlgCreate").open();
    },

    onCloseCreateDialog() {
        this.byId("dlgCreate").close();
    },

    onRegisterUser() {
        let oModel = this.getView().getModel("view");
        jQuery.ajax({
            url: API_BASE_PATH + "/register",
            method: "POST",
            data: {
                username: oModel.getProperty("/newUsername")
            },
            success: oData => {
                this.onCloseCreateDialog();
                this.onRefresh();
            },
            error: this.onError.bind(this)
        });
    },

    onNavBack() {
        window.history.go(-1);
    }
});
