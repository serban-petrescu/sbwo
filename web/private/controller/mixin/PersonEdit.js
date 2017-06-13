sap.ui.define(["./BaseEdit", "jquery.sap.global"], function (_BaseEdit, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _BaseEdit2 = _interopRequireDefault(_BaseEdit);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _jquerySap2.default.extend({}, _BaseEdit2.default, {
        onAddPersonEmail: function onAddPersonEmail(oEvent) {
            this.onAddCollection(oEvent, "emailAddresses", {
                name: "",
                email: "",
                primary: this.isCollectionEmpty(oEvent, "emailAddresses")
            });
        },
        onDeletePersonLastEmail: function onDeletePersonLastEmail(oEvent) {
            this.onDeleteCollectionLastItem(oEvent, "emailAddresses");
        },
        onAddPersonPhone: function onAddPersonPhone(oEvent) {
            this.onAddCollection(oEvent, "telephones", {
                name: "",
                telephone: "",
                primary: this.isCollectionEmpty(oEvent, "telephones")
            });
        },
        onDeletePersonLastPhone: function onDeletePersonLastPhone(oEvent) {
            this.onDeleteCollectionLastItem(oEvent, "telephones");
        },
        onAddPersonBankAccount: function onAddPersonBankAccount(oEvent) {
            this.onAddCollection(oEvent, "bankAccounts", {
                bank: "",
                accountNumber: "",
                primary: this.isCollectionEmpty(oEvent, "bankAccounts")
            });
        },
        onDeletePersonLastBankAccount: function onDeletePersonLastBankAccount(oEvent) {
            this.onDeleteCollectionLastItem(oEvent, "bankAccounts");
        },
        onPrimaryFlagChanged: function onPrimaryFlagChanged(oEvent) {
            var oControl = oEvent.getSource(),
                oModel = oControl.getBindingContext("data").getModel(),
                sBasePath = oControl.getBindingContext("data").getPath(),
                sPath = sBasePath.substring(0, sBasePath.lastIndexOf("/") + 1),
                iIndex = parseInt(sBasePath.substring(sBasePath.lastIndexOf("/") + 1), 10),
                iLength = oModel.getProperty(sPath + "length") || 0;

            for (var i = 0; i < iLength; ++i) {
                if (i !== iIndex) {
                    oModel.setProperty(sPath + i + "/primary", false);
                }
            }

            this.onValueChanged(oEvent);
        }
    });
    return exports.default;
});
//# sourceMappingURL=PersonEdit.js.map
