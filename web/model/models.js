sap.ui.define([
	"spet/sbwo/web/util/Period",
	"spet/sbwo/web/util/Duration",
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device"
], function(Period, Duration, JSONModel, Device) {
	"use strict";

	return {

		createDeviceModel: function() {
			var oModel = new JSONModel(Device);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		},
		
		createContextModel: function() {
			var oModel = new JSONModel(jQuery.sap.getModulePath("spet.sbwo.web.model") + "/context.json");
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		}

	};

});