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

    var fnYearMatchesToObject = function fnYearMatchesToObject(aMatches) {
        return {
            negative: aMatches[1] === "-",
            year: aMatches[2] ? parseInt(aMatches[2], 10) : null,
            month: aMatches[3] ? parseInt(aMatches[3], 10) : null,
            day: aMatches[4] ? parseInt(aMatches[4], 10) : null
        };
    };

    var fnWeekMatchesToObject = function fnWeekMatchesToObject(aMatches) {
        return {
            negative: aMatches[1] === "-",
            week: aMatches[2] ? parseInt(aMatches[2], 10) : null
        };
    };

    var fnBackendToObject = function fnBackendToObject(sValue) {
        var aMatches = sValue.match(/^([+-])?P(?:(\d+)Y)?(?:(\d+)M)?(?:(\d+)D)?$/);
        if (aMatches && aMatches.length) {
            return fnYearMatchesToObject(aMatches);
        } else {
            aMatches = sValue.match(/^([+-])?P(?:(\d+)W)$/);
            if (aMatches && aMatches.length) {
                fnWeekMatchesToObject(aMatches);
            } else {
                return {};
            }
        }
    };

    var fnObjectToBackend = function fnObjectToBackend(oObject) {
        var sResult = oObject.negative ? "-P" : "P";
        if (oObject.year) {
            sResult += oObject.year + "Y";
        }
        if (oObject.month) {
            sResult += oObject.month + "M";
        }
        if (oObject.day) {
            sResult += oObject.day + "D";
        }
        if (oObject.week) {
            sResult += oObject.week + "W";
        }
        return sResult;
    };

    var fnStringToObject = function fnStringToObject(sValue) {
        var aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[yY])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+)\s*[dD])?\s*$/);
        if (aMatches && aMatches.length) {
            return fnYearMatchesToObject(aMatches);
        } else {
            aMatches = sValue.match(/^([-])?\s*(?:(\d+)\s*[wW])\s*$/);
            if (aMatches && aMatches.length) {
                fnWeekMatchesToObject(aMatches);
            } else {
                return {};
            }
        }
    };

    var fnObjectToString = function fnObjectToString(oObject) {
        var sResult = oObject.negative ? "- " : "";
        if (oObject.year) {
            sResult += oObject.year + "y ";
        }
        if (oObject.month) {
            sResult += oObject.month + "m ";
        }
        if (oObject.day) {
            sResult += oObject.day + "d ";
        }
        if (oObject.week) {
            sResult += oObject.week + "w ";
        }
        return sResult.trim();
    };

    exports.default = _SimpleType2.default.extend("spet.sbwo.web.private.util.Period", {
        constructor: function constructor() {
            _SimpleType2.default.apply(this, arguments);
        },
        formatValue: function formatValue(oValue, sInternalType) {
            var sType = this.getPrimitiveType(sInternalType);
            if (sType === "string" && sType === "any") {
                if (oValue == null) {
                    return "";
                }
                return fnObjectToString(fnBackendToObject(oValue));
            } else {
                throw new _FormatException2.default("Don't know how to format Period to " + sInternalType);
            }
        },
        parseValue: function parseValue(oValue, sInternalType) {
            if (this.getPrimitiveType(sInternalType) === "string") {
                if (oValue == null) {
                    return null;
                }
                return fnObjectToBackend(fnStringToObject(oValue));
            } else {
                throw new _ParseException2.default("Don't know how to parse Period from " + sInternalType);
            }
        },
        validateValue: function validateValue() {}
    });
    return exports.default;
});
//# sourceMappingURL=Period.js.map
