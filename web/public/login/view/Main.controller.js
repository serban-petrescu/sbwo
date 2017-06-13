sap.ui.define(["sap/ui/core/mvc/Controller", "sap/ui/Device", "sap/ui/model/resource/ResourceModel", "sap/ui/model/json/JSONModel"], function (_Controller, _Device, _ResourceModel, _JSONModel) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Controller2 = _interopRequireDefault(_Controller);

    var _Device2 = _interopRequireDefault(_Device);

    var _ResourceModel2 = _interopRequireDefault(_ResourceModel);

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Controller2.default.extend("spet.sbwo.web.public.login.view.Main", {
        onInit: function onInit() {
            this.initializeModels();
            this.initializeStyleClass();
            window.document.title = this.getView().getModel("i18n").getProperty("pageTitle");
        },
        initializeModels: function initializeModels() {
            var oModel = new _ResourceModel2.default({
                bundleUrl: "i18n/i18n.properties"
            });
            this.getView().setModel(oModel, "i18n");
            this.getView().setModel(new _JSONModel2.default({
                error: window.location.hash === "#/error",
                localhost: /localhost:\d+/.test(window.location.host)
            }), "view");
        },
        initializeStyleClass: function initializeStyleClass() {
            if (!_Device2.default.support.touch) {
                this.getView().addStyleClass("sapUiSizeCompact");
            } else {
                this.getView().addStyleClass("sapUiSizeCozy");
            }
        },
        onLogin: function onLogin() {
            this.byId("hbxMain").$().parent().submit();
        }
    });
    return exports.default;
});
//# sourceMappingURL=Main.controller.js.map
