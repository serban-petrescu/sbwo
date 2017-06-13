sap.ui.define(["sap/ui/model/SimpleType", "sap/ui/model/FormatException", "sap/ui/model/ParseException"], function (_SimpleType, _FormatException, _ParseException) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _SimpleType2 = _interopRequireDefault(_SimpleType);

    var _FormatException2 = _interopRequireDefault(_FormatException);

    var _ParseException2 = _interopRequireDefault(_ParseException);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var fnMatchesToObject = function fnMatchesToObject(aMatches) {
        return {
            negative: aMatches[1] === "-",
            day: aMatches[2] ? parseInt(aMatches[2], 10) : null,
            hour: aMatches[3] ? parseInt(aMatches[3], 10) : null,
            minute: aMatches[4] ? parseInt(aMatches[4], 10) : null,
            second: aMatches[5] ? parseFloat(aMatches[5], 10) : null
        };
    };

    var fnBackendToObject = function fnBackendToObject(sValue) {
        var aMatches = sValue.match(/^([+-])?P(?:(\d+)D)?T(?:(\d+)H)?(?:(\d+)M)?(?:(\d+(?:\.\d+)?)S)?$/);
        if (aMatches && aMatches.length) {
            return fnMatchesToObject(aMatches);
        } else {
            return {};
        }
    };

    var fnObjectToBackend = function fnObjectToBackend(oObject) {
        var sResult = oObject.negative ? "-P" : "P";
        if (oObject.day) {
            sResult += oObject.year + "D";
        }
        sResult += "T";
        if (oObject.hour) {
            sResult += oObject.hour + "H";
        }
        if (oObject.minute) {
            sResult += oObject.minute + "M";
        }
        if (oObject.second) {
            sResult += oObject.second + "S";
        }
        return sResult;
    };

    var fnStringToObject = function fnStringToObject(sValue) {
        var aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[dD])?\s*(?:(\d+)\s*[hH])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+(?:\.\d+)?)\s*[sS])?\s*$/);
        if (aMatches && aMatches.length) {
            return fnMatchesToObject(aMatches);
        } else {
            return {};
        }
    };

    var fnObjectToString = function fnObjectToString(oObject) {
        var sResult = oObject.negative ? "- " : "";
        if (oObject.day) {
            sResult += oObject.day + "d ";
        }
        if (oObject.hour) {
            sResult += oObject.hour + "h ";
        }
        if (oObject.minute) {
            sResult += oObject.minute + "m ";
        }
        if (oObject.second) {
            sResult += oObject.second + "s ";
        }
        return sResult.trim();
    };

    exports.default = _SimpleType2.default.extend("spet.sbwo.web.private.util.Duration", {
        constructor: function constructor() {
            _SimpleType2.default.apply(this, arguments);
        },
        formatValue: function formatValue(oValue, sInternalType) {
            var sType = this.getPrimitiveType(sInternalType);
            if (sType === "string" || sType === "any") {
                if (oValue == null) {
                    return "";
                }
                return fnObjectToString(fnBackendToObject(oValue));
            } else {
                throw new _FormatException2.default("Don't know how to format Duration to " + sInternalType);
            }
        },
        parseValue: function parseValue(oValue, sInternalType) {
            if (this.getPrimitiveType(sInternalType) === "string") {
                if (oValue == null) {
                    return null;
                }
                return fnObjectToBackend(fnStringToObject(oValue));
            } else {
                throw new _ParseException2.default("Don't know how to parse Duration from " + sInternalType);
            }
        },
        validateValue: function validateValue() {}
    });
    return exports.default;
});
//# sourceMappingURL=Duration.js.map
