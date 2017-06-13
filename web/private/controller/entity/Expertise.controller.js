sap.ui.define(["./Base", "spet/sbwo/web/private/controller/mixin/ExpertiseEdit", "sap/m/MessageBox", "jquery.sap.global"], function (_Base, _ExpertiseEdit, _MessageBox, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _ExpertiseEdit2 = _interopRequireDefault(_ExpertiseEdit);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.entity.Expertise", _jquerySap2.default.extend({}, _ExpertiseEdit2.default, {
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("expertise").attachPatternMatched(this.onRouteMatched, this);
        },
        getBaseApiUrl: function getBaseApiUrl() {
            return "/private/api/rest/expertise";
        },
        getEntityListRoute: function getEntityListRoute() {
            return "expertise-list";
        },
        onReadSuccess: function onReadSuccess() {
            _Base2.default.prototype.onReadSuccess.apply(this, arguments);
            this.getModel("view").setProperty("/case", null);
        },
        getCaseNumberInput: function getCaseNumberInput() {
            return this.byId("inpCaseNumber");
        },
        onCaseNotFound: function onCaseNotFound() {
            if (this.getModel("view").getProperty("/edit")) {
                _ExpertiseEdit2.default.onCaseNotFound.apply(this, arguments);
            } else {
                if (this.byId("dlgCourtCase").isOpen()) {
                    this.byId("dlgCourtCase").close();
                }
                _MessageBox2.default.warning(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
            }
        },
        onCaseLoaded: function onCaseLoaded(oData) {
            if (this.getModel("view").getProperty("/edit")) {
                _ExpertiseEdit2.default.onCaseLoaded.apply(this, arguments);
                this.saveDraft();
            } else {
                this.getModel("view").setProperty("/case", oData);
            }
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
            if (this.getModel("view").getProperty("/case") === null) {
                this.onLoadCase();
            }
            this.byId("dlgCourtCase").open();
        },
        onCloseCourtCaseDialog: function onCloseCourtCaseDialog() {
            this.byId("dlgCourtCase").close();
        },
        onOpenLocationMapDialog: function onOpenLocationMapDialog() {
            this.byId("dlgLocationMap").open();
        },
        onCloseLocationMapDialog: function onCloseLocationMapDialog() {
            this.byId("dlgLocationMap").close();
        }
    }));
    return exports.default;
});
//# sourceMappingURL=Expertise.controller.js.map
