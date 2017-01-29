sap.ui.define([
	"spet/sbwo/web/controller/Base"
], function(Base) {
	"use strict";
	
	var sBaseApiPath = "/private/api/rest/utility/file/explore";
	
	var oSystem = {
		separator: "/",
		roots: []
	};
	
	var fnIsRoot = function(sDir) {
		for (var i = 0; i < oSystem.roots.length; ++i) {
			if (oSystem.roots[i].name === sDir || oSystem.roots[i].name + oSystem.separator === sDir) {
				return true;
			}
		}
		return false;
	};
	
	jQuery.ajax({
		url: sBaseApiPath + "/roots",
		method: "GET",
		success: function(oData) {
			oSystem.separator = oData.separator;
			oSystem.roots = oData.roots.map(function(sRoot){
				return {
					name: sRoot,
					icon: "sap-icon://folder-full"
				};
			});
		}
	});
	
	var fnGetIconForExtension = function(sExt) {
		switch(sExt.toLowerCase()) {
		case "mp3":
		case "wav":
		case "flac":
			return "sap-icon://attachment-audio";
		case "html":
		case "htm":
		case "xml":
			return "sap-icon://attachment-html";
		case "png":
		case "svg":
		case "jpg":
		case "bmp":
		case "gif":
			return "sap-icon://attachment-photo";
		case "txt":
		case "ini":
		case "rtf":
			return "sap-icon://attachment-text-file";
		case "avi":
		case "mkv":
		case "mp4":
		case "mpeg":
			return "sap-icon://attachment-video";
		case "zip":
		case "gz":
		case "7z":
		case "rar":
			return "sap-icon://attachment-zip-file";
		case "doc":
		case "docx":
			return "sap-icon://doc-attachment";
		case "xls":
		case "xlsx":
		case "csv":
			return "sap-icon://excel-attachment";
		case "pdf":
			return "sap-icon://pdf-attachment";
		default:
			return "sap-icon://document";
		}
	};
	
	return Base.extend("spet.sbwo.web.controller.misc.FileExplorer", {
		onInit: function() {
			this.buildViewModel({
				current: "",
				extension: "",
				folder: true,
				files: [],
				folders: [],
				multiselect: false
			});
			
			this.byId("dlgMain").addStyleClass(this.getOwnerComponent().getContentDensityClass());
		},
		
		multiselect: function(bMulti) {
			this.getModel("view").setProperty("/multiselect", bMulti);
			return this;
		},
		
		current: function(sCurrent) {
			this.getModel("view").setProperty("/current", sCurrent);
			return this;
		},
		
		folder: function() {
			this.getModel("view").setProperty("/folder", true);
			return this;
		},
		
		file: function(sExt) {
			this.getModel("view").setProperty("/folder", false);
			this.getModel("view").setProperty("/extension", sExt);
			return this;
		},
		
		onFolderPress: function(oEvent) {
			var sName = oEvent.getSource().getBindingContext("view").getProperty("name"),
				sCurrent = this.getModel("view").getProperty("/current");
			if (sCurrent.lastIndexOf(oSystem.separator) === sCurrent.length - 1) {
				sCurrent += sName + oSystem.separator;
			}
			else {
				sCurrent += oSystem.separator + sName + oSystem.separator;
			}
			this.getModel("view").setProperty("/current", sCurrent);
			this.read();
		},
		
		onUpPress: function() {
			var sCurrent = this.getModel("view").getProperty("/current");
			if (fnIsRoot(sCurrent) || !sCurrent) {
				this.getModel("view").setProperty("/current", "");
				this.getModel("view").setProperty("/folders", oSystem.roots);
			}
			else {
				sCurrent = sCurrent.substring(0, sCurrent.lastIndexOf(oSystem.separator, sCurrent.length - 1) + 1) || oSystem.separator;
				this.getModel("view").setProperty("/current", sCurrent);
				this.read();
			}
		},
		
		onOkPress: function() {
			var oModel = this.getModel("view"),
				sBase = this.getModel("view").getProperty("/current"),
				oList = oModel.getProperty("/folder") ? this.byId("lstFolders") : this.byId("lstFiles"),
				aSels = oList.getSelectedItems().map(function(oSel) {
					if (sBase.lastIndexOf(oSystem.separator) === sBase.length - 1) {
						return sBase + oSel.getBindingContext("view").getProperty("name");
					}
					else {
						return sBase + oSystem.separator + oSel.getBindingContext("view").getProperty("name");
					}
				});
			if (oModel.getProperty("/folder") && !oModel.getProperty("/multiselect")) {
				this.fireEvent("select", {
					result: [sBase]
				});
			}
			else {
				this.fireEvent("select", {
					result: aSels
				});
			}
			this.close();
		},
		
		open: function() {
			this.byId("dlgMain").open();
			this.read();
		},
		
		close: function() {
			this.byId("dlgMain").close();
		},
		
		read: function() {
			var oModel = this.getModel("view");
			jQuery.ajax({
				method: "GET",
				url: sBaseApiPath + "/folders",
				data: {
					base: oModel.getProperty("/current")
				},
				success: function(oData) {
					oModel.setProperty("/current", oData.base);
					oModel.setProperty("/folders", oData.content.map(function(sFolder){
						return {
							name: sFolder,
							icon: "sap-icon://open-folder"
						};
					}));
				},
				error: function() {
					oModel.setProperty("/folders", []);
				}
			});
			if (!oModel.getProperty("folder")) {
				jQuery.ajax({
					method: "GET",
					url: sBaseApiPath + "/files",
					data: {
						base: oModel.getProperty("/current"),
						extension: oModel.getProperty("/extension")
					},
					success: function(oData) {
						oModel.setProperty("/filder", oData.content.map(function(oFile){
							return {
								name: oFile.name + "." + oFile.extension,
								icon: fnGetIconForExtension(oFile.extension)
							};
						}));
					},
					error: function() {
						oModel.setProperty("/files", []);
					}
				});
			}
		}
		
	});

});