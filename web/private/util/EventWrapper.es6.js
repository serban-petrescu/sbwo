import Control from "sap/ui/core/Control";
import jQuery from "jquery.sap.global";

const fnBuildHandler = sName => function () {
    this.fireEvent(sName);
};

let oEvents = {},
    oHandlers = {};

for (let sKey of Object.keys(jQuery.sap.PseudoEvents)) {
    if (typeof jQuery.sap.PseudoEvents[sKey] === "object") {
        oEvents[sKey] = {};
        oHandlers["on" + sKey] = fnBuildHandler(sKey);
    }
}

/**
 * Link for handling indirect bindings.
 * @class
 */
export default Control.extend("spet.sbwo.web.private.util.EventWrapper", jQuery.extend({}, oHandlers, {
    metadata: {
        aggregations: {
            control: {
                type: "sap.ui.core.Control",
                multiple: false
            }
        },
        events: oEvents,
        defaultAggregation: "control"
    },

    renderer(oRm, oControl) {
        oRm.write("<div ");
        oRm.addStyle("overflow", "auto");
        oRm.writeStyles();
        oRm.writeControlData(oControl);
        oRm.write(">");
        oRm.renderControl(oControl.getControl());
        oRm.write("</div>");
    }

}));
