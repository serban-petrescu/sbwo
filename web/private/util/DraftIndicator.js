sap.ui.define(["sap/m/Label", "sap/ui/core/Control", "sap/ui/Global", "jquery.sap.global"], function (_Label, _Control, _Global, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Label2 = _interopRequireDefault(_Label);

    var _Control2 = _interopRequireDefault(_Control);

    var _Global2 = _interopRequireDefault(_Global);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var oBundle = _Global2.default.getCore().getLibraryResourceBundle("sap.m"),
        sSaving = oBundle.getText("DRAFT_INDICATOR_SAVING_DRAFT"),
        sSaved = oBundle.getText("DRAFT_INDICATOR_DRAFT_SAVED");

    var fnStateToText = function fnStateToText(sState) {
        switch (sState) {
            case "Saved":
                return sSaved;
            case "Saving":
                return sSaving;
            default:
                return "";
        }
    };

    exports.default = _Control2.default.extend("spet.sbwo.web.private.util.DraftIndicator", {
        metadata: {
            properties: {
                delay: {
                    type: "int",
                    defaultValue: 1000
                },
                state: {
                    type: "sap.m.DraftIndicatorState",
                    defaultValue: "Clear"
                }
            },
            aggregations: {
                "_label": {
                    type: "sap.m.Label",
                    multiple: false,
                    visibility: "hidden"
                }
            }
        },

        _queued: false,
        _ts: 0,
        _oldState: "",

        init: function init() {
            this.setAggregation("_label", new _Label2.default());
            this._ts = new Date().getTime();
        },
        setState: function setState(sState) {
            var iTs = new Date().getTime(),
                iDelay = this.getDelay();
            this.setProperty("state", sState);
            if (!this._queued) {
                if (iTs - this._ts > iDelay) {
                    this._updateLabel();
                } else {
                    this._queued = true;
                    _jquerySap2.default.sap.delayedCall(iDelay - (iTs - this._ts), this, this._updateLabel);
                }
            }
        },
        _updateLabel: function _updateLabel() {
            var sState = this.getState(),
                oLabel = this.getAggregation("_label");
            this._queued = false;
            if (this._oldState !== sState) {
                this._ts = new Date().getTime();
            }
            this._oldState = sState;
            oLabel.setText(fnStateToText(sState));
        },
        renderer: function renderer(oRm, oControl) {
            oRm.write("<div");
            oRm.writeControlData(oControl);
            oRm.addClass("sapMDraftIndicator");
            oRm.writeClasses();
            oRm.write(">");
            oRm.renderControl(oControl.getAggregation("_label"));
            oRm.write("</div>");
        }
    });
    return exports.default;
});
//# sourceMappingURL=DraftIndicator.js.map
