sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageBox",
	"sap/ui/core/routing/HashChanger",
	"spet/sbwo/web/util/DraftService",
	"sap/ui/core/format/DateFormat"
], function(Base, JSONModel, MessageBox, HashChanger, DraftService, DateFormat) {
	"use strict";
	
	var oDtFormat = DateFormat.getDateTimeInstance({style: "short"});
	
	return Base.extend("spet.sbwo.web.controller.entity.Base", {
		
		getReadApiUrl: function(/* sId */){},
		
		getUpdateApiUrl: function(/* sId */) {},
		
		getRestoreApiUrl: function(/* sId */) {},
		
		getDeleteApiUrl: function(/* sId */) {},
		
		getEntityListRoute: function() {},
		
		onInit: function() {
			this.buildViewModel({
				edit: false,
				draft: "Clear",
				id: null
			});
			this.setModel(new JSONModel({}), "data");
		},
		
		onSave: function() {
			var oViewModel = this.getModel("view"),
				oDataModel = this.getModel("data");
			jQuery.ajax({
				url:	this.getUpdateApiUrl(oViewModel.getProperty("/id")),
				method: "PUT",
				contentType: "application/json",
				data:	JSON.stringify(oDataModel.getData()),
				dataType: "json",
				success: function(oData) {
					oDataModel.setData(oData);
					oViewModel.setProperty("/edit", false);
					oViewModel.setProperty("/draft", "Clear");
					DraftService.remove(HashChanger.getInstance().getHash());
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onRestore: function() {
			var oViewModel = this.getModel("view"),
				oDataModel = this.getModel("data");
			jQuery.ajax({
				url:	this.getRestoreApiUrl(oViewModel.getProperty("/id")),
				method: "PUT",
				dataType: "json",
				success: function(oData) {
					oDataModel.setData(oData);
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onReset: function() {
			this.onRead();
			DraftService.remove(HashChanger.getInstance().getHash());
		},
		
		onRead: function() {
			var oViewModel = this.getModel("view"),
				oDataModel = this.getModel("data");
			jQuery.ajax({
				url:	this.getReadApiUrl(oViewModel.getProperty("/id")),
				method: "GET",
				dataType: "json",
				success: function(oData) {
					oDataModel.setData(oData);
					oViewModel.setProperty("/draft", "Clear");
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onDelete: function() {
			var oViewModel = this.getModel("view");
			jQuery.ajax({
				url:	this.getDeleteApiUrl(oViewModel.getProperty("/id")),
				method: "DELETE",
				success: function() {
					oViewModel.setProperty("/draft", "Clear");
					window.history.go(-1);
				},
				error: this.onRestApiError.bind(this)
			});
		},
		
		onValueChanged: function() {
			Base.prototype.onValueChanged.apply(this, arguments);
			this._saveDraft();
		},
		
		onRouteMatched: function(oEvent) {
			var sId = oEvent.getParameter("arguments").id,
				oModel = this.getModel("view");
			oModel.setProperty("/id", sId);
			oModel.setProperty("/edit", false);
			oModel.setProperty("/draft", "Clear");
			this.onRead();
		},
		
		onToggleEdit: function() {
			var oModel = this.getModel("view");
			if (oModel.getProperty("/edit")) {
				this._restoreDraft();
			}
			else {
				oModel.setProperty("/draft", "Clear");
				this.onRead();
			}
		},
		
		onNotFoundError: function(/* oXhr */) {
			this.getRouter().getTargets().display("not-found");
		},
		
		_saveDraft: function() {
			var oModel = this.getModel("view"),
				oData = {
					data: this.getModel("data").getData(),
					time: new Date().getTime()
				},
				sHash = HashChanger.getInstance().getHash();
			oModel.setProperty("/draft", "Saving");
			DraftService.save(sHash, oData).then(function(){
				oModel.setProperty("/draft", "Saved");
			});
		},
		
		_restoreDraft: function() {
			var oViewModel = this.getModel("view"),
				oDataModel = this.getModel("data"),
				oBundle = this.getResourceBundle(),
				fnOnConfirm = function(oData) {
					oDataModel.setData(oData.data);
					oViewModel.setProperty("/edit", true);
				},
				fnOnDecline = function() {
					oViewModel.setProperty("/edit", true);
					DraftService.remove(HashChanger.getInstance().getHash());
				},
				fnShowMsg = function(oData) {
					var sMsg = oBundle.getText("txtEntityPageDraftExistsActionText", [oDtFormat.format(new Date(oData.time))]);
					MessageBox.show(sMsg, {
						icon: MessageBox.Icon.INFORMATION,
						title: oBundle.getText("txtEntityPageDraftExistsActionTitle"),
						actions: [MessageBox.Action.YES, MessageBox.Action.NO],
						onClose: function(sAction) {
							if (sAction === MessageBox.Action.YES) {
								fnOnConfirm(oData);
							}
							else {
								fnOnDecline();
							}
						}
					});
				};
				
			DraftService.load(HashChanger.getInstance().getHash()).then(function(oData){
				if (oData && oData.data) {
					if ((new Date().getTime() - oData.time) <= 30 * 60 * 1000) {
						fnOnConfirm(oData);
					}
					else {
						fnShowMsg(oData);
					}
				}
				else {
					fnOnDecline();
				}
			});
		}
		
	});

});