sap.ui.define([
	"spet/sbwo/web/controller/Base"
], function(Base) {
	"use strict";
	
	var fnInitializeData = function(oData) {
		var fnOnEachFile = function(iIndex, oFile) {
				oFile.exists = false;
				oFile.size = null;
			},
			fnOnEachImport = function(sKey, oImport){
				jQuery.each(oImport.files, fnOnEachFile);
				oImport.ready = false;
			};
		jQuery.each(oData, fnOnEachImport);
	};
	
	var oPromise = (function(){
		var oDeferred = jQuery.Deferred();
		jQuery.ajax({
			method: "GET",
			url: jQuery.sap.getModulePath("spet.sbwo.web.model.import", "/data.json")
		}).then(function(oData){
			fnInitializeData(oData);
			oDeferred.resolve(oData);
		}).fail(function(){
			oDeferred.reject();
		});
		return oDeferred.promise();
	}());
	
	
	return Base.extend("spet.sbwo.web.controller.misc.Import", {
		onInit: function() {
			this.buildViewModel({
				busy: false,
				selected: "persons",
				data: {}
			});
			this.reset();
		},
		
		reset: function() {
			var oModel = this.getModel("view");
			
			oPromise.then(function(oData){
				oModel.setProperty("/data", jQuery.extend(true, {}, oData));
			});
			this.getOwnerComponent().getModel().securityTokenAvailable().then(function(sToken) {
				oModel.setProperty("/securityToken", sToken);
			});
			
			oModel.setProperty("/selected", "persons");
			this.getView().bindElement({
				model: "view",
				path: "/data/persons"
			});
			this.byId("fupImport").clear();
		},
		
		formatTabText: function(sText) {
			return this.getResourceBundle().getText(jQuery.sap.camelCase("itf-import-" + sText + "-tab-text"));
		},
		
		formatTabDescription: function(sText) {
			return this.getResourceBundle().getText(jQuery.sap.camelCase("itf-import-" + sText + "-tab-description"));
		},
		
		formatUploadUrl: function(sName) {
			return "/private/api/rest/import/data/" + sName;
		},
		
		onUpdateBinding: function() {
			this.getView().bindElement({
				model: "view",
				path: "/data/" + this.getModel("view").getProperty("/selected")
			});
		},
		
		onUploadChange: function(oEvent) {
			var aFiles = oEvent.getParameter("files"),
				oModel = this.getModel("view"),
				oImports = oModel.getProperty("/data"),
				fnGetFileName = function(sName) {
					return sName.lastIndexOf(".") >= 0 ? sName.substring(0, sName.lastIndexOf(".")) : sName;
				},
				fnUpdateFile = function(sName, iSize, sKey, oImport) {
					for (var j = 0; j < oImport.files.length; ++j) {
						if (oImport.files[j].name === sName) {
							oImport.files[j].exists = true;
							oImport.files[j].size = iSize;
						}
					}
				},
				fnUpdateReadyFlag = function(sKey, oImport) {
					oImport.ready = false;
					for (var j = 0; j < oImport.files.length; ++j) {
						if (!oImport.files[j].exists && !oImport.files[j].optional) {
							return;
						}
					}
					oImport.ready = true;
				},
				i;
			fnInitializeData(oImports);
			if (aFiles) {
				for (i = 0; i < aFiles.length; ++i) {
					jQuery.each(oImports, fnUpdateFile.bind(null, fnGetFileName(aFiles[i].name || ""), aFiles[i].size));
				}
				jQuery.each(oImports, fnUpdateReadyFlag);
			}
			oModel.refresh();
		},
		
		onStartUpload: function() {
			this.byId("fupImport").upload();
		}
		
	});

});