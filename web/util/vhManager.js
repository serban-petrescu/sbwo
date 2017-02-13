sap.ui.define([], function() {
	var oComponent, 
		oRootView,
		mDialogs = {};
	
	return {
		initialize: function(oC, oV) {
			oComponent = oC;
			oRootView = oV;
		},
		
		get: function(sName) {
			if (sName.indexOf("spet") !== 0) {
				sName = "spet.sbwo.web.view.vh." + sName;
			}
			
			if (mDialogs.hasOwnProperty(sName)) {
				return mDialogs[sName];
			}
			else {
				return oComponent.runAsOwner(function() {
					var oView = sap.ui.xmlview(sName);
					mDialogs[sName] = oView.getController();
					oRootView.addDependent(oView);
					return oView.getController();
				});
			}
		}
	};
});