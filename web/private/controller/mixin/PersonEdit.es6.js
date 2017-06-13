import BaseEdit from "./BaseEdit";
import jQuery from "jquery.sap.global";

export default jQuery.extend({}, BaseEdit, {
    onAddPersonEmail(oEvent) {
        this.onAddCollection(oEvent, "emailAddresses", {
            name: "",
            email: "",
            primary: this.isCollectionEmpty(oEvent, "emailAddresses")
        });
    },

    onDeletePersonLastEmail(oEvent) {
        this.onDeleteCollectionLastItem(oEvent, "emailAddresses");
    },

    onAddPersonPhone(oEvent) {
        this.onAddCollection(oEvent, "telephones", {
            name: "",
            telephone: "",
            primary: this.isCollectionEmpty(oEvent, "telephones")
        });
    },

    onDeletePersonLastPhone(oEvent) {
        this.onDeleteCollectionLastItem(oEvent, "telephones");
    },

    onAddPersonBankAccount(oEvent) {
        this.onAddCollection(oEvent, "bankAccounts", {
            bank: "",
            accountNumber: "",
            primary: this.isCollectionEmpty(oEvent, "bankAccounts")
        });
    },

    onDeletePersonLastBankAccount(oEvent) {
        this.onDeleteCollectionLastItem(oEvent, "bankAccounts");
    },

    onPrimaryFlagChanged(oEvent) {
        let oControl = oEvent.getSource(),
            oModel = oControl.getBindingContext("data").getModel(),
            sBasePath = oControl.getBindingContext("data").getPath(),
            sPath = sBasePath.substring(0, sBasePath.lastIndexOf("/") + 1),
            iIndex = parseInt(sBasePath.substring(sBasePath.lastIndexOf("/") + 1), 10),
            iLength = oModel.getProperty(sPath + "length") || 0;

        for (let i = 0; i < iLength; ++i) {
            if (i !== iIndex) {
                oModel.setProperty(sPath + i + "/primary", false);
            }
        }

        this.onValueChanged(oEvent);
    }
});
