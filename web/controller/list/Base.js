sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/ui/model/Sorter",
	"sap/ui/model/Filter",
	"sap/m/GroupHeaderListItem"
], function(Base, Sorter, Filter, GroupHeaderListItem) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.Base", {
		_groupHeaderFormatter: null,
		_filters: [],
		_query: "",
		
		getEntityRoute: function(/*oContext*/){},
		
		getListRoute: function(){},
		
		getDefaultSortSettings: function() {
			return [];
		},
		
		getCustomFilterForKey: function(/* sKey */) {
			return null;
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
			this._query = sQuery;
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
			this._filters = [];
			this._filterList(this._query);
		},
		
		onOpenViewSettingsDialog: function() {
			this.getViewSettingsDialog().open();
		},
		
		onViewSettingsConfirm: function(oEvent) {
			var fnApplySorting = function() {
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
				},
				
				fnGetExternal = this.getCustomFilterForKey.bind(this),
				
				fnMapParent = function(mKeys, sParentKey) {
					var aFilters = [];
					for (var sKey in mKeys) {
						if (mKeys.hasOwnProperty(sKey) && mKeys[sKey]) {
							aFilters.push(new Filter(sParentKey, "EQ", sKey));
						}
					}
					return aFilters;
				},
				
				fnBuildFilterFromArray = function(bAnd, aFilters) {
					if (aFilters.length === 0) {
						return null;
					}
					else if (aFilters.length === 1) {
						return aFilters[0];
					}
					else {
						return new Filter({filters: aFilters, and: bAnd});
					}
				},
				
				fnComputeFilters = function() {
					var oComposite = oEvent.getParameter("filterCompoundKeys") || {},
						aFilters = [],
						sKey,
						oFilter;
					
					for (sKey in oComposite) {
						if (oComposite.hasOwnProperty(sKey) && sKey) {
							oFilter = fnBuildFilterFromArray(false, fnMapParent(oComposite[sKey], sKey));
							if (oFilter) {
								aFilters.push(oFilter);
							}
						}
					}
					
					if (oComposite[""]) {
						for (sKey in oComposite[""]) {
							if (oComposite[""].hasOwnProperty(sKey) && oComposite[""][sKey]) {
								oFilter = fnGetExternal(sKey);
								if (oFilter) {
									aFilters.push(oFilter);
								}
							}
						}
					}
					
					return aFilters;
				};
				
			fnApplySorting.call(this);
			this._filters = fnComputeFilters();
			this._filterList(this._query);
			this.getModel("view").setProperty("/settings/applied", true);
		},
		
		buildGroupHeader: function(oGroup) {
			var sTitle = this._groupHeaderFormatter ? this._groupHeaderFormatter(oGroup.key) : oGroup.key;
			return new GroupHeaderListItem({title: sTitle});
		},
		
		_filterList: function(sQuery) {
			var aFilters = this.getSearchFilters(sQuery, this.getSearchAttribute()),
				oBinding = this.getList().getBinding("items");
			aFilters.push.apply(aFilters, this._filters);
			if (aFilters.length){
				oBinding.filter(new Filter({
					filters: aFilters,
					and: true
				}));
			}
			else {
				oBinding.filter(aFilters);
			}
		}
		
	});

});