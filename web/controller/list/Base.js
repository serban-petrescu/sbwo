sap.ui.define([
	"spet/sbwo/web/controller/Base"
], function(Base) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.Base", {
		
		getEntityRoute: function(/*oContext*/){},
		
		getListRoute: function(){},
		
		onInit: function() {
			this.getRouter().getRoute(this.getListRoute()).attachPatternMatched(this.onRouteMatched, this);
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
		
		_filterList: function(sQuery) {
			this.applySearchFilter(sQuery, this.getSearchAttribute(), this.getList().getBinding("items"));
		}
		
	});

});