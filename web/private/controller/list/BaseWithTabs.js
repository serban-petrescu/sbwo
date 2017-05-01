sap.ui.define([
	"./Base",
	"sap/ui/model/Filter"
], function(Base, Filter) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.BaseWithTabs", {
		
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this._buildTabsModel();
		},
		
		getUserAttribute: function() {
			return "User";
		},
		
		getAllFilters: function() {
			var aFilters = Base.prototype.getAllFilters.apply(this, arguments),
				sKey = this.getModel("view").getProperty("/currentTab");
			if (sKey !== "all") {
				aFilters.push(new Filter(this.getUserAttribute(), "EQ", parseInt(sKey, 10)));
			}
			return aFilters;
		},
		
		onTabChanged: function() {
			this._filterList();
		},
		
		_buildTabsModel: function() {
			var oUserModel = this.getModel("user"),
				iUserId = oUserModel.getProperty("/id"),
				oViewModel = this.getModel("view"),
				fnMapOther = function(oOther) {
					return {
						key: oOther.id,
						title: oOther.username
					};
				},
				aOthers = oUserModel.getProperty("/others"),
				aTabs = aOthers.map(fnMapOther).concat([{
					key: "all",
					title: this.getResourceBundle().getText("itfAllUsersTitle")
				}, {
					key: iUserId,
					title: oUserModel.getProperty("/username")
				}]);
			oViewModel.setProperty("/tabs", aTabs);
			oViewModel.setProperty("/currentTab", iUserId);
		}
		
	});

});