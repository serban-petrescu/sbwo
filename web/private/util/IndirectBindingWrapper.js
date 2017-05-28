sap.ui.define(["sap/ui/core/Control"], function(Control) {

    /**
    * Link for handling indirect bindings.
    * @class
    */
    return Control.extend("spet.sbwo.web.util.IndirectBindingWrapper", {
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

        setControl: function(oControl) {
            this.setAggregation("control", oControl);
            this.updateBinding();
        },

        setValue: function(sValue) {
            this.setProperty("value", sValue);
            this.updateBinding();
        },

        setPattern: function(sValue) {
            this.setProperty("pattern", sValue);
            this.updateBinding();
        },

        setDefault: function(sValue) {
            this.setProperty("default", sValue);
            this.updateBinding();
        },

        setModelName: function(sValue) {
            this.setProperty("modelName", sValue);
            this.updateBinding();
        },

        updateBinding: function() {
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
                }
                else if (sDefault) {
                    oControl.bindElement({
                        model: sModel,
                        path: sDefault
                    });
                }
            }
        },

        renderer: function(oRm, oControl) {
            oRm.renderControl(oControl.getControl());
        }

    });

});
