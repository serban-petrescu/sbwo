sap.ui.define([
	"spet/sbwo/web/util/Period",
	"spet/sbwo/web/util/Duration",
	"sap/ui/model/json/JSONModel",
	"sap/ui/Device",
	"./user"
], function(Period, Duration, JSONModel, Device, user) {
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
		},
		
		createUserModel: function() {
			var oModel = new JSONModel(user);
			oModel.setDefaultBindingMode("OneWay");
			return oModel;
		}

	};

});