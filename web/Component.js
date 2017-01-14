sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"spet/sbwo/web/model/models",
	"spet/sbwo/web/model/formatter",
	"sap/ui/unified/FileUploader"
], function(UIComponent, Device, models, formatter, FileUploader) {
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
			
			//put the bundle in the formatter
			formatter.setResourceBundle(this.getModel("i18n").getResourceBundle());
			
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
		
		_fixTargetTitles: function() {
			var oTargets = this.getRouter().getTargets(),
				oTarget,
				oInfo;
			oTarget = oTargets.getTarget("person");
			if (oTarget._oTitleProvider && oTarget._oTitleProvider.getBindingInfo) {
				oInfo = oTarget._oTitleProvider.getBindingInfo("title");
				if (oInfo) {
					oInfo.formatter = jQuery.sap.getObject(oInfo.formatter);
				}
			}
		}
	});

});