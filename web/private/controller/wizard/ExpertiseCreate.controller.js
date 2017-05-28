sap.ui.define([
    "./Base",
    "spet/sbwo/web/controller/facade/ExpertiseEdit"
], function(Base, ExpertiseEdit) {
    "use strict";

    var sBaseApiPath = "/private/api/rest/expertise";

    return Base.extend("spet.sbwo.web.controller.wizard.ExpertiseCreate", jQuery.extend({}, ExpertiseEdit, {

        getEntityRoute: function() {
            return "expertise";
        },

        getCreateApiPath: function() {
            return sBaseApiPath + "/create";
        },

        onInit: function() {
            Base.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("expertise-create").attachPatternMatched(this.onRouteMatched, this);
            this.byId("dlgCourtCase").addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },

        getInitialDataModelContent: function() {
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

        getInitialViewModelContent: function() {
            return {
                draft: "Clear",
                "case": null,
                wizard: {
                    step: 0,
                    validity: [true, true, true]
                }
            };
        },

        getCaseNumberInput: function() {
            return this.byId("inpCaseNumber");
        },

        restoreDraft: function() {
            Base.prototype.restoreDraft.apply(this, arguments);
            this.onLoadCase();
        },

        onCaseLoaded: function() {
            ExpertiseEdit.onCaseLoaded.apply(this, arguments);
            this.saveDraft();
        },

        onCourtLoaded: function() {
            ExpertiseEdit.onCourtLoaded.apply(this, arguments);
            this.saveDraft();
        },

        onCourtClear: function() {
            ExpertiseEdit.onCourtClear.apply(this, arguments);
            this.saveDraft();
        },

        onOpenCourtCaseDialog: function() {
            this.byId("dlgCourtCase").open();
        },

        onCloseCourtCaseDialog: function() {
            this.byId("dlgCourtCase").close();
        }
    }));

});
