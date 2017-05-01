sap.ui.define([
	"./BaseWithTabs",
	"spet/sbwo/web/model/formatter"
], function(BaseWithTabs, formatter) {
	"use strict";
	
	return BaseWithTabs.extend("spet.sbwo.web.controller.list.ExpertiseList", {
		getEntityRoute: function() {
			return "expertise";
		},
		
		getListRoute: function() {
			return "expertise-list";
		},
		
		getUserAttribute: function() {
			return "Responsible";
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