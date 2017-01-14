sap.ui.define([
	"./Base"
], function(Base) {
	"use strict";
	
	return Base.extend("spet.sbwo.web.controller.list.DeletedEntityList", {
		onInit: function() {
			Base.prototype.onInit.apply(this, arguments);
			this.buildViewModel({
				selection:	false
			});
		},
		
		getEntityRoute: function(oContext) {
			return this.getRouterForEntityType(oContext.getProperty("Type"));
		},
		
		getListRoute: function() {
			return "deleted-list";
		},
		
		onRouteMatched: function() {
			Base.prototype.onRouteMatched.apply(this, arguments);
			this.clearSelection();
		},
		
		onSelectionChange: function(oEvent) {
			this.getModel("view").setProperty("/selection", oEvent.getSource().getSelectedItems().length > 0);
		},
		
		getSelectionEntities: function() {
			var aItems = this.byId("lstMain").getSelectedItems(),
				aData = aItems.map(function(oItem) {
					var oContext = oItem.getBindingContext();
					return {
						id:	oContext.getProperty("Id"),
						type: oContext.getProperty("Type")
					};
				});
			return aData;
		},
		
		onDeleteSelected: function() {
			var oModel = this.getModel(),
				fnClear = this.clearSelection.bind(this);
			jQuery.ajax({
				method: "DELETE",
				url: "/private/api/rest/trash/delete",
				contentType: "application/json",
				data: JSON.stringify(this.getSelectionEntities()),
				success: function() {
					oModel.refresh();
					fnClear();
				}
			});
		},
		
		onRestoreSelected: function() {
			var oModel = this.getModel(),
				fnClear = this.clearSelection.bind(this);
			jQuery.ajax({
				method: "PUT",
				url: "/private/api/rest/trash/restore",
				contentType: "application/json",
				data: JSON.stringify(this.getSelectionEntities()),
				success: function() {
					oModel.refresh();
					fnClear();
				}
			});
		},
		
		onDeleteAll: function() {
			var oModel = this.getModel(),
				fnClear = this.clearSelection.bind(this);
			jQuery.ajax({
				method: "DELETE",
				url: "/private/api/rest/trash/delete/all",
				success: function() {
					oModel.refresh();
					fnClear();
				}
			});
		},
		
		clearSelection: function(){
			this.byId("lstMain").removeSelections(true);
			this.getModel("view").setProperty("/selection", false);
		}
		
	});

});