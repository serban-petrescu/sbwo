import DateFormat from "sap/ui/core/format/DateFormat";
import jQuery from "jquery.sap.global";

let oBundle;

const mSearchIcons = {
    "PERSON": {
        "-2": "sap-icon://arobase",
        "-1": "sap-icon://phone",
        "0": "sap-icon://customer",
        "1": "sap-icon://group",
        "": "sap-icon://person-placeholder"
    },
    "EXPERTISE": "sap-icon://official-service"
};

const PERSON_TYPE_DEFAULT = "txtEnumValueUnknown";
const PERSON_TYPE_TO_I18N = new Map([
    [0, "txtPersonTypeNatural"],
    [1, "txtPersonTypeJuridical"]
]);

const IDENTITY_CARD_DEFAULT = "txtIndentityCardTypeOther";
const IDENTITY_CARD_TO_I18N = new Map([
    [0, "txtIndentityCardTypeCard"],
    [1, "txtIndentityCardTypeBulletin"],
    [2, "txtIndentityCardTypePassport"]
]);

const EXPERTISE_STATUS_DEFAULT = "txtEnumValueUnknown";
const EXPERTISE_STATUS_TO_I18N = new Map([
    [0, "txtExpertiseStatusNotStudied"],
    [1, "txtExpertiseStatusStudied"],
    [2, "txtExpertiseStatusSubmitted"],
    [3, "txtExpertiseStatusSupplement"],
    [4, "txtExpertiseStatusFinalized"]
]);

const fnEntityPageTitle = (bLoaded, sDefault, sPattern, ...aArgs) => {
    return bLoaded ? jQuery.sap.formatMessage(sPattern, aArgs) : sDefault;
};

const oDisplayDateFormat = DateFormat.getDateInstance({
        pattern: "dd.MM.yyyy"
    }),
    oDisplayTimeFormat = DateFormat.getTimeInstance({
        pattern: "HH:mm:ss"
    }),
    oDisplayDateTimeFormat = DateFormat.getDateTimeInstance({
        pattern: "dd.MM.yyyy HH:mm:ss"
    }),
    oBackendDateFormat = DateFormat.getDateInstance({
        pattern: "yyyy-MM-dd"
    }),
    oBackendTimeFormat = DateFormat.getTimeInstance({
        pattern: "HH:mm:ss.SSSSSSSSS"
    }),
    oBackendDateTimeFormat = DateFormat.getDateTimeInstance({
        pattern: "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"
    });

const fnToDate = (oDate, oFormat) => {
    if (oDate instanceof Date) {
        return oDate;
    } else if (typeof oDate === "number") {
        return new Date(oDate);
    } else {
        return oFormat.parse(oDate);
    }
};

const fnFormatDate = oDate => oDisplayDateFormat.format(fnToDate(oDate, oBackendDateFormat));

const fnFormatTime = oDate => oDisplayTimeFormat.format(fnToDate(oDate, oBackendTimeFormat));

const fnFormatDateTime = oDate => oDisplayDateTimeFormat.format(fnToDate(oDate, oBackendDateTimeFormat));

export default {

    setResourceBundle(oResourceBundle) {
        oBundle = oResourceBundle;
    },

    pattern(sPattern) {
        return jQuery.sap.formatMessage(sPattern, Array.prototype.slice.call(arguments, 1));
    },

    entityPageTitle: fnEntityPageTitle,

    courtCaseDocument(sType, sNumber, sDate) {
        if (sType && sNumber && sDate) {
            return oBundle.getText("txtCourtCaseDocumentFullPattern", [sType, sNumber, fnFormatDate(sDate)]);
        } else if (sType && sDate) {
            return oBundle.getText("txtCourtCaseDocumentPartialPattern", [sType, fnFormatDate(sDate)]);
        } else if (sType) {
            return sType;
        } else {
            return "";
        }
    },

    date: fnFormatDate,

    time: fnFormatTime,

    dateTime: fnFormatDateTime,

    personPageTitle(bLoaded, sDefault, sPattern, iType, sFirstName, sLastName, sName) {
        if (iType === 0) {
            sName = oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
        }
        return fnEntityPageTitle(bLoaded, sDefault, sPattern, sName);
    },

    helpPageTitle(sDefault, sPattern, sName) {
        return fnEntityPageTitle(!!sName, sDefault, sPattern, sName);
    },

    entityIcon(iType, iSubtype) {
        let oType = mSearchIcons[iType + ""];
        if (typeof oType === "object") {
            return oType[iSubtype + ""] || oType[""] || "sap-icon://question-mark";
        } else if (typeof oType === "string") {
            return oType;
        }
        return "sap-icon://question-mark";
    },

    personName(iType, sFirstName, sLastName, sName) {
        if (iType === 0 || iType === "NATURAL") {
            return oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
        } else {
            return sName;
        }
    },

    personType(iType) {
        return oBundle.getText(PERSON_TYPE_TO_I18N.get(iType) || PERSON_TYPE_DEFAULT);
    },

    identityCardType(iType) {
        return oBundle.getText(IDENTITY_CARD_TO_I18N.get(iType) || IDENTITY_CARD_DEFAULT);
    },

    expertiseStatus(iStatus) {
        return oBundle.getText(EXPERTISE_STATUS_TO_I18N.get(iStatus) || EXPERTISE_STATUS_DEFAULT);
    },

    address(sCountry, sRegion, sAddress) {
        if (sCountry && sRegion && sAddress) {
            return oBundle.getText("txtFullyQualifiedAddressText", [sCountry, sRegion, sAddress]);
        } else if (sAddress) {
            return sAddress;
        } else if (sCountry && sRegion) {
            return oBundle.getText("txtPartialRegionalAddressText", [sCountry, sRegion]);
        } else {
            return oBundle.getText("txtUnknownAddressText");
        }
    }

};
