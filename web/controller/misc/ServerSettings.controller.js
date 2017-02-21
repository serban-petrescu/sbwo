sap.ui.define([
	"spet/sbwo/web/controller/entity/Base",
	"sap/ui/core/format/DateFormat",
	"sap/m/MessageBox"
], function(Base, DateFormat, MessageBox) {
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
		
		formatScheduleType: function(sType) {
			var oBundle = this.getResourceBundle();
			switch(sType) {
			case "BACKUP": return oBundle.getText("txtScheduleTypeBackupText");
			case "CLEANUP": return oBundle.getText("txtScheduleTypeCleanupText");
			case "SESSION_CACHE": return oBundle.getText("txtScheduleTypeSessionFlushText");
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
			var fnSuccesss = function(oData) {
				this.getModel("view").setProperty("/logs", oData);
			};
			this.get(sBaseApiPath + "/logs", fnSuccesss);
		},
		
		onReadLogEvents: function(sName) {
			var fnSuccesss = function(oData) {
				this.getModel("view").setProperty("/log/events", oData);
			};
			this.get(sBaseApiPath + "/log/" + sName, fnSuccesss);
		},
		
		onReadSchedules: function() {
			var fnSuccesss = function(oData) {
				this.getModel("view").setProperty("/schedules", oData);
			};
			this.get(sBaseApiPath + "/schedules/read", fnSuccesss);
		},
		
		onSaveSuccess: function() {
			Base.prototype.onSaveSuccess.apply(this, arguments);
			MessageBox.information(this.getResourceBundle().getText("txtServerSettingsUpdatedRefreshInfo"));
		}
	});

});