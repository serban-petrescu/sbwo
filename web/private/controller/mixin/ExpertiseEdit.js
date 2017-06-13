sap.ui.define(["./BaseEdit", "sap/ui/model/Filter", "spet/sbwo/web/private/util/vhManager", "jquery.sap.global"], function (_BaseEdit, _Filter, _vhManager, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _BaseEdit2 = _interopRequireDefault(_BaseEdit);

    var _Filter2 = _interopRequireDefault(_Filter);

    var _vhManager2 = _interopRequireDefault(_vhManager);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _jquerySap2.default.extend({}, _BaseEdit2.default, {
        onAddExpertiseFine: function onAddExpertiseFine(oEvent) {
            this.onAddCollection(oEvent, "fines", {
                date: new Date().getTime(),
                sum: 0
            });
        },
        onDeleteExpertiseLastFine: function onDeleteExpertiseLastFine(oEvent) {
            this.onDeleteCollectionLastItem(oEvent, "fines");
        },
        getCaseNumberInput: function getCaseNumberInput() {
            // must be overriden by target controllers
        },
        getNextHearing: function getNextHearing(oData) {
            var sMax = "";
            if (oData && oData.hearings) {
                var _iteratorNormalCompletion = true;
                var _didIteratorError = false;
                var _iteratorError = undefined;

                try {
                    for (var _iterator = oData.hearings[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                        var oHearing = _step.value;

                        sMax = sMax > oHearing.date ? sMax : oHearing.date;
                    }
                } catch (err) {
                    _didIteratorError = true;
                    _iteratorError = err;
                } finally {
                    try {
                        if (!_iteratorNormalCompletion && _iterator.return) {
                            _iterator.return();
                        }
                    } finally {
                        if (_didIteratorError) {
                            throw _iteratorError;
                        }
                    }
                }
            }
            return sMax === "" ? null : sMax;
        },
        onLoadCase: function onLoadCase() {
            var _this = this;

            var sNumber = this.getModel("data").getProperty("/number");
            this.getModel("view").setProperty("/case", null);
            if (sNumber) {
                this.get("/private/api/rest/expertise/case?number=" + encodeURIComponent(sNumber), function (oData) {
                    return oData ? _this.onCaseLoaded(oData) : _this.onCaseNotFound();
                });
            }
        },
        onCaseLoaded: function onCaseLoaded(oData) {
            this.getModel("view").setProperty("/case", oData);
            this.getModel("data").setProperty("/nextHearing", this.getNextHearing(oData));
            if (oData.matter) {
                this.getModel("data").setProperty("/title", oData.matter.substring(0, 100));
            }
        },
        onCaseNotFound: function onCaseNotFound() {
            var oInput = this.getCaseNumberInput();
            if (oInput) {
                oInput.setValueState("Warning");
                oInput.setValueStateText(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
            }
        },
        onCourtLoaded: function onCourtLoaded(oData) {
            var oModel = this.getModel("data");
            oModel.setProperty("/court/code", oData.Code);
            oModel.setProperty("/court/id", oData.Id);
            oModel.setProperty("/court/name", oData.Name);
        },
        onCourtClear: function onCourtClear() {
            this.getModel("data").setProperty("/court", {
                id: null,
                name: "",
                code: ""
            });
        },
        onCaseNumberChanged: function onCaseNumberChanged(oEvent) {
            var _this2 = this;

            var oModel = this.getModel("data"),
                sOldCode = oModel.getProperty("/court/code"),
                sNewCode;

            var fnUpdateCourt = function fnUpdateCourt(oData) {
                if (oData && oData.results && oData.results.length) {
                    _this2.onCourtLoaded(oData.results[0]);
                } else {
                    _this2.onCourtClear();
                }
            };

            this.onValueChanged(oEvent);
            sNewCode = oModel.getProperty("/court/code");

            if (sOldCode !== sNewCode) {
                if (sNewCode) {
                    this.getModel().read("/Courts", {
                        filters: [new _Filter2.default("Code", "EQ", sNewCode)],
                        success: fnUpdateCourt
                    });
                } else {
                    this.onCourtClear();
                }
            }

            this.onLoadCase();
        },
        onOpenCourtValueHelp: function onOpenCourtValueHelp() {
            _vhManager2.default.get("Court").open(this.onCourtLoaded, this);
        }
    });
    return exports.default;
});
//# sourceMappingURL=ExpertiseEdit.js.map
