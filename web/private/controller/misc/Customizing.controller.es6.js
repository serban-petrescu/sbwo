import Base from "spet/sbwo/web/private/controller/Base";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.misc.Customizing", {
    onInit() {
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

        this.getOwnerComponent().getModel().securityTokenAvailable()
            .then(sToken => this.getModel("view").setProperty("/securityToken", sToken));

        this.byId("pagCustomizing").getDependents()
            .forEach(oD => oD.addStyleClass(sClass));
    },

    formatUploadUrl(sPattern, sSeparator, bHeader) {
        if (sPattern) {
            return jQuery.sap.formatMessage(sPattern, [encodeURIComponent(sSeparator), bHeader + ""]);
        } else {
            return "";
        }
    },

    onOpenUploadSheet(oEvent) {
        this.byId("actUploadDestination").openBy(oEvent.getSource());
    },

    onOpenUploadDialog(oEvent) {
        var sPath = oEvent.getSource().getBindingContext("view").getPath(),
            oDialog = this.byId("dlgUpload");
        oDialog.bindElement({
            model: "view",
            path: sPath + "/dialog"
        });
        oDialog.open();
    },

    onCloseUploadDialog() {
        this.byId("dlgUpload").close();
    },

    onUploadStart() {
        this.getModel("view").setProperty("/busy", true);
        this.byId("fupCustomizing").upload();
    },

    onUploadComplete(oEvent) {
        var oBundle = this.getResourceBundle();
        this.getModel("view").setProperty("/busy", false);
        if (oEvent.getParameter("status") === 204) {
            MessageBox.success(oBundle.getText("txtCustomizingUploadSuccessText"));
        } else {
            this.onInternalServerError(oEvent.getParameter("responseRaw"));
        }
        this.getModel().refresh();
        this.byId("dlgUpload").close();
    }
});
