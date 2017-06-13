sap.ui.define(["spet/sbwo/web/private/controller/Base", "sap/ui/model/json/JSONModel", "sap/m/MessageBox", "sap/ui/core/routing/HashChanger", "spet/sbwo/web/private/util/DraftService", "sap/ui/core/format/DateFormat"], function (_Base, _JSONModel, _MessageBox, _HashChanger, _DraftService, _DateFormat) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _HashChanger2 = _interopRequireDefault(_HashChanger);

    var _DraftService2 = _interopRequireDefault(_DraftService);

    var _DateFormat2 = _interopRequireDefault(_DateFormat);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var oDtFormat = _DateFormat2.default.getDateTimeInstance({ style: "short" });

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.entity.Base", {
        getBaseApiUrl: function getBaseApiUrl() {
            return "";
        },
        getReadApiUrl: function getReadApiUrl(sId) {
            return this.getBaseApiUrl() + "/read/" + sId;
        },
        getUpdateApiUrl: function getUpdateApiUrl(sId) {
            return this.getBaseApiUrl() + "/update/" + sId;
        },
        getRestoreApiUrl: function getRestoreApiUrl(sId) {
            return this.getBaseApiUrl() + "/restore/" + sId;
        },
        getDeleteApiUrl: function getDeleteApiUrl(sId) {
            return this.getBaseApiUrl() + "/delete/" + sId;
        },
        getEntityListRoute: function getEntityListRoute() {
            return null;
        },
        onInit: function onInit() {
            this.buildViewModel({
                edit: false,
                draft: "Clear",
                id: null,
                loaded: false
            });
            this.setModel(new _JSONModel2.default({}), "data");
        },
        onSave: function onSave() {
            var sUrl = this.getUpdateApiUrl(this.getModel("view").getProperty("/id"));
            this.put(sUrl, this.getModel("data").getData(), this.onSaveSuccess);
        },
        onSaveSuccess: function onSaveSuccess(oData) {
            this.getModel("data").setData(oData);
            this.getModel("view").setProperty("/edit", false);
            this.getModel("view").setProperty("/draft", "Clear");
            _DraftService2.default.remove(_HashChanger2.default.getInstance().getHash());
        },
        onRestore: function onRestore() {
            var sUrl = this.getRestoreApiUrl(this.getModel("view").getProperty("/id"));
            this.put(sUrl, {}, this.onRestoreSuccess);
        },
        onRestoreSuccess: function onRestoreSuccess(oData) {
            this.getModel("data").setData(oData);
        },
        onReset: function onReset() {
            this.onRead();
            _DraftService2.default.remove(_HashChanger2.default.getInstance().getHash());
        },
        onRead: function onRead() {
            var oViewModel = this.getModel("view"),
                sUrl = this.getReadApiUrl(oViewModel.getProperty("/id"));
            oViewModel.setProperty("/loaded", false);
            this.get(sUrl, this.onReadSuccess);
        },
        onReadSuccess: function onReadSuccess(oData) {
            this.getModel("data").setData(oData);
            this.getModel("view").setProperty("/draft", "Clear");
            this.getModel("view").setProperty("/loaded", true);
        },
        onDelete: function onDelete() {
            var sUrl = this.getDeleteApiUrl(this.getModel("view").getProperty("/id"));
            this.del(sUrl, this.onDeleteSuccess);
        },
        onDeleteSuccess: function onDeleteSuccess() {
            this.getModel("view").setProperty("/draft", "Clear");
            window.history.go(-1);
        },
        onValueChanged: function onValueChanged() {
            _Base2.default.prototype.onValueChanged.apply(this, arguments);
            this.saveDraft();
        },
        onRouteMatched: function onRouteMatched(oEvent) {
            var sId = oEvent.getParameter("arguments").id,
                oModel = this.getModel("view");
            oModel.setProperty("/id", sId);
            oModel.setProperty("/edit", false);
            oModel.setProperty("/draft", "Clear");
            this.onRead();
        },
        onToggleEdit: function onToggleEdit() {
            var oModel = this.getModel("view");
            if (oModel.getProperty("/edit")) {
                this.restoreDraft();
            } else {
                oModel.setProperty("/draft", "Clear");
                this.onRead();
            }
        },
        onNotFoundError: function onNotFoundError() /* oXhr */{
            this.getRouter().getTargets().display("not-found");
        },
        saveDraft: function saveDraft() {
            var oModel = this.getModel("view"),
                oData = {
                data: this.getModel("data").getData(),
                time: new Date().getTime()
            },
                sHash = _HashChanger2.default.getInstance().getHash();
            oModel.setProperty("/draft", "Saving");
            _DraftService2.default.save(sHash, oData).then(function () {
                oModel.setProperty("/draft", "Saved");
            });
        },
        restoreDraft: function restoreDraft() {
            var _this = this;

            var oViewModel = this.getModel("view"),
                oDataModel = this.getModel("data"),
                oBundle = this.getResourceBundle(),
                fnOnConfirm = function fnOnConfirm(oData) {
                oDataModel.setData(oData.data);
                oViewModel.setProperty("/edit", true);
            },
                fnOnDecline = function fnOnDecline() {
                oViewModel.setProperty("/edit", true);
                _DraftService2.default.remove(_HashChanger2.default.getInstance().getHash());
            },
                fnShowMsg = function fnShowMsg(oData) {
                var sMsg = oBundle.getText("txtEntityPageDraftExistsActionText", [oDtFormat.format(new Date(oData.time))]);
                _MessageBox2.default.show(sMsg, {
                    icon: _MessageBox2.default.Icon.INFORMATION,
                    title: oBundle.getText("txtEntityPageDraftExistsActionTitle"),
                    actions: [_MessageBox2.default.Action.YES, _MessageBox2.default.Action.NO],
                    onClose: function onClose(sAction) {
                        if (sAction === _MessageBox2.default.Action.YES) {
                            fnOnConfirm(oData);
                        } else {
                            fnOnDecline();
                        }
                    }
                });
            };

            _DraftService2.default.load(_HashChanger2.default.getInstance().getHash()).then(function (oData) {
                if (oData && oData.data) {
                    var iDelay = _this.getModel("user").getProperty("/preference/draftResumeDelay");
                    if (new Date().getTime() - oData.time <= iDelay * 60 * 1000) {
                        fnOnConfirm(oData);
                    } else {
                        fnShowMsg(oData);
                    }
                } else {
                    fnOnDecline();
                }
            });
        }
    });
    return exports.default;
});
//# sourceMappingURL=Base.js.map
