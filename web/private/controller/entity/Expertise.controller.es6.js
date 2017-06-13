import Base from "./Base";
import ExpertiseEdit from "spet/sbwo/web/private/controller/mixin/ExpertiseEdit";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.entity.Expertise", jQuery.extend({}, ExpertiseEdit, {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.getRouter().getRoute("expertise").attachPatternMatched(this.onRouteMatched, this);
    },

    getBaseApiUrl() {
        return "/private/api/rest/expertise";
    },

    getEntityListRoute() {
        return "expertise-list";
    },

    onReadSuccess() {
        Base.prototype.onReadSuccess.apply(this, arguments);
        this.getModel("view").setProperty("/case", null);
    },

    getCaseNumberInput() {
        return this.byId("inpCaseNumber");
    },

    onCaseNotFound() {
        if (this.getModel("view").getProperty("/edit")) {
            ExpertiseEdit.onCaseNotFound.apply(this, arguments);
        }
        else {
            if (this.byId("dlgCourtCase").isOpen()) {
                this.byId("dlgCourtCase").close();
            }
            MessageBox.warning(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
        }
    },

    onCaseLoaded(oData) {
        if (this.getModel("view").getProperty("/edit")) {
            ExpertiseEdit.onCaseLoaded.apply(this, arguments);
            this.saveDraft();
        }
        else {
            this.getModel("view").setProperty("/case", oData);
        }
    },

    onCourtLoaded() {
        ExpertiseEdit.onCourtLoaded.apply(this, arguments);
        this.saveDraft();
    },

    onCourtClear() {
        ExpertiseEdit.onCourtClear.apply(this, arguments);
        this.saveDraft();
    },

    onOpenCourtCaseDialog() {
        if (this.getModel("view").getProperty("/case") === null) {
            this.onLoadCase();
        }
        this.byId("dlgCourtCase").open();
    },

    onCloseCourtCaseDialog() {
        this.byId("dlgCourtCase").close();
    },

    onOpenLocationMapDialog() {
        this.byId("dlgLocationMap").open();
    },

    onCloseLocationMapDialog() {
        this.byId("dlgLocationMap").close();
    }

}));

