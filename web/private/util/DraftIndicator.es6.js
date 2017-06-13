import Label from "sap/m/Label";
import Control from "sap/ui/core/Control";
import sapui from "sap/ui/Global"
import jQuery from "jquery.sap.global";

const oBundle = sapui.getCore().getLibraryResourceBundle("sap.m"),
    sSaving = oBundle.getText("DRAFT_INDICATOR_SAVING_DRAFT"),
    sSaved = oBundle.getText("DRAFT_INDICATOR_DRAFT_SAVED");

const fnStateToText = sState => {
    switch (sState) {
        case "Saved":
            return sSaved;
        case "Saving":
            return sSaving;
        default:
            return "";
    }
};

export default Control.extend("spet.sbwo.web.private.util.DraftIndicator", {
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

    init() {
        this.setAggregation("_label", new Label());
        this._ts = new Date().getTime();
    },

    setState(sState) {
        let iTs = new Date().getTime(),
            iDelay = this.getDelay();
        this.setProperty("state", sState);
        if (!this._queued) {
            if (iTs - this._ts > iDelay) {
                this._updateLabel();
            } else {
                this._queued = true;
                jQuery.sap.delayedCall(iDelay - (iTs - this._ts), this, this._updateLabel);
            }
        }
    },

    _updateLabel() {
        let sState = this.getState(),
            oLabel = this.getAggregation("_label");
        this._queued = false;
        if (this._oldState !== sState) {
            this._ts = new Date().getTime();
        }
        this._oldState = sState;
        oLabel.setText(fnStateToText(sState));
    },

    renderer(oRm, oControl) {
        oRm.write("<div");
        oRm.writeControlData(oControl);
        oRm.addClass("sapMDraftIndicator");
        oRm.writeClasses();
        oRm.write(">");
        oRm.renderControl(oControl.getAggregation("_label"));
        oRm.write("</div>");
    }
});
