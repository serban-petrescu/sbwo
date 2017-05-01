sap.ui.define(["./BaseEdit"], function(BaseEdit) {
	return jQuery.extend({}, BaseEdit, {
		onAddPersonEmail: function(oEvent) {
			this.onAddCollection(oEvent, "emailAddresses", {name: "", email: "", primary: this.isCollectionEmpty(oEvent, "emailAddresses")});
		},
		
		onDeletePersonLastEmail: function(oEvent) {
			this.onDeleteCollectionLastItem(oEvent, "emailAddresses");
		},
		
		onAddPersonPhone: function(oEvent) {
			this.onAddCollection(oEvent, "telephones", {name: "", telephone: "", primary: this.isCollectionEmpty(oEvent, "telephones")});
		},
		
		onDeletePersonLastPhone: function(oEvent) {
			this.onDeleteCollectionLastItem(oEvent, "telephones");
		},
		
		onAddPersonBankAccount: function(oEvent) {
			this.onAddCollection(oEvent, "bankAccounts", {bank: "", accountNumber: "", primary: this.isCollectionEmpty(oEvent, "bankAccounts")});
		},
		
		onDeletePersonLastBankAccount: function(oEvent) {
			this.onDeleteCollectionLastItem(oEvent, "bankAccounts");
		},
		
		onPrimaryFlagChanged: function(oEvent) {
			var oControl = oEvent.getSource(),
				oModel = oControl.getBindingContext("data").getModel(),
				sBasePath = oControl.getBindingContext("data").getPath(),
				sPath = sBasePath.substring(0, sBasePath.lastIndexOf("/") + 1),
				iIndex = parseInt(sBasePath.substring(sBasePath.lastIndexOf("/") + 1), 10),
				iLength = oModel.getProperty(sPath + "length") || 0,
				i;
				
			for (i = 0; i < iLength; ++i) {
				if (i !== iIndex) {
					oModel.setProperty(sPath + i + "/primary", false);
				}
			}
			
			this.onValueChanged(oEvent);
		}
	});
});