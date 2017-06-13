sap.ui.define(["sap/ui/core/Control", "jquery.sap.global"], function (_Control, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Control2 = _interopRequireDefault(_Control);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) {
        return typeof obj;
    } : function (obj) {
        return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj;
    };

    var fnBuildHandler = function fnBuildHandler(sName) {
        return function () {
            this.fireEvent(sName);
        };
    };

    var oEvents = {},
        oHandlers = {};

    var _iteratorNormalCompletion = true;
    var _didIteratorError = false;
    var _iteratorError = undefined;

    try {
        for (var _iterator = Object.keys(_jquerySap2.default.sap.PseudoEvents)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
            var sKey = _step.value;

            if (_typeof(_jquerySap2.default.sap.PseudoEvents[sKey]) === "object") {
                oEvents[sKey] = {};
                oHandlers["on" + sKey] = fnBuildHandler(sKey);
            }
        }

        /**
         * Link for handling indirect bindings.
         * @class
         */
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

    exports.default = _Control2.default.extend("spet.sbwo.web.private.util.EventWrapper", _jquerySap2.default.extend({}, oHandlers, {
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

        renderer: function renderer(oRm, oControl) {
            oRm.write("<div ");
            oRm.addStyle("overflow", "auto");
            oRm.writeStyles();
            oRm.writeControlData(oControl);
            oRm.write(">");
            oRm.renderControl(oControl.getControl());
            oRm.write("</div>");
        }
    }));
    return exports.default;
});
//# sourceMappingURL=EventWrapper.js.map
