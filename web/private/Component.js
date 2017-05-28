sap.ui.define([
    "sap/ui/core/UIComponent",
    "sap/ui/Device",
    "spet/sbwo/web/model/models",
    "spet/sbwo/web/model/formatter",
    "sap/ui/unified/FileUploader",
    "sap/m/MessageBox"
], function(UIComponent, Device, models, formatter, FileUploader, MessageBox) {
    "use strict";

    var fnSuper = FileUploader.prototype.onAfterRendering;
    FileUploader.prototype.onAfterRendering = function() {
        fnSuper.apply(this, arguments);
        if (this.getFileType() && this.getFileType().length && (!this.getMimeType() || !this.getMimeType().length)){
            jQuery(this.oFileUpload).attr("accept", "." + this.getFileType().join(",."));
        }
    };

    return UIComponent.extend("spet.sbwo.web.Component", {

        metadata: {
            manifest: "json"
        },

        /**
        * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
        * @public
        * @override
        */
        init: function() {
            // call the base component's init function
            UIComponent.prototype.init.apply(this, arguments);

            // set the device model
            this.setModel(models.createDeviceModel(), "device");

            // set the context model
            this.setModel(models.createContextModel(), "context");

            // set the user model
            this.setModel(models.createUserModel(), "user");

            //put the bundle in the formatter
            formatter.setResourceBundle(this.getModel("i18n").getResourceBundle());

            this.attachLoginRedirector();

            this._fixTargetTitles();
            this.getRouter().attachTitleChanged(function(oEvent) {
                document.title = oEvent.getParameter("title");
            });
            this.getRouter().initialize();
        },

        /**
        * Returns the content density class based on the current device.
        * @returns {string}	The name of the CSS class.
        */
        getContentDensityClass: function() {
            return Device.support.touch ? "sapUiSizeCozy" : "sapUiSizeCompact";
        },

        /**
        * Attaches a login redirect error handler.
        */
        attachLoginRedirector: function() {
            var sText = this.getModel("i18n").getResourceBundle().getText("txtLoggedOutAutomaticallyText"),
                fnOnConfirm = function() {
                    window.location.reload();
                },
                fnOnError = function(oHandler, oXhr) {
                    if (parseInt(oXhr.status, 10) === 403) {
                        MessageBox.information(sText, {
                            onClose: fnOnConfirm
                        });
                    }
                };
            jQuery(document).ajaxError(fnOnError);
        },

        _fixTargetTitles: function() {
            var oTargets = this.getRouter().getTargets();

            jQuery.each(oTargets._mTargets, function(sKey, oTarget) {
                var oInfo;
                if (oTarget._oOptions && oTarget._oOptions.title) {
                    oInfo = oTarget._oOptions.title;
                    if (oInfo && oInfo.formatter) {
                        oInfo.formatter = jQuery.sap.getObject(oInfo.formatter);
                    }
                }
            });
        }
    });

});
