sap.ui.define(["sap/ui/core/Control"], function(Control) {

    var sKey,
        fnBuildHandler = function(sName) {
            return function() {
                this.fireEvent(sName);
            };
        },
        oEvents = {},
        oHandlers = {};

    for (sKey in jQuery.sap.PseudoEvents) {
        if (jQuery.sap.PseudoEvents.hasOwnProperty(sKey) && typeof jQuery.sap.PseudoEvents[sKey] === "object") {
            oEvents[sKey] = {};
            oHandlers["on" + sKey] = fnBuildHandler(sKey);
        }
    }

    /**
    * Link for handling indirect bindings.
    * @class
    */
    return Control.extend("spet.sbwo.web.util.EventWrapper", jQuery.extend({}, oHandlers, {
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

        renderer: function(oRm, oControl) {
            oRm.write("<div ");
            oRm.addStyle("overflow", "auto");
            oRm.writeStyles();
            oRm.writeControlData(oControl);
            oRm.write(">");
            oRm.renderControl(oControl.getControl());
            oRm.write("</div>");
        }

    }));

});
