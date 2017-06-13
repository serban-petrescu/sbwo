import UIComponent from "sap/ui/core/UIComponent";
import Device from "sap/ui/Device";
import models from "spet/sbwo/web/private/model/models";
import formatter from "spet/sbwo/web/private/model/formatter";
import FileUploader from "sap/ui/unified/FileUploader";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";
import globals from "spet/sbwo/web/private/util/globals";

globals();

let fnSuper = FileUploader.prototype.onAfterRendering;
FileUploader.prototype.onAfterRendering = function () {
    fnSuper.apply(this, arguments);
    if (this.getFileType() && this.getFileType().length && (!this.getMimeType() || !this.getMimeType().length)) {
        jQuery(this.oFileUpload).attr("accept", "." + this.getFileType().join(",."));
    }
};

export default UIComponent.extend("spet.sbwo.web.private.Component", {

    metadata: {
        manifest: "json"
    },

    /**
     * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
     * @public
     * @override
     */
    init() {
        // call the base component's init function
        UIComponent.prototype.init.apply(this, arguments);

        // set the device model
        this.setModel(models.createDeviceModel(), "device");

        // set the context model
        this.setModel(models.createContextModel(), "context");

        // set the user model
        this.setModel(models.createUserModel(), "user");

        // put the bundle in the formatter
        formatter.setResourceBundle(this.getModel("i18n").getResourceBundle());

        // attach the redirect handler
        this.attachLoginRedirector();

        this._fixTargetTitles();
        this.getRouter().attachTitleChanged(oEvent => document.title = oEvent.getParameter("title"));
        this.getRouter().initialize();
    },

    /**
     * Returns the content density class based on the current device.
     * @returns {string}	The name of the CSS class.
     */
    getContentDensityClass() {
        return Device.support.touch ? "sapUiSizeCozy" : "sapUiSizeCompact";
    },

    /**
     * Attaches a login redirect error handler.
     */
    attachLoginRedirector() {
        const sText = this.getModel("i18n").getResourceBundle().getText("txtLoggedOutAutomaticallyText");
        jQuery(document).ajaxError((oHandler, oXhr) => {
            if (parseInt(oXhr.status, 10) === 403) {
                MessageBox.information(sText, {
                    onClose: () => window.location.reload()
                });
            }
        });
    },

    _fixTargetTitles() {
        let oTargets = this.getRouter().getTargets();
        jQuery.each(oTargets._mTargets, (sKey, oTarget) => {
            if (oTarget._oOptions && oTarget._oOptions.title) {
                let oInfo = oTarget._oOptions.title;
                if (oInfo && oInfo.formatter) {
                    oInfo.formatter = jQuery.sap.getObject(oInfo.formatter);
                }
            }
        });
    }
});
