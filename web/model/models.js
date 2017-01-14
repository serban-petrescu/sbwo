sap.ui.define([
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device"
], function(JSONModel, Device) {
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