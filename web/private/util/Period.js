sap.ui.define([
    "sap/ui/model/SimpleType",
    "sap/ui/model/FormatException",
    "sap/ui/model/ParseException"
], function(SimpleType, FormatException, ParseException) {
    "use strict";

    var fnYearMatchesToObject = function(aMatches) {
        return {
            negative: aMatches[1] === "-",
            year: aMatches[2] ? parseInt(aMatches[2], 10) : null,
            month: aMatches[3] ? parseInt(aMatches[3], 10) : null,
            day: aMatches[4]? parseInt(aMatches[4], 10) : null
        };
    };

    var fnWeekMatchesToObject = function(aMatches) {
        return {
            negative: aMatches[1] === "-",
            week: aMatches[2] ? parseInt(aMatches[2], 10) : null
        };
    };

    var fnBackendToObject = function(sValue) {
        var aMatches = sValue.match(/^([+-])?P(?:(\d+)Y)?(?:(\d+)M)?(?:(\d+)D)?$/);
        if (aMatches && aMatches.length) {
            return fnYearMatchesToObject(aMatches);
        }
        else {
            aMatches = sValue.match(/^([+-])?P(?:(\d+)W)$/);
            if (aMatches && aMatches.length) {
                fnWeekMatchesToObject(aMatches);
            }
            else {
                return {};
            }
        }
    };

    var fnObjectToBackend = function(oObject) {
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

    var fnStringToObject = function(sValue) {
        var aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[yY])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+)\s*[dD])?\s*$/);
        if (aMatches && aMatches.length) {
            return fnYearMatchesToObject(aMatches);
        }
        else {
            aMatches = sValue.match(/^([-])?\s*(?:(\d+)\s*[wW])\s*$/);
            if (aMatches && aMatches.length) {
                fnWeekMatchesToObject(aMatches);
            }
            else {
                return {};
            }
        }
    };

    var fnObjectToString = function(oObject) {
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

    return SimpleType.extend("spet.sbwo.web.util.Period", {

        constructor: function() {
            SimpleType.apply(this, arguments);
        },

        formatValue: function(oValue, sInternalType) {
            switch (this.getPrimitiveType(sInternalType)) {
                case "string":
                case "any":
                    if (oValue == null) {
                        return "";
                    }
                    return fnObjectToString(fnBackendToObject(oValue));
                default:
                    throw new FormatException("Don't know how to format Period to " + sInternalType);
            }
        },

        parseValue: function(oValue, sInternalType) {
            switch (this.getPrimitiveType(sInternalType)) {
                case "string":
                    if (oValue == null) {
                        return null;
                    }
                    return fnObjectToBackend(fnStringToObject(oValue));
                default:
                    throw new ParseException("Don't know how to parse Period from " + sInternalType);
            }
        },

        validateValue: function() { }

    });
});
