sap.ui.define([
	"sap/ui/model/SimpleType",
	"sap/ui/model/FormatException", 
	"sap/ui/model/ParseException"
], function(SimpleType, FormatException, ParseException) {
	"use strict";
	
	var fnMatchesToObject = function(aMatches) {
		return {
			negative: aMatches[1] === "-", 
			day: aMatches[2] ? parseInt(aMatches[2], 10) : null, 
			hour: aMatches[3] ? parseInt(aMatches[3], 10) : null, 
			minute: aMatches[4]? parseInt(aMatches[4], 10) : null,
			second: aMatches[5]? parseFloat(aMatches[5], 10) : null
		};
	};
	
	var fnBackendToObject = function(sValue) {
		var aMatches = sValue.match(/^([+-])?P(?:(\d+)D)?T(?:(\d+)H)?(?:(\d+)M)?(?:(\d+(?:\.\d+)?)S)?$/);
		if (aMatches && aMatches.length) {
			return fnMatchesToObject(aMatches);
		}
		else {
			return {};
		}
	};
	
	var fnObjectToBackend = function(oObject) {
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
	
	var fnStringToObject = function(sValue) {
		var aMatches = sValue.match(/^(\-)?\s*(?:(\d+)\s*[dD])?\s*(?:(\d+)\s*[hH])?\s*(?:(\d+)\s*[mM])?\s*(?:(\d+(?:\.\d+)?)\s*[sS])?\s*$/);
		if (aMatches && aMatches.length) {
			return fnMatchesToObject(aMatches);
		}
		else {
			return {};
		}
	};
	
	var fnObjectToString = function(oObject) {
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
	
	return SimpleType.extend("spet.sbwo.web.util.Duration", {
		
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
					throw new FormatException("Don't know how to format Duration to " + sInternalType);
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
					throw new ParseException("Don't know how to parse Duration from " + sInternalType);
			}
		},
		
		validateValue: function() { }
		
	});
});