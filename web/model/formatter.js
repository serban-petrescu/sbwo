sap.ui.define([], function() {
	var oBundle;
	
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
	
	var fnEntityPageTitle = function(bLoaded, sDefault, sPattern) {
		if (!bLoaded) {
			return sDefault;
		}
		else {
			return jQuery.sap.formatMessage(sPattern, Array.prototype.splice.call(arguments, 3));
		}
	};
	
	return {
		
		setResourceBundle: function(oResourceBundle) {
			oBundle = oResourceBundle;
		},
		
		pattern: function(sPattern) {
			return jQuery.sap.formatMessage(sPattern, Array.prototype.slice.call(arguments, 1));
		},
		
		entityPageTitle: fnEntityPageTitle,
		
		courtCaseDocument: function(sType, sNumber, sDate) {
			if (sType && sNumber && sDate) {
				return oBundle.getText("txtCourtCaseDocumentFullPattern", [sType, sNumber, sDate]);
			}
			else if (sType && sDate) {
				return oBundle.getText("txtCourtCaseDocumentPartialPattern", [sType, sDate]);
			}
			else if (sType) {
				return sType;
			}
			else {
				return "";
			}
		},
		
		personPageTitle: function(bLoaded, sDefault, sPattern, iType, sFirstName, sLastName, sName) {
			if (iType === 0) {
				sName = oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
			}
			return fnEntityPageTitle(bLoaded, sDefault, sPattern, sName);
		},
		
		helpPageTitle: function(sDefault, sPattern, sName) {
			return fnEntityPageTitle(!!sName, sDefault, sPattern, sName);
		},
		
		entityIcon: function(iType, iSubtype) {
			var oType = mSearchIcons[iType + ""];
			if (typeof oType === "object") {
				return oType[iSubtype + ""] || oType[""] || "sap-icon://question-mark";
			} else if (typeof oType === "string") {
				return oType;
			}
			return "sap-icon://question-mark";
		},
		
		personName: function(iType, sFirstName, sLastName, sName) {
			if (iType === 0 || iType === "NATURAL") {
				return oBundle.getText("txtPersonNaturalNameText", [sFirstName, sLastName]);
			}
			else {
				return sName;
			}
		},
		
		personType: function(iType) {
			switch(iType) {
				case 0: return oBundle.getText("txtPersonTypeNatural");
				case 1: return oBundle.getText("txtPersonTypeJuridical");
				default: return oBundle.getText("txtEnumValueUnknown");
			}
		},
		
		identityCardType: function(iType) {
			switch(iType) {
				case 0: return oBundle.getText("txtIndentityCardTypeCard");
				case 1: return oBundle.getText("txtIndentityCardTypeBulletin");
				case 2: return oBundle.getText("txtIndentityCardTypePassport");
				default: return oBundle.getText("txtIndentityCardTypeOther");
			}
		},
		
		expertiseStatus: function(iStatus) {
			switch(iStatus) {
				case 0: return oBundle.getText("txtExpertiseStatusNotStudied");
				case 1: return oBundle.getText("txtExpertiseStatusStudied");
				case 2: return oBundle.getText("txtExpertiseStatusSubmitted");
				case 3: return oBundle.getText("txtExpertiseStatusSupplement");
				case 4: return oBundle.getText("txtExpertiseStatusFinalized");
				default: return oBundle.getText("txtEnumValueUnknown");
			}
		},
		
		address: function(sCountry, sRegion, sAddress) {
			if (sCountry && sRegion && sAddress) {
				return oBundle.getText("txtFullyQualifiedAddressText", [sCountry, sRegion, sAddress]);
			}
			else if (sAddress) {
				return sAddress;
			}
			else if (sCountry && sRegion) {
				return oBundle.getText("txtPartialRegionalAddressText", [sCountry, sRegion]);
			}
			else {
				return oBundle.getText("txtUnknownAddressText");
			}
		}
		
	};
}, /* bExport = */ true);