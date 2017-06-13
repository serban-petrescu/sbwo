sap.ui.define(["sap/ui/core/UIComponent", "sap/ui/Device", "spet/sbwo/web/private/model/models", "spet/sbwo/web/private/model/formatter", "sap/ui/unified/FileUploader", "sap/m/MessageBox", "jquery.sap.global", "spet/sbwo/web/private/util/globals"], function (_UIComponent, _Device, _models, _formatter, _FileUploader, _MessageBox, _jquerySap, _globals) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _UIComponent2 = _interopRequireDefault(_UIComponent);

    var _Device2 = _interopRequireDefault(_Device);

    var _models2 = _interopRequireDefault(_models);

    var _formatter2 = _interopRequireDefault(_formatter);

    var _FileUploader2 = _interopRequireDefault(_FileUploader);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    var _globals2 = _interopRequireDefault(_globals);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    (0, _globals2.default)();

    var fnSuper = _FileUploader2.default.prototype.onAfterRendering;
    _FileUploader2.default.prototype.onAfterRendering = function () {
        fnSuper.apply(this, arguments);
        if (this.getFileType() && this.getFileType().length && (!this.getMimeType() || !this.getMimeType().length)) {
            (0, _jquerySap2.default)(this.oFileUpload).attr("accept", "." + this.getFileType().join(",."));
        }
    };

    exports.default = _UIComponent2.default.extend("spet.sbwo.web.private.Component", {

        metadata: {
            manifest: "json"
        },

        /**
         * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
         * @public
         * @override
         */
        init: function init() {
            // call the base component's init function
            _UIComponent2.default.prototype.init.apply(this, arguments);

            // set the device model
            this.setModel(_models2.default.createDeviceModel(), "device");

            // set the context model
            this.setModel(_models2.default.createContextModel(), "context");

            // set the user model
            this.setModel(_models2.default.createUserModel(), "user");

            // put the bundle in the formatter
            _formatter2.default.setResourceBundle(this.getModel("i18n").getResourceBundle());

            // attach the redirect handler
            this.attachLoginRedirector();

            this._fixTargetTitles();
            this.getRouter().attachTitleChanged(function (oEvent) {
                return document.title = oEvent.getParameter("title");
            });
            this.getRouter().initialize();
        },


        /**
         * Returns the content density class based on the current device.
         * @returns {string}	The name of the CSS class.
         */
        getContentDensityClass: function getContentDensityClass() {
            return _Device2.default.support.touch ? "sapUiSizeCozy" : "sapUiSizeCompact";
        },


        /**
         * Attaches a login redirect error handler.
         */
        attachLoginRedirector: function attachLoginRedirector() {
            var sText = this.getModel("i18n").getResourceBundle().getText("txtLoggedOutAutomaticallyText");
            (0, _jquerySap2.default)(document).ajaxError(function (oHandler, oXhr) {
                if (parseInt(oXhr.status, 10) === 403) {
                    _MessageBox2.default.information(sText, {
                        onClose: function onClose() {
                            return window.location.reload();
                        }
                    });
                }
            });
        },
        _fixTargetTitles: function _fixTargetTitles() {
            var oTargets = this.getRouter().getTargets();
            _jquerySap2.default.each(oTargets._mTargets, function (sKey, oTarget) {
                if (oTarget._oOptions && oTarget._oOptions.title) {
                    var oInfo = oTarget._oOptions.title;
                    if (oInfo && oInfo.formatter) {
                        oInfo.formatter = _jquerySap2.default.sap.getObject(oInfo.formatter);
                    }
                }
            });
        }
    });
    return exports.default;
});
//# sourceMappingURL=Component.js.map
