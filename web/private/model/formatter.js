sap.ui.define(["sap/ui/core/format/DateFormat", "jquery.sap.global"], function (_DateFormat, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _DateFormat2 = _interopRequireDefault(_DateFormat);

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

    var oBundle = void 0;

    var mSearchIcons = {
        "PERSON": {
            "-2": "sap-icon://arobase",
            "-1": "sap-icon://phone",
            "0": "sap-icon://customer",
            "1": "sap-icon://group",
            "": "sap-icon://person-placeholder"
        },
        "EXPERTISE": "sap-icon://official-service"
    };

    var PERSON_TYPE_DEFAULT = "txtEnumValueUnknown";
    var PERSON_TYPE_TO_I18N = new Map([[0, "txtPersonTypeNatural"], [1, "txtPersonTypeJuridical"]]);

    var IDENTITY_CARD_DEFAULT = "txtIndentityCardTypeOther";
    var IDENTITY_CARD_TO_I18N = new Map([[0, "txtIndentityCardTypeCard"], [1, "txtIndentityCardTypeBulletin"], [2, "txtIndentityCardTypePassport"]]);

    var EXPERTISE_STATUS_DEFAULT = "txtEnumValueUnknown";
    var EXPERTISE_STATUS_TO_I18N = new Map([[0, "txtExpertiseStatusNotStudied"], [1, "txtExpertiseStatusStudied"], [2, "txtExpertiseStatusSubmitted"], [3, "txtExpertiseStatusSupplement"], [4, "txtExpertiseStatusFinalized"]]);

    var fnEntityPageTitle = function fnEntityPageTitle(bLoaded, sDefault, sPattern) {
        for (var _len = arguments.length, aArgs = Array(_len > 3 ? _len - 3 : 0), _key = 3; _key < _len; _key++) {
            aArgs[_key - 3] = arguments[_key];
        }

        return bLoaded ? _jquerySap2.default.sap.formatMessage(sPattern, aArgs) : sDefault;
    };

    var oDisplayDateFormat = _DateFormat2.default.getDateInstance({
        pattern: "dd.MM.yyyy"
    }),
        oDisplayTimeFormat = _DateFormat2.default.getTimeInstance({
        pattern: "HH:mm:ss"
    }),
        oDisplayDateTimeFormat = _DateFormat2.default.getDateTimeInstance({
        pattern: "dd.MM.yyyy HH:mm:ss"
    }),
        oBackendDateFormat = _DateFormat2.default.getDateInstance({
        pattern: "yyyy-MM-dd"
    }),
        oBackendTimeFormat = _DateFormat2.default.getTimeInstance({
        pattern: "HH:mm:ss.SSSSSSSSS"
    }),
        oBackendDateTimeFormat = _DateFormat2.default.getDateTimeInstance({
        pattern: "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"
    });

    var fnToDate = function fnToDate(oDate, oFormat) {
        if (oDate instanceof Date) {
            return oDate;
        } else if (typeof oDate === "number") {
            return new Date(oDate);
        } else {
            return oFormat.parse(oDate);
        }
    };

    var fnFormatDate = function fnFormatDate(oDate) {
        return oDisplayDateFormat.format(fnToDate(oDate, oBackendDateFormat));
    };

    var fnFormatTime = function fnFormatTime(oDate) {
        return oDisplayTimeFormat.format(fnToDate(oDate, oBackendTimeFormat));
    };

    var fnFormatDateTime = function fnFormatDateTime(oDate) {
        return oDisplayDateTimeFormat.format(fnToDate(oDate, oBackendDateTimeFormat));
    };

    exports.default = {
        setResourceBundle: function setResourceBundle(oResourceBundle) {
            oBundle = oResourceBundle;
        },
        pattern: function pattern(sPattern) {
            return _jquerySap2.default.sap.formatMessage(sPattern, Array.prototype.slice.call(arguments, 1));
        },


        entityPageTitle: fnEntityPageTitle,

        courtCaseDocument: function courtCaseDocument(sType, sNumber, sDate) {
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

        personPageTitle: function personPageTitle(bLoaded, sDefault, sPattern, iType, sFirstName, sLastName, sName) {
            if (iType === 0) {
                sName = oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
            }
            return fnEntityPageTitle(bLoaded, sDefault, sPattern, sName);
        },
        helpPageTitle: function helpPageTitle(sDefault, sPattern, sName) {
            return fnEntityPageTitle(!!sName, sDefault, sPattern, sName);
        },
        entityIcon: function entityIcon(iType, iSubtype) {
            var oType = mSearchIcons[iType + ""];
            if ((typeof oType === "undefined" ? "undefined" : _typeof(oType)) === "object") {
                return oType[iSubtype + ""] || oType[""] || "sap-icon://question-mark";
            } else if (typeof oType === "string") {
                return oType;
            }
            return "sap-icon://question-mark";
        },
        personName: function personName(iType, sFirstName, sLastName, sName) {
            if (iType === 0 || iType === "NATURAL") {
                return oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
            } else {
                return sName;
            }
        },
        personType: function personType(iType) {
            return oBundle.getText(PERSON_TYPE_TO_I18N.get(iType) || PERSON_TYPE_DEFAULT);
        },
        identityCardType: function identityCardType(iType) {
            return oBundle.getText(IDENTITY_CARD_TO_I18N.get(iType) || IDENTITY_CARD_DEFAULT);
        },
        expertiseStatus: function expertiseStatus(iStatus) {
            return oBundle.getText(EXPERTISE_STATUS_TO_I18N.get(iStatus) || EXPERTISE_STATUS_DEFAULT);
        },
        address: function address(sCountry, sRegion, sAddress) {
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
    return exports.default;
});
//# sourceMappingURL=formatter.js.map
