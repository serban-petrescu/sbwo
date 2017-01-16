sap.ui.define([
	"./Base",
	"spet/sbwo/web/controller/facade/PersonEdit"
], function(Base, PersonEdit) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/person";
	
	return Base.extend("spet.sbwo.web.controller.entity.Person", jQuery.extend({}, PersonEdit, {
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.getRouter().getRoute("person").attachPatternMatched(this.onRouteMatched, this);
		},
		
		getReadApiUrl: function(sId){
			return sBaseApiPath + "/read/" + sId;
		},
		
		getUpdateApiUrl: function(sId) {
			return sBaseApiPath + "/update/" + sId;
		},
		
		getRestoreApiUrl: function(sId) {
			return sBaseApiPath + "/restore/" + sId;
		},
		
		getDeleteApiUrl: function(sId) {
			return sBaseApiPath + "/delete/" + sId;
		},
		
		getEntityListRoute: function() {
			return "person-list";
		},
		
		onExport: function() {
			window.location.assign(sBaseApiPath + "/export/" + this.getModel("view").getProperty("/id"));
		}
	}));

});