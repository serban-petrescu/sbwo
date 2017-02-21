sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/ui/model/Sorter",
	"sap/m/GroupHeaderListItem"
], function(Base, Sorter, GroupHeaderListItem) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.Base", {
		_groupHeaderFormatter: null,
		
		getEntityRoute: function(/*oContext*/){},
		
		getListRoute: function(){},
		
		getDefaultSortSettings: function() {
			return [];
		},
		
		getFormatterForColumn: function(/* sColumn */) {
			return null;
		},
		
		onInit: function() {
			this.getRouter().getRoute(this.getListRoute()).attachPatternMatched(this.onRouteMatched, this);
			this.buildViewModel({settings: {applied: false}});
			if (this.getViewSettingsDialog()) {
				this.getViewSettingsDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
			}
		},
		
		getViewSettingsDialog: function() {
			return this.byId("vsdMain");
		},
		
		getList: function() {
			return this.byId("lstMain");
		},
		
		getSearchField: function() {
			return this.byId("seaMain");
		},
		
		getSearchAttribute: function() {
			return "Search";
		},
		
		onSearch: function(oEvent) {
			this.getRouter().navTo(this.getListRoute(), {
				search: oEvent.getParameter("query")
			}, true);
		},
		
		onRouteMatched: function(oEvent) {
			var sQuery = oEvent.getParameter("arguments").search || "",
				oField = this.getSearchField();
			if (oField.getValue() !== sQuery) {
				oField.setValue(sQuery);
			}
			this._filterList(sQuery);
		},
		
		onPressItem: function(oEvent) {
			var oContext = oEvent.getSource().getBindingContext();
			this.getRouter().navTo(this.getEntityRoute(oContext), {
				id: oContext.getProperty("Id")
			});
		},
		
		onResetViewSettings: function() {
			var aSorters = this.getDefaultSortSettings().map(function(oSettings){
				return new Sorter(oSettings.path, oSettings.descending, oSettings.group);
			});
			this.getModel("view").setData({settings: {applied: false}});
			this.getList().getBinding("items").sort(aSorters);
		},
		
		onOpenViewSettingsDialog: function() {
			this.getViewSettingsDialog().open();
		},
		
		onViewSettingsConfirm: function(oEvent) {
			var aSorters = [],
				oGroup = oEvent.getParameter("groupItem"),
				oSort = oEvent.getParameter("sortItem");
			if (oGroup) {
				aSorters.push(new Sorter(oGroup.getKey(), oEvent.getParameter("groupDescending"), true));
				this._groupHeaderFormatter = this.getFormatterForColumn(oGroup.getKey());
			}
			if (oSort) {
				aSorters.push(new Sorter(oSort.getKey(), oEvent.getParameter("sortDescending"), false));
			}
			this.getList().getBinding("items").sort(aSorters);
			this.getModel("view").setProperty("/settings/applied", true);
		},
		
		buildGroupHeader: function(oGroup) {
			var sTitle = this._groupHeaderFormatter ? this._groupHeaderFormatter(oGroup.key) : oGroup.key;
			return new GroupHeaderListItem({title: sTitle});
		},
		
		_filterList: function(sQuery) {
			this.applySearchFilter(sQuery, this.getSearchAttribute(), this.getList().getBinding("items"));
		}
		
	});

});