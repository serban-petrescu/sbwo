import Base from "spet/sbwo/web/private/controller/Base";
import JSONModel from "sap/ui/model/json/JSONModel";
import HashChanger from "sap/ui/core/routing/HashChanger";
import DraftService from "spet/sbwo/web/private/util/DraftService";
import Wizard from "sap/m/Wizard";
import jQuery from "jquery.sap.global";

if (Wizard.CONSTANTS) {
    Wizard.CONSTANTS.SCROLL_OFFSET = 0;
}

export default Base.extend("spet.sbwo.web.private.controller.wizard.Base", {
    _rendered: null,

    getEntityRoute() {},
    getCreateApiPath() {},

    onInit() {
        this.buildViewModel();
        this.buildDataModel();
        this._rendered = jQuery.Deferred();
    },

    onAfterRendering() {
        this._rendered.resolve();
    },

    buildViewModel() {
        this.setModel(new JSONModel(this.getInitialViewModelContent()), "view");
    },

    buildDataModel() {
        this.setModel(new JSONModel(this.getInitialDataModelContent()), "data");
    },

    getWizard() {
        return this.byId("wizMain");
    },

    getInitialDataModelContent() {
        return {};
    },

    getInitialViewModelContent() {
        return {};
    },

    onValueChanged() {
        Base.prototype.onValueChanged.apply(this, arguments);
        this.saveDraft();
    },

    onRouteMatched() {
        this._reset();
        this.restoreDraft();
    },

    onReset() {
        this._reset();
        DraftService.remove(HashChanger.getInstance().getHash());
    },

    onComplete() {
        let oData = this.getModel("data").getData();
        this.post(this.getCreateApiPath(), oData, sId => {
            DraftService.remove(HashChanger.getInstance().getHash());
            this.getRouter().navTo(this.getEntityRoute(), {
                id: sId
            }, true);
        });
    },

    saveDraft() {
        let oModel = this.getModel("view"),
            sHash = HashChanger.getInstance().getHash();
        let oData = {
            data: this.getModel("data").getData(),
            wizard: this.getModel("view").getProperty("/wizard")
        };
        oModel.setProperty("/draft", "Saving");
        DraftService.save(sHash, oData).then(() => oModel.setProperty("/draft", "Saved"));
        this._updateWizardModel();
    },

    restoreDraft() {
        DraftService.load(HashChanger.getInstance().getHash()).then(oData => {
            if (oData && oData.data) {
                this.getModel("data").setData(oData.data);
                this.getModel("view").setProperty("/wizard", oData.wizard);
                this._updateWizardControl();
            }
        });
    },

    _reset() {
        let oWizard = this.getWizard(),
            oFirstStep = oWizard.getSteps()[0];
        oWizard.discardProgress(oFirstStep);
        this.getModel("view").setData(this.getInitialViewModelContent());
        this.getModel("data").setData(this.getInitialDataModelContent());
    },

    _updateWizardModel() {
        this.getModel("view").setProperty("/wizard/step", this.getWizard().getProgress());
    },

    _updateWizardControl() {
        this._rendered.then(() => {
            let oWizard = this.getWizard(),
                iTargetStep = this.getModel("view").getProperty("/wizard/step");
            oWizard.discardProgress(oWizard.getSteps()[0]);
            for (var i = 1; i < iTargetStep; ++i) {
                oWizard.nextStep();
            }
        });
    }
});
