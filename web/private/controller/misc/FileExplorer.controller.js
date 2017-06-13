sap.ui.define(["spet/sbwo/web/private/controller/Base", "jquery.sap.global"], function (_Base, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var sBaseApiPath = "/private/api/rest/utility/file/explore";

    var oSystem = {
        separator: "/",
        roots: []
    };

    var fnIsRoot = function fnIsRoot(sDir) {
        for (var i = 0; i < oSystem.roots.length; ++i) {
            if (oSystem.roots[i].name === sDir || oSystem.roots[i].name + oSystem.separator === sDir) {
                return true;
            }
        }
        return false;
    };

    _jquerySap2.default.ajax({
        url: sBaseApiPath + "/roots",
        method: "GET",
        success: function success(oData) {
            oSystem.separator = oData.separator;
            oSystem.roots = oData.roots.map(function (sRoot) {
                return {
                    name: sRoot,
                    icon: "sap-icon://folder-full"
                };
            });
        }
    });

    var fnGetIconForExtension = function fnGetIconForExtension(sExt) {
        switch (sExt.toLowerCase()) {
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

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.FileExplorer", {
        onInit: function onInit() {
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
        multiselect: function multiselect(bMulti) {
            this.getModel("view").setProperty("/multiselect", bMulti);
            return this;
        },
        current: function current(sCurrent) {
            this.getModel("view").setProperty("/current", sCurrent);
            return this;
        },
        folder: function folder() {
            this.getModel("view").setProperty("/folder", true);
            return this;
        },
        file: function file(sExt) {
            this.getModel("view").setProperty("/folder", false);
            this.getModel("view").setProperty("/extension", sExt);
            return this;
        },
        onFolderPress: function onFolderPress(oEvent) {
            var sName = oEvent.getSource().getBindingContext("view").getProperty("name"),
                sCurrent = this.getModel("view").getProperty("/current");
            if (sCurrent.lastIndexOf(oSystem.separator) === sCurrent.length - 1) {
                sCurrent += sName + oSystem.separator;
            } else {
                sCurrent += oSystem.separator + sName + oSystem.separator;
            }
            this.getModel("view").setProperty("/current", sCurrent);
            this.read();
        },
        onUpPress: function onUpPress() {
            var sCurrent = this.getModel("view").getProperty("/current");
            if (fnIsRoot(sCurrent) || !sCurrent) {
                this.getModel("view").setProperty("/current", "");
                this.getModel("view").setProperty("/folders", oSystem.roots);
            } else {
                sCurrent = sCurrent.substring(0, sCurrent.lastIndexOf(oSystem.separator, sCurrent.length - 1) + 1) || oSystem.separator;
                this.getModel("view").setProperty("/current", sCurrent);
                this.read();
            }
        },
        onOkPress: function onOkPress() {
            var oModel = this.getModel("view"),
                sBase = this.getModel("view").getProperty("/current"),
                oList = oModel.getProperty("/folder") ? this.byId("lstFolders") : this.byId("lstFiles"),
                aSels = oList.getSelectedItems().map(function (oSel) {
                if (sBase.lastIndexOf(oSystem.separator) === sBase.length - 1) {
                    return sBase + oSel.getBindingContext("view").getProperty("name");
                } else {
                    return sBase + oSystem.separator + oSel.getBindingContext("view").getProperty("name");
                }
            });
            if (oModel.getProperty("/folder") && !oModel.getProperty("/multiselect")) {
                this.fireEvent("select", {
                    result: [sBase]
                });
            } else {
                this.fireEvent("select", {
                    result: aSels
                });
            }
            this.close();
        },
        open: function open() {
            this.byId("dlgMain").open();
            this.read();
        },
        close: function close() {
            this.byId("dlgMain").close();
        },
        read: function read() {
            var oModel = this.getModel("view");
            this.readFolders(oModel);
            if (!oModel.getProperty("folder")) {
                this.readFiles(oModel);
            }
        },
        readFolders: function readFolders(oModel) {
            _jquerySap2.default.ajax({
                method: "GET",
                url: sBaseApiPath + "/folders",
                data: {
                    base: oModel.getProperty("/current")
                },
                success: function success(oData) {
                    oModel.setProperty("/current", oData.base);
                    oModel.setProperty("/folders", oData.content.map(function (sFolder) {
                        return {
                            name: sFolder,
                            icon: "sap-icon://open-folder"
                        };
                    }));
                },
                error: function error() {
                    return oModel.setProperty("/folders", []);
                }
            });
        },
        readFiles: function readFiles(oModel) {
            _jquerySap2.default.ajax({
                method: "GET",
                url: sBaseApiPath + "/files",
                data: {
                    base: oModel.getProperty("/current"),
                    extension: oModel.getProperty("/extension")
                },
                success: function success(oData) {
                    oModel.setProperty("/filder", oData.content.map(function (oFile) {
                        return {
                            name: oFile.name + "." + oFile.extension,
                            icon: fnGetIconForExtension(oFile.extension)
                        };
                    }));
                },
                error: function error() {
                    return oModel.setProperty("/files", []);
                }
            });
        }
    });
    return exports.default;
});
//# sourceMappingURL=FileExplorer.controller.js.map
