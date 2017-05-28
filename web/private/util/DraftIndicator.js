sap.ui.define([
    "sap/m/Label",
    "sap/ui/core/Control"
], function(Label, Control) {
    var oBundle = sap.ui.getCore().getLibraryResourceBundle("sap.m"),
        sSaving = oBundle.getText("DRAFT_INDICATOR_SAVING_DRAFT"),
        sSaved = oBundle.getText("DRAFT_INDICATOR_DRAFT_SAVED");

    return Control.extend("spet.sbwo.web.util.DraftIndicator", {
        metadata: {
            properties: {
                delay: {type: "int", defaultValue: 1000},
                state: {type: "sap.m.DraftIndicatorState", defaultValue: "Clear"}
            },
            aggregations: {
                "_label": {type: "sap.m.Label", multiple: false, visibility: "hidden"}
            }
        },

        _queued: false,
        _ts: 0,
        _oldState: "",

        init: function() {
            this.setAggregation("_label", new Label());
            this._ts = new Date().getTime();
        },

        setState: function(sState) {
            var iTs = new Date().getTime(),
                iDelay = this.getDelay();
            this.setProperty("state", sState);
            if (!this._queued) {
                if (iTs - this._ts > iDelay) {
                    this._updateLabel();
                }
                else {
                    this._queued = true;
                    jQuery.sap.delayedCall(iDelay - (iTs - this._ts), this, this._updateLabel);
                }
            }
        },

        _updateLabel: function() {
            var sState = this.getState(),
                oLabel = this.getAggregation("_label");
            this._queued = false;
            if (this._oldState !== sState) {
                this._ts = new Date().getTime();
            }
            this._oldState = sState;
            switch(sState) {
            case "Saved":
                oLabel.setText(sSaved);
                break;
            case "Saving":
                oLabel.setText(sSaving);
                break;
            default:
                oLabel.setText("");
                break;
            }
        },

        renderer: function(oRm, oControl) {
            oRm.write("<div");
            oRm.writeControlData(oControl);
            oRm.addClass("sapMDraftIndicator");
            oRm.writeClasses();
            oRm.write(">");
            oRm.renderControl(oControl.getAggregation("_label"));
            oRm.write("</div>");
        }
    });

});
