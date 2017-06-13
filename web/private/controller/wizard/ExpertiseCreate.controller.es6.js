import Base from "./Base";
import ExpertiseEdit from "spet/sbwo/web/private/controller/mixin/ExpertiseEdit";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.wizard.ExpertiseCreate", jQuery.extend({}, ExpertiseEdit, {

    getEntityRoute() {
        return "expertise";
    },

    getCreateApiPath() {
        return "/private/api/rest/expertise/create";
    },

    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.getRouter().getRoute("expertise-create").attachPatternMatched(this.onRouteMatched, this);
        this.byId("dlgCourtCase").addStyleClass(this.getOwnerComponent().getContentDensityClass());
    },

    getInitialDataModelContent() {
        return {
            number: "",
            title: "",
            year: null,
            court: {
                id: null,
                name: "",
                code: ""
            },
            status: null,
            nextHearing: null,
            responsible: {
                id: null
            }
        };
    },

    getInitialViewModelContent() {
        return {
            "draft": "Clear",
            "case": null,
            "wizard": {
                step: 0,
                validity: [true, true, true]
            }
        };
    },

    getCaseNumberInput() {
        return this.byId("inpCaseNumber");
    },

    restoreDraft() {
        Base.prototype.restoreDraft.apply(this, arguments);
        this.onLoadCase();
    },

    onCaseLoaded() {
        ExpertiseEdit.onCaseLoaded.apply(this, arguments);
        this.saveDraft();
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
        this.byId("dlgCourtCase").open();
    },

    onCloseCourtCaseDialog() {
        this.byId("dlgCourtCase").close();
    }
}));
