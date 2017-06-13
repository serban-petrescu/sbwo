sap.ui.define(["./Base", "spet/sbwo/web/private/controller/mixin/ExpertiseEdit", "jquery.sap.global"], function (_Base, _ExpertiseEdit, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _ExpertiseEdit2 = _interopRequireDefault(_ExpertiseEdit);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.wizard.ExpertiseCreate", _jquerySap2.default.extend({}, _ExpertiseEdit2.default, {
        getEntityRoute: function getEntityRoute() {
            return "expertise";
        },
        getCreateApiPath: function getCreateApiPath() {
            return "/private/api/rest/expertise/create";
        },
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("expertise-create").attachPatternMatched(this.onRouteMatched, this);
            this.byId("dlgCourtCase").addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },
        getInitialDataModelContent: function getInitialDataModelContent() {
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
        getInitialViewModelContent: function getInitialViewModelContent() {
            return {
                "draft": "Clear",
                "case": null,
                "wizard": {
                    step: 0,
                    validity: [true, true, true]
                }
            };
        },
        getCaseNumberInput: function getCaseNumberInput() {
            return this.byId("inpCaseNumber");
        },
        restoreDraft: function restoreDraft() {
            _Base2.default.prototype.restoreDraft.apply(this, arguments);
            this.onLoadCase();
        },
        onCaseLoaded: function onCaseLoaded() {
            _ExpertiseEdit2.default.onCaseLoaded.apply(this, arguments);
            this.saveDraft();
        },
        onCourtLoaded: function onCourtLoaded() {
            _ExpertiseEdit2.default.onCourtLoaded.apply(this, arguments);
            this.saveDraft();
        },
        onCourtClear: function onCourtClear() {
            _ExpertiseEdit2.default.onCourtClear.apply(this, arguments);
            this.saveDraft();
        },
        onOpenCourtCaseDialog: function onOpenCourtCaseDialog() {
            this.byId("dlgCourtCase").open();
        },
        onCloseCourtCaseDialog: function onCloseCourtCaseDialog() {
            this.byId("dlgCourtCase").close();
        }
    }));
    return exports.default;
});
//# sourceMappingURL=ExpertiseCreate.controller.js.map
