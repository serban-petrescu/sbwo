sap.ui.define([
	"spet/sbwo/web/controller/Base",
	"sap/ui/model/json/JSONModel",
	"sap/m/MessageBox",
	"sap/ui/core/routing/HashChanger",
	"spet/sbwo/web/util/DraftService",
	"sap/ui/core/format/DateFormat",
	"spet/sbwo/web/model/preference"
], function(Base, JSONModel, MessageBox, HashChanger, DraftService, DateFormat, preference) {
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
				id: null,
				loaded: false
			});
			this.setModel(new JSONModel({}), "data");
		},
		
		onSave: function() {
			var sUrl = this.getUpdateApiUrl(this.getModel("view").getProperty("/id"));
			this.put(sUrl, this.getModel("data").getData(), this.onSaveSuccess);
		},
		
		onSaveSuccess: function(oData) {
			this.getModel("data").setData(oData);
			this.getModel("view").setProperty("/edit", false);
			this.getModel("view").setProperty("/draft", "Clear");
			DraftService.remove(HashChanger.getInstance().getHash());
		},
		
		onRestore: function() {
			var sUrl = this.getRestoreApiUrl(this.getModel("view").getProperty("/id"));
			this.put(sUrl, {}, this.onRestoreSuccess);
		},
		
		onRestoreSuccess: function(oData) {
			this.getModel("data").setData(oData);
		},
		
		onReset: function() {
			this.onRead();
			DraftService.remove(HashChanger.getInstance().getHash());
		},
		
		onRead: function() {
			var oViewModel = this.getModel("view"),
				sUrl = this.getReadApiUrl(oViewModel.getProperty("/id"));
			oViewModel.setProperty("/loaded", false);
			this.get(sUrl, this.onReadSuccess);
		},
		
		onReadSuccess: function(oData) {
			this.getModel("data").setData(oData);
			this.getModel("view").setProperty("/draft", "Clear");
			this.getModel("view").setProperty("/loaded", true);
		},
		
		onDelete: function() {
			var sUrl = this.getDeleteApiUrl(this.getModel("view").getProperty("/id"));
			this.del(sUrl, this.onDeleteSuccess);
		},
		
		onDeleteSuccess: function() {
			this.getModel("view").setProperty("/draft", "Clear");
			window.history.go(-1);
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
					if ((new Date().getTime() - oData.time) <= preference.draftResumeDelay * 60 * 1000) {
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