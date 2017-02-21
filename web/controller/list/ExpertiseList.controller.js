sap.ui.define([
	"./Base",
	"spet/sbwo/web/model/formatter"
], function(Base, formatter) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.ExpertiseList", {
		getEntityRoute: function() {
			return "expertise";
		},
		
		getListRoute: function() {
			return "expertise-list";
		},
		
		getDefaultSortSettings: function() {
			return [{
				path: "NextHearing",
				descending: true,
				group: false
			}];
		},
		
		getFormatterForColumn: function(sColumn) {
			return sColumn === "Status" ? formatter.expertiseStatus : null;
		}
		
	});

});