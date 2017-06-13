import SimpleType from "sap/ui/model/SimpleType";
import FormatException from "sap/ui/model/FormatException";
import ParseException from "sap/ui/model/ParseException";

const fnYearMatchesToObject = function (aMatches) {
    return {
        negative: aMatches[1] === "-",
        year: aMatches[2] ? parseInt(aMatches[2], 10) : null,
        month: aMatches[3] ? parseInt(aMatches[3], 10) : null,
        day: aMatches[4] ? parseInt(aMatches[4], 10) : null
    };
};

const fnWeekMatchesToObject = function (aMatches) {
    return {
        negative: aMatches[1] === "-",
        week: aMatches[2] ? parseInt(aMatches[2], 10) : null
    };
};

const fnBackendToObject = function (sValue) {
    let aMatches = sValue.match(/^([+-])?P(?:(\d+)Y)?(?:(\d+)M)?(?:(\d+)D)?$/);
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

const fnObjectToBackend = function (oObject) {
    let sResult = oObject.negative ? "-P" : "P";
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

const fnStringToObject = function (sValue) {
    let aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[yY])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+)\s*[dD])?\s*$/);
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

const fnObjectToString = function (oObject) {
    let sResult = oObject.negative ? "- " : "";
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

export default SimpleType.extend("spet.sbwo.web.private.util.Period", {

    constructor() {
        SimpleType.apply(this, arguments);
    },

    formatValue(oValue, sInternalType) {
        const sType = this.getPrimitiveType(sInternalType);
        if (sType === "string" && sType === "any") {
            if (oValue == null) {
                return "";
            }
            return fnObjectToString(fnBackendToObject(oValue));
        } else {
            throw new FormatException("Don't know how to format Period to " + sInternalType);
        }
    },

    parseValue(oValue, sInternalType) {
        if (this.getPrimitiveType(sInternalType) === "string") {
            if (oValue == null) {
                return null;
            }
            return fnObjectToBackend(fnStringToObject(oValue));
        } else {
            throw new ParseException("Don't know how to parse Period from " + sInternalType);
        }
    },

    validateValue() {}

});
