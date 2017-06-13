import Base from "spet/sbwo/web/private/controller/Base";
import JSONModel from "sap/ui/model/json/JSONModel";
import MessageBox from "sap/m/MessageBox";
import HashChanger from "sap/ui/core/routing/HashChanger";
import DraftService from "spet/sbwo/web/private/util/DraftService";
import DateFormat from "sap/ui/core/format/DateFormat";

var oDtFormat = DateFormat.getDateTimeInstance({style: "short"});

export default Base.extend("spet.sbwo.web.private.controller.entity.Base", {

    getBaseApiUrl() {
        return "";
    },

    getReadApiUrl(sId) {
        return this.getBaseApiUrl() + "/read/" + sId;
    },

    getUpdateApiUrl(sId) {
        return this.getBaseApiUrl() + "/update/" + sId;
    },

    getRestoreApiUrl(sId) {
        return this.getBaseApiUrl() + "/restore/" + sId;
    },

    getDeleteApiUrl(sId) {
        return this.getBaseApiUrl() + "/delete/" + sId;
    },

    getEntityListRoute() {
        return null;
    },

    onInit() {
        this.buildViewModel({
            edit: false,
            draft: "Clear",
            id: null,
            loaded: false
        });
        this.setModel(new JSONModel({}), "data");
    },

    onSave() {
        var sUrl = this.getUpdateApiUrl(this.getModel("view").getProperty("/id"));
        this.put(sUrl, this.getModel("data").getData(), this.onSaveSuccess);
    },

    onSaveSuccess(oData) {
        this.getModel("data").setData(oData);
        this.getModel("view").setProperty("/edit", false);
        this.getModel("view").setProperty("/draft", "Clear");
        DraftService.remove(HashChanger.getInstance().getHash());
    },

    onRestore() {
        var sUrl = this.getRestoreApiUrl(this.getModel("view").getProperty("/id"));
        this.put(sUrl, {}, this.onRestoreSuccess);
    },

    onRestoreSuccess(oData) {
        this.getModel("data").setData(oData);
    },

    onReset() {
        this.onRead();
        DraftService.remove(HashChanger.getInstance().getHash());
    },

    onRead() {
        var oViewModel = this.getModel("view"),
            sUrl = this.getReadApiUrl(oViewModel.getProperty("/id"));
        oViewModel.setProperty("/loaded", false);
        this.get(sUrl, this.onReadSuccess);
    },

    onReadSuccess(oData) {
        this.getModel("data").setData(oData);
        this.getModel("view").setProperty("/draft", "Clear");
        this.getModel("view").setProperty("/loaded", true);
    },

    onDelete() {
        var sUrl = this.getDeleteApiUrl(this.getModel("view").getProperty("/id"));
        this.del(sUrl, this.onDeleteSuccess);
    },

    onDeleteSuccess() {
        this.getModel("view").setProperty("/draft", "Clear");
        window.history.go(-1);
    },

    onValueChanged() {
        Base.prototype.onValueChanged.apply(this, arguments);
        this.saveDraft();
    },

    onRouteMatched(oEvent) {
        var sId = oEvent.getParameter("arguments").id,
            oModel = this.getModel("view");
        oModel.setProperty("/id", sId);
        oModel.setProperty("/edit", false);
        oModel.setProperty("/draft", "Clear");
        this.onRead();
    },

    onToggleEdit() {
        var oModel = this.getModel("view");
        if (oModel.getProperty("/edit")) {
            this.restoreDraft();
        }
        else {
            oModel.setProperty("/draft", "Clear");
            this.onRead();
        }
    },

    onNotFoundError(/* oXhr */) {
        this.getRouter().getTargets().display("not-found");
    },

    saveDraft() {
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

    restoreDraft() {
        var oViewModel = this.getModel("view"),
            oDataModel = this.getModel("data"),
            oBundle = this.getResourceBundle(),
            fnOnConfirm = oData => {
                oDataModel.setData(oData.data);
                oViewModel.setProperty("/edit", true);
            },
            fnOnDecline = () => {
                oViewModel.setProperty("/edit", true);
                DraftService.remove(HashChanger.getInstance().getHash());
            },
            fnShowMsg = oData => {
                var sMsg = oBundle.getText("txtEntityPageDraftExistsActionText", [oDtFormat.format(new Date(oData.time))]);
                MessageBox.show(sMsg, {
                    icon: MessageBox.Icon.INFORMATION,
                    title: oBundle.getText("txtEntityPageDraftExistsActionTitle"),
                    actions: [MessageBox.Action.YES, MessageBox.Action.NO],
                    onClose: sAction => {
                        if (sAction === MessageBox.Action.YES) {
                            fnOnConfirm(oData);
                        }
                        else {
                            fnOnDecline();
                        }
                    }
                });
            };

        DraftService.load(HashChanger.getInstance().getHash()).then(oData => {
            if (oData && oData.data) {
                var iDelay = this.getModel("user").getProperty("/preference/draftResumeDelay");
                if ((new Date().getTime() - oData.time) <= iDelay * 60 * 1000) {
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
