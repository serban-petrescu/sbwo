
sap.ui.define([
	"sap/ui/model/SimpleType", 
	"sap/ui/model/type/Time"
], function(SimpleType, Time){
	
	var iOffset = (function(){
		var oToday = new Date();
		return Date.UTC(oToday.getUTCFullYear(), oToday.getUTCMonth(), oToday.getUTCDate(), 0, 0, 0);
	})();
	
	var fnDateToInt = function(oDate) {
		return oDate.getTime() - iOffset;
	};
	
	var fnIntToDate = function(iValue) {
		return new Date(iOffset + iValue);
	};
		
	return SimpleType.extend("spet.sbwo.web.util.TimeOfDay", {
		_time: null,
		
		constructor: function(oC, oF) {
			this._time = new Time(oC, oF);
		},
		
		formatValue: function(iValue, sType) {
			sType = this.getPrimitiveType(sType);
			if (iValue === null || iValue === undefined) {
				return iValue;
			}
			
			if (sType === "int") {
				return iValue;
			}
			else {
				return this._time.formatValue(fnIntToDate(iValue), sType);
			}
		},
		
		parseValue: function(oValue, sType) {
			sType = this.getPrimitiveType(sType);
			if (oValue === null || oValue === undefined) {
				return oValue;
			}
			
			if (sType === "int") {
				return oValue;
			}
			else {
				return fnDateToInt(this._time.parseValue(oValue, sType));
			}
		},
		
		validateValue: function(iValue) {
			return !iValue || (iValue > 0 && iValue < 24 * 60 * 60 * 1000);
		}
	});
	
});