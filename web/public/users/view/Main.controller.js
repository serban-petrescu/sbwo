sap.ui.define(["sap/ui/core/mvc/Controller", "sap/ui/Device", "sap/ui/model/resource/ResourceModel", "sap/ui/model/json/JSONModel", "sap/m/MessageBox", "jquery.sap.global"], function (_Controller, _Device, _ResourceModel, _JSONModel, _MessageBox, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Controller2 = _interopRequireDefault(_Controller);

    var _Device2 = _interopRequireDefault(_Device);

    var _ResourceModel2 = _interopRequireDefault(_ResourceModel);

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var API_BASE_PATH = "/public/rest/user/manage";

    /**
     * Login controller class.
     * @class
     */
    exports.default = _Controller2.default.extend("spet.sbwo.web.public.users.view.Main", {
        onInit: function onInit() {
            this.initializeModels();
            this.initializeStyleClass();
            this.onRefresh();
            window.document.title = this.getView().getModel("i18n").getProperty("pageTitle");
        },
        initializeModels: function initializeModels() {
            var oModel = new _ResourceModel2.default({
                bundleUrl: "i18n/i18n.properties"
            });
            this.getView().setModel(oModel, "i18n");
            this.getView().setModel(new _JSONModel2.default({
                users: [],
                current: null,
                newUsername: ""
            }), "view");
        },
        initializeStyleClass: function initializeStyleClass() {
            var sClass = _Device2.default.support.touch ? "sapUiSizeCozy" : "sapUiSizeCompact";
            this.getView().addStyleClass(sClass);
            this.byId("dlgCreate").addStyleClass(sClass);
        },
        onRefresh: function onRefresh() {
            var _this = this;

            var oModel = this.getView().getModel("view");
            _jquerySap2.default.ajax({
                url: API_BASE_PATH + "/list",
                method: "GET",
                dataType: "json",
                success: function success(oData) {
                    oModel.setProperty("/users", oData);
                    oModel.setProperty("/current", null);
                    _this.byId("lstUsers").removeSelections(true);
                },
                error: this.onError.bind(this)
            });
        },
        onError: function onError(oXhr, sStatus, sError) {
            var i18n = this.getView().getModel("i18n");
            _MessageBox2.default.error(i18n.getProperty("txtErrorMessageText"), {
                details: sError
            });
        },
        onSelect: function onSelect(oEvent) {
            var oModel = this.getView().getModel("view"),
                oItem = oEvent.getParameter("listItem");
            if (oEvent.getParameter("selected") && oItem) {
                var oUser = oItem.getBindingContext("view").getObject();
                oModel.setProperty("/current", oUser);
            } else {
                oModel.setProperty("/current", null);
            }
        },
        onToggleActivation: function onToggleActivation() {
            var oModel = this.getView().getModel("view");
            _jquerySap2.default.ajax({
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
        onResetPassword: function onResetPassword() {
            var oModel = this.getView().getModel("view"),
                i18n = this.getView().getModel("i18n");
            _jquerySap2.default.ajax({
                url: API_BASE_PATH + "/reset",
                method: "POST",
                data: {
                    username: oModel.getProperty("/current/username")
                },
                success: function success() {
                    return _MessageBox2.default.success(i18n.getProperty("txtPasswordResetText"));
                },
                error: this.onError.bind(this)
            });
        },
        onOpenCreateDialog: function onOpenCreateDialog() {
            this.getView().getModel("view").setProperty("/newUsername", "");
            this.byId("dlgCreate").open();
        },
        onCloseCreateDialog: function onCloseCreateDialog() {
            this.byId("dlgCreate").close();
        },
        onRegisterUser: function onRegisterUser() {
            var _this2 = this;

            var oModel = this.getView().getModel("view");
            _jquerySap2.default.ajax({
                url: API_BASE_PATH + "/register",
                method: "POST",
                data: {
                    username: oModel.getProperty("/newUsername")
                },
                success: function success(oData) {
                    _this2.onCloseCreateDialog();
                    _this2.onRefresh();
                },
                error: this.onError.bind(this)
            });
        },
        onNavBack: function onNavBack() {
            window.history.go(-1);
        }
    });
    return exports.default;
});
//# sourceMappingURL=Main.controller.js.map
