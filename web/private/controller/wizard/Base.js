sap.ui.define(["spet/sbwo/web/private/controller/Base", "sap/ui/model/json/JSONModel", "sap/ui/core/routing/HashChanger", "spet/sbwo/web/private/util/DraftService", "sap/m/Wizard", "jquery.sap.global"], function (_Base, _JSONModel, _HashChanger, _DraftService, _Wizard, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    var _HashChanger2 = _interopRequireDefault(_HashChanger);

    var _DraftService2 = _interopRequireDefault(_DraftService);

    var _Wizard2 = _interopRequireDefault(_Wizard);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    if (_Wizard2.default.CONSTANTS) {
        _Wizard2.default.CONSTANTS.SCROLL_OFFSET = 0;
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.wizard.Base", {
        _rendered: null,

        getEntityRoute: function getEntityRoute() {},
        getCreateApiPath: function getCreateApiPath() {},
        onInit: function onInit() {
            this.buildViewModel();
            this.buildDataModel();
            this._rendered = _jquerySap2.default.Deferred();
        },
        onAfterRendering: function onAfterRendering() {
            this._rendered.resolve();
        },
        buildViewModel: function buildViewModel() {
            this.setModel(new _JSONModel2.default(this.getInitialViewModelContent()), "view");
        },
        buildDataModel: function buildDataModel() {
            this.setModel(new _JSONModel2.default(this.getInitialDataModelContent()), "data");
        },
        getWizard: function getWizard() {
            return this.byId("wizMain");
        },
        getInitialDataModelContent: function getInitialDataModelContent() {
            return {};
        },
        getInitialViewModelContent: function getInitialViewModelContent() {
            return {};
        },
        onValueChanged: function onValueChanged() {
            _Base2.default.prototype.onValueChanged.apply(this, arguments);
            this.saveDraft();
        },
        onRouteMatched: function onRouteMatched() {
            this._reset();
            this.restoreDraft();
        },
        onReset: function onReset() {
            this._reset();
            _DraftService2.default.remove(_HashChanger2.default.getInstance().getHash());
        },
        onComplete: function onComplete() {
            var _this = this;

            var oData = this.getModel("data").getData();
            this.post(this.getCreateApiPath(), oData, function (sId) {
                _DraftService2.default.remove(_HashChanger2.default.getInstance().getHash());
                _this.getRouter().navTo(_this.getEntityRoute(), {
                    id: sId
                }, true);
            });
        },
        saveDraft: function saveDraft() {
            var oModel = this.getModel("view"),
                sHash = _HashChanger2.default.getInstance().getHash();
            var oData = {
                data: this.getModel("data").getData(),
                wizard: this.getModel("view").getProperty("/wizard")
            };
            oModel.setProperty("/draft", "Saving");
            _DraftService2.default.save(sHash, oData).then(function () {
                return oModel.setProperty("/draft", "Saved");
            });
            this._updateWizardModel();
        },
        restoreDraft: function restoreDraft() {
            var _this2 = this;

            _DraftService2.default.load(_HashChanger2.default.getInstance().getHash()).then(function (oData) {
                if (oData && oData.data) {
                    _this2.getModel("data").setData(oData.data);
                    _this2.getModel("view").setProperty("/wizard", oData.wizard);
                    _this2._updateWizardControl();
                }
            });
        },
        _reset: function _reset() {
            var oWizard = this.getWizard(),
                oFirstStep = oWizard.getSteps()[0];
            oWizard.discardProgress(oFirstStep);
            this.getModel("view").setData(this.getInitialViewModelContent());
            this.getModel("data").setData(this.getInitialDataModelContent());
        },
        _updateWizardModel: function _updateWizardModel() {
            this.getModel("view").setProperty("/wizard/step", this.getWizard().getProgress());
        },
        _updateWizardControl: function _updateWizardControl() {
            var _this3 = this;

            this._rendered.then(function () {
                var oWizard = _this3.getWizard(),
                    iTargetStep = _this3.getModel("view").getProperty("/wizard/step");
                oWizard.discardProgress(oWizard.getSteps()[0]);
                for (var i = 1; i < iTargetStep; ++i) {
                    oWizard.nextStep();
                }
            });
        }
    });
    return exports.default;
});
//# sourceMappingURL=Base.js.map
