sap.ui.define([
	"./Base"
], function(Base) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.ExpertiseList", {
		getEntityRoute: function() {
			return "expertise";
		},
		
		getListRoute: function() {
			return "expertise-list";
		}
		
	});

});