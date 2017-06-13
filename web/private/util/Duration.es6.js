import SimpleType from "sap/ui/model/SimpleType";
import FormatException from "sap/ui/model/FormatException";
import ParseException from "sap/ui/model/ParseException";

const fnMatchesToObject = function (aMatches) {
    return {
        negative: aMatches[1] === "-",
        day: aMatches[2] ? parseInt(aMatches[2], 10) : null,
        hour: aMatches[3] ? parseInt(aMatches[3], 10) : null,
        minute: aMatches[4] ? parseInt(aMatches[4], 10) : null,
        second: aMatches[5] ? parseFloat(aMatches[5], 10) : null
    };
};

const fnBackendToObject = function (sValue) {
    const aMatches = sValue.match(/^([+-])?P(?:(\d+)D)?T(?:(\d+)H)?(?:(\d+)M)?(?:(\d+(?:\.\d+)?)S)?$/);
    if (aMatches && aMatches.length) {
        return fnMatchesToObject(aMatches);
    } else {
        return {};
    }
};

const fnObjectToBackend = function (oObject) {
    let sResult = oObject.negative ? "-P" : "P";
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

const fnStringToObject = function (sValue) {
    const aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[dD])?\s*(?:(\d+)\s*[hH])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+(?:\.\d+)?)\s*[sS])?\s*$/);
    if (aMatches && aMatches.length) {
        return fnMatchesToObject(aMatches);
    } else {
        return {};
    }
};

const fnObjectToString = function (oObject) {
    let sResult = oObject.negative ? "- " : "";
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

export default SimpleType.extend("spet.sbwo.web.private.util.Duration", {

    constructor() {
        SimpleType.apply(this, arguments);
    },

    formatValue(oValue, sInternalType) {
        const sType = this.getPrimitiveType(sInternalType);
        if (sType === "string" || sType === "any") {
            if (oValue == null) {
                return "";
            }
            return fnObjectToString(fnBackendToObject(oValue));
        } else {
            throw new FormatException("Don't know how to format Duration to " + sInternalType);
        }
    },

    parseValue(oValue, sInternalType) {
        if (this.getPrimitiveType(sInternalType) === "string") {
            if (oValue == null) {
                return null;
            }
            return fnObjectToBackend(fnStringToObject(oValue));
        } else {
            throw new ParseException("Don't know how to parse Duration from " + sInternalType);
        }
    },

    validateValue() {}

});
