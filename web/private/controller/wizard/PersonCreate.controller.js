sap.ui.define([
    "./Base",
    "spet/sbwo/web/controller/facade/PersonEdit"
], function(Base, PersonEdit) {
    "use strict";

    return Base.extend("spet.sbwo.web.controller.wizard.PersonCreate", jQuery.extend({}, PersonEdit, {

        getEntityRoute: function() {
            return "person";
        },

        getCreateApiPath: function() {
            return "/private/api/rest/person/create";
        },

        onInit: function() {
            Base.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("person-create").attachPatternMatched(this.onRouteMatched, this);
        },

        getInitialDataModelContent: function() {
            return {
                type: 0,
                location: {
                    address: "",
                    country: {
                        id: null,
                        name: ""
                    },
                    region: {
                        id: null,
                        name: ""
                    },
                    administrativeUnit: null
                },
                bankAccounts: [],
                telephones: [],
                emailAddresses: [],
                name: null,
                idNumber: null,
                regNumber: null,
                firstName: null,
                lastName: null,
                personalNumber: null,
                identityCardType: null,
                identityCardNumber: null,
                jointStock: null
            };
        },

        getInitialViewModelContent: function() {
            return {
                draft: "Clear",
                wizard: {
                    step: 0,
                    validity: [true, true, true, true]
                }
            };
        }
    }));

});
