sap.ui.define([
	"spet/sbwo/web/controller/entity/Base",
	"spet/sbwo/web/util/TimeOfDay",
	"sap/ui/core/format/DateFormat"
], function(Base, TimeOfDay, DateFormat) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/utility/file";
	
	return Base.extend("spet.sbwo.web.controller.misc.ServerSettings", {
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.getRouter().getRoute("server-settings").attachPatternMatched(this.onRouteMatched, this);
			this.getRouter().getRoute("server-settings").attachPatternMatched(this.onReadLogs, this);
			this.getRouter().getRoute("server-settings").attachPatternMatched(this.onReadSchedules, this);
		},
		
		getReadApiUrl: function(){
			return sBaseApiPath + "/config/read";
		},
		
		getUpdateApiUrl: function() {
			return sBaseApiPath + "/config/update";
		},
		
		formatLogColorFromLevel: function(sLevel) {
			switch(sLevel) {
			case "ERROR": return "Negative";
			case "WARN": return "Critical";
			default: return "Neutral";
			}
		},
		
		formatTimestampDate: function(iTs) {
			return DateFormat.getDateTimeInstance({style: "short"}).format(new Date(iTs));
		},
		
		formatScheduleType: function(sType) {
			var oBundle = this.getResourceBundle();
			switch(sType) {
			case "BACKUP": return oBundle.getText("txtScheduleTypeBackupText");
			case "CLEANUP": return oBundle.getText("txtScheduleTypeCleanupText");
			default: return oBundle.getText("txtScheduleTypeUnknownText");
			}
		},
		
		onDatabaseBackupLocationVh: function(oEvent) {
			var fnOnSelect = function(oE) {
				var aSels = oE.getParameter("result");
				if (aSels.length) {
					this.getModel("data").setProperty("/databaseBackupLocation", aSels[0]);
					this.onValueChanged();
				}
			};
			this.byId("viewFileExporer").getController()
				.multiselect(false)
				.current(oEvent.getSource().getValue())
				.folder()
				.attachEventOnce("select", fnOnSelect, this)
				.open();
		},
		
		onSearchLogs: function(oEvent) {
			var sQuery = oEvent.getParameter("query"),
				oList = this.byId("lstLogs");
			this.applyContainsFilter(sQuery, "name", oList.getBinding("items"));
			if (oEvent.getParameter("refreshButtonPressed")) {
				this.onReadLogs();
			}
		},
		
		onSearchLogEvents: function(oEvent) {
			var sQuery = oEvent.getParameter("query"),
				oList = this.byId("lstLogEvents");
			this.applyContainsFilter(sQuery, "message", oList.getBinding("items"));
			if (oEvent.getParameter("refreshButtonPressed")) {
				this.onReadLogEvents(this.getModel("view").getProperty("/log/name"));
			}
		},
		
		onShowLogEvents: function(oEvent) {
			var sName = oEvent.getSource().getBindingContext("view").getProperty("name");
			this.getModel("view").setProperty("/log", {name: sName, events: []});
			this.byId("navLogs").to(this.byId("sccLogEvents"));
			this.onReadLogEvents(sName);
		},
		
		onShowLogList: function() {
			this.byId("navLogs").back();
		},
		
		onReadLogs: function() {
			var oModel = this.getModel("view");
			jQuery.ajax({
				url: sBaseApiPath + "/logs",
				method: "GET",
				success: function(oData) {
					oModel.setProperty("/logs", oData);
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onReadLogEvents: function(sName) {
			var oModel = this.getModel("view");
			jQuery.ajax({
				url: sBaseApiPath + "/log/" + sName,
				method: "GET",
				success: function(oData) {
					oModel.setProperty("/log/events", oData);
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onReadSchedules: function() {
			var oModel = this.getModel("view");
			jQuery.ajax({
				url: sBaseApiPath + "/schedules",
				method: "GET",
				success: function(oData) {
					oModel.setProperty("/schedules", oData);
				},
				error: this.onRestApiError.bind(this)
			});
		}
	});

});