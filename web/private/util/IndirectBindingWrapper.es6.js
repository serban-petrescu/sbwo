import Control from "sap/ui/core/Control";

/**
 * Link for handling indirect bindings.
 * @class
 */
export default Control.extend("spet.sbwo.web.private.util.IndirectBindingWrapper", {
    metadata: {
        properties: {
            "pattern": {
                type: "string"
            },
            "value": {
                type: "any"
            },
            "default": {
                type: "string"
            },
            "modelName": {
                type: "string",
                defaultValue: undefined
            }
        },
        aggregations: {
            control: {
                type: "sap.ui.core.Control",
                multiple: false
            }
        },
        defaultAggregation: "control"
    },

    setControl(oControl) {
        this.setAggregation("control", oControl);
        this.updateBinding();
    },

    setValue(sValue) {
        this.setProperty("value", sValue);
        this.updateBinding();
    },

    setPattern(sValue) {
        this.setProperty("pattern", sValue);
        this.updateBinding();
    },

    setDefault(sValue) {
        this.setProperty("default", sValue);
        this.updateBinding();
    },

    setModelName(sValue) {
        this.setProperty("modelName", sValue);
        this.updateBinding();
    },

    updateBinding() {
        var sPattern = this.getPattern(),
            sValue = this.getValue(),
            sDefault = this.getDefault(),
            sModel = this.getModelName(),
            oControl = this.getControl();

        if (oControl) {
            if (sPattern && sValue !== null && sValue !== undefined) {
                oControl.bindElement({
                    model: sModel,
                    path: sPattern.replace(/#/g, sValue + "")
                });
            } else if (sDefault) {
                oControl.bindElement({
                    model: sModel,
                    path: sDefault
                });
            }
        }
    },

    renderer(oRm, oControl) {
        oRm.renderControl(oControl.getControl());
    }

});
