sap.ui.define(["spet/sbwo/web/private/controller/Base", "sap/m/MessageBox", "jquery.sap.global"], function (_Base, _MessageBox, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var fnInitializeData = function fnInitializeData(oData) {
        var fnOnEachFile = function fnOnEachFile(iIndex, oFile) {
            oFile.exists = false;
            oFile.size = null;
        },
            fnOnEachImport = function fnOnEachImport(sKey, oImport) {
            _jquerySap2.default.each(oImport.files, fnOnEachFile);
            oImport.ready = false;
        };
        _jquerySap2.default.each(oData, fnOnEachImport);
    };

    var oPromise = function () {
        var oDeferred = _jquerySap2.default.Deferred();
        _jquerySap2.default.ajax({
            method: "GET",
            url: _jquerySap2.default.sap.getModulePath("spet.sbwo.web.private.model.import", "/data.json")
        }).then(function (oData) {
            fnInitializeData(oData);
            oDeferred.resolve(oData);
        }).fail(function () {
            oDeferred.reject();
        });
        return oDeferred.promise();
    }();

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.Import", {
        onInit: function onInit() {
            this.buildViewModel({
                busy: false,
                selected: "persons",
                data: {}
            });
            this.reset();
        },
        reset: function reset() {
            var oModel = this.getModel("view");

            oPromise.then(function (oData) {
                oModel.setProperty("/data", _jquerySap2.default.extend(true, {}, oData));
            });
            this.getOwnerComponent().getModel().securityTokenAvailable().then(function (sToken) {
                oModel.setProperty("/securityToken", sToken);
            });

            oModel.setProperty("/selected", "persons");
            this.getView().bindElement({
                model: "view",
                path: "/data/persons"
            });
            this.byId("fupImport").clear();
        },
        formatTabText: function formatTabText(sText) {
            return this.getResourceBundle().getText(_jquerySap2.default.sap.camelCase("itf-import-" + sText + "-tab-text"));
        },
        formatFileDescription: function formatFileDescription(sFile) {
            return this.getResourceBundle().getText(_jquerySap2.default.sap.camelCase("txt-import-file-" + sFile + "-description"));
        },
        formatTabDescription: function formatTabDescription(sText) {
            return this.getResourceBundle().getText(_jquerySap2.default.sap.camelCase("itf-import-" + sText + "-tab-description"));
        },
        formatUploadUrl: function formatUploadUrl(sName) {
            return "/private/api/rest/import/data/" + sName;
        },
        formatZipPath: function formatZipPath(sName) {
            return _jquerySap2.default.sap.getModulePath("spet/sbwo/web/private/model/import", "/" + sName + ".zip");
        },
        onUpdateBinding: function onUpdateBinding() {
            this.getView().bindElement({
                model: "view",
                path: "/data/" + this.getModel("view").getProperty("/selected")
            });
        },
        onUploadChange: function onUploadChange(oEvent) {
            var aFiles = oEvent.getParameter("files"),
                oModel = this.getModel("view"),
                oImports = oModel.getProperty("/data"),
                fnGetFileName = function fnGetFileName(sName) {
                return sName.lastIndexOf(".") >= 0 ? sName.substring(0, sName.lastIndexOf(".")) : sName;
            },
                fnUpdateFile = function fnUpdateFile(sName, iSize, sKey, oImport) {
                for (var j = 0; j < oImport.files.length; ++j) {
                    if (oImport.files[j].name === sName) {
                        oImport.files[j].exists = true;
                        oImport.files[j].size = iSize;
                    }
                }
            },
                fnUpdateReadyFlag = function fnUpdateReadyFlag(sKey, oImport) {
                oImport.ready = false;
                for (var j = 0; j < oImport.files.length; ++j) {
                    if (!oImport.files[j].exists && !oImport.files[j].optional) {
                        return;
                    }
                }
                oImport.ready = true;
            };
            fnInitializeData(oImports);
            if (aFiles) {
                for (var i = 0; i < aFiles.length; ++i) {
                    _jquerySap2.default.each(oImports, fnUpdateFile.bind(null, fnGetFileName(aFiles[i].name || ""), aFiles[i].size));
                }
                _jquerySap2.default.each(oImports, fnUpdateReadyFlag);
            }
            oModel.refresh();
        },
        onStartUpload: function onStartUpload() {
            this.getModel("view").setProperty("/busy", true);
            this.byId("fupImport").upload();
        },
        onUploadComplete: function onUploadComplete(oEvent) {
            var oBundle = this.getResourceBundle();
            this.getModel("view").setProperty("/busy", false);
            if (oEvent.getParameter("status") === 204) {
                _MessageBox2.default.success(oBundle.getText("txtDataImportUploadSuccessText"));
                this.reset();
            } else {
                this.onInternalServerError(oEvent.getParameter("responseRaw"));
            }
            this.getModel().refresh();
        }
    });
    return exports.default;
});
//# sourceMappingURL=Import.controller.js.map
