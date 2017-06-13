sap.ui.define(["spet/sbwo/web/private/controller/Base", "sap/m/MessageBox", "jquery.sap.global"], function (_Base, _MessageBox, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.Customizing", {
        onInit: function onInit() {
            var _this = this;

            var oBundle = this.getResourceBundle(),
                sClass = this.getOwnerComponent().getContentDensityClass();

            this.buildViewModel({
                busy: false,
                destinations: {
                    location: {
                        title: oBundle.getText("btnCustomizingUploadLocationsText"),
                        icon: "sap-icon://excel-attachment",
                        tooltip: oBundle.getText("btnCustomizingUploadLocationsText"),
                        dialog: {
                            target: "/private/api/rest/import/locations/{0}/{1}",
                            text: oBundle.getText("btnCustomizingUploadLocationsDescription"),
                            separator: ",",
                            header: false
                        }
                    },
                    court: {
                        title: oBundle.getText("btnCustomizingUploadCourtsText"),
                        icon: "sap-icon://excel-attachment",
                        tooltip: oBundle.getText("btnCustomizingUploadCourtsText"),
                        dialog: {
                            target: "/private/api/rest/import/courts/{0}/{1}",
                            text: oBundle.getText("btnCustomizingUploadCourtsDescription"),
                            separator: ",",
                            header: false
                        }
                    }
                }
            });

            this.getOwnerComponent().getModel().securityTokenAvailable().then(function (sToken) {
                return _this.getModel("view").setProperty("/securityToken", sToken);
            });

            this.byId("pagCustomizing").getDependents().forEach(function (oD) {
                return oD.addStyleClass(sClass);
            });
        },
        formatUploadUrl: function formatUploadUrl(sPattern, sSeparator, bHeader) {
            if (sPattern) {
                return _jquerySap2.default.sap.formatMessage(sPattern, [encodeURIComponent(sSeparator), bHeader + ""]);
            } else {
                return "";
            }
        },
        onOpenUploadSheet: function onOpenUploadSheet(oEvent) {
            this.byId("actUploadDestination").openBy(oEvent.getSource());
        },
        onOpenUploadDialog: function onOpenUploadDialog(oEvent) {
            var sPath = oEvent.getSource().getBindingContext("view").getPath(),
                oDialog = this.byId("dlgUpload");
            oDialog.bindElement({
                model: "view",
                path: sPath + "/dialog"
            });
            oDialog.open();
        },
        onCloseUploadDialog: function onCloseUploadDialog() {
            this.byId("dlgUpload").close();
        },
        onUploadStart: function onUploadStart() {
            this.getModel("view").setProperty("/busy", true);
            this.byId("fupCustomizing").upload();
        },
        onUploadComplete: function onUploadComplete(oEvent) {
            var oBundle = this.getResourceBundle();
            this.getModel("view").setProperty("/busy", false);
            if (oEvent.getParameter("status") === 204) {
                _MessageBox2.default.success(oBundle.getText("txtCustomizingUploadSuccessText"));
            } else {
                this.onInternalServerError(oEvent.getParameter("responseRaw"));
            }
            this.getModel().refresh();
            this.byId("dlgUpload").close();
        }
    });
    return exports.default;
});
//# sourceMappingURL=Customizing.controller.js.map
