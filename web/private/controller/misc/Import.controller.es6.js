import Base from "spet/sbwo/web/private/controller/Base";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";

var fnInitializeData = function (oData) {
    var fnOnEachFile = function (iIndex, oFile) {
            oFile.exists = false;
            oFile.size = null;
        },
        fnOnEachImport = function (sKey, oImport) {
            jQuery.each(oImport.files, fnOnEachFile);
            oImport.ready = false;
        };
    jQuery.each(oData, fnOnEachImport);
};

var oPromise = (function () {
    var oDeferred = jQuery.Deferred();
    jQuery.ajax({
        method: "GET",
        url: jQuery.sap.getModulePath("spet.sbwo.web.private.model.import", "/data.json")
    }).then(function (oData) {
        fnInitializeData(oData);
        oDeferred.resolve(oData);
    }).fail(function () {
        oDeferred.reject();
    });
    return oDeferred.promise();
}());


export default Base.extend("spet.sbwo.web.private.controller.misc.Import", {
    onInit() {
        this.buildViewModel({
            busy: false,
            selected: "persons",
            data: {}
        });
        this.reset();
    },

    reset() {
        var oModel = this.getModel("view");

        oPromise.then(function (oData) {
            oModel.setProperty("/data", jQuery.extend(true, {}, oData));
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

    formatTabText(sText) {
        return this.getResourceBundle().getText(jQuery.sap.camelCase("itf-import-" + sText + "-tab-text"));
    },

    formatFileDescription(sFile) {
        return this.getResourceBundle().getText(jQuery.sap.camelCase("txt-import-file-" + sFile + "-description"));
    },

    formatTabDescription(sText) {
        return this.getResourceBundle().getText(jQuery.sap.camelCase("itf-import-" + sText + "-tab-description"));
    },

    formatUploadUrl(sName) {
        return "/private/api/rest/import/data/" + sName;
    },

    formatZipPath(sName) {
        return jQuery.sap.getModulePath("spet/sbwo/web/private/model/import", "/" + sName + ".zip");
    },

    onUpdateBinding() {
        this.getView().bindElement({
            model: "view",
            path: "/data/" + this.getModel("view").getProperty("/selected")
        });
    },

    onUploadChange(oEvent) {
        var aFiles = oEvent.getParameter("files"),
            oModel = this.getModel("view"),
            oImports = oModel.getProperty("/data"),
            fnGetFileName = sName => sName.lastIndexOf(".") >= 0 ? sName.substring(0, sName.lastIndexOf(".")) : sName,
            fnUpdateFile = function (sName, iSize, sKey, oImport) {
                for (var j = 0; j < oImport.files.length; ++j) {
                    if (oImport.files[j].name === sName) {
                        oImport.files[j].exists = true;
                        oImport.files[j].size = iSize;
                    }
                }
            },
            fnUpdateReadyFlag = function (sKey, oImport) {
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
            for (let i = 0; i < aFiles.length; ++i) {
                jQuery.each(oImports, fnUpdateFile.bind(null, fnGetFileName(aFiles[i].name || ""), aFiles[i].size));
            }
            jQuery.each(oImports, fnUpdateReadyFlag);
        }
        oModel.refresh();
    },

    onStartUpload() {
        this.getModel("view").setProperty("/busy", true);
        this.byId("fupImport").upload();
    },

    onUploadComplete(oEvent) {
        var oBundle = this.getResourceBundle();
        this.getModel("view").setProperty("/busy", false);
        if (oEvent.getParameter("status") === 204) {
            MessageBox.success(oBundle.getText("txtDataImportUploadSuccessText"));
            this.reset();
        } else {
            this.onInternalServerError(oEvent.getParameter("responseRaw"));
        }
        this.getModel().refresh();
    }

});
