sap.ui.define(["sap/ui/core/Control"], function (_Control) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Control2 = _interopRequireDefault(_Control);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Control2.default.extend("spet.sbwo.web.private.util.IndirectBindingWrapper", {
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

        setControl: function setControl(oControl) {
            this.setAggregation("control", oControl);
            this.updateBinding();
        },
        setValue: function setValue(sValue) {
            this.setProperty("value", sValue);
            this.updateBinding();
        },
        setPattern: function setPattern(sValue) {
            this.setProperty("pattern", sValue);
            this.updateBinding();
        },
        setDefault: function setDefault(sValue) {
            this.setProperty("default", sValue);
            this.updateBinding();
        },
        setModelName: function setModelName(sValue) {
            this.setProperty("modelName", sValue);
            this.updateBinding();
        },
        updateBinding: function updateBinding() {
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
        renderer: function renderer(oRm, oControl) {
            oRm.renderControl(oControl.getControl());
        }
    });
    return exports.default;
});
//# sourceMappingURL=IndirectBindingWrapper.js.map
