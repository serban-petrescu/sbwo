import Base from "./Base";
import PersonEdit from "spet/sbwo/web/private/controller/mixin/PersonEdit";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.wizard.PersonCreate", jQuery.extend({}, PersonEdit, {
    getEntityRoute() {
        return "person";
    },

    getCreateApiPath() {
        return "/private/api/rest/person/create";
    },

    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.getRouter().getRoute("person-create").attachPatternMatched(this.onRouteMatched, this);
    },

    getInitialDataModelContent() {
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

    getInitialViewModelContent() {
        return {
            draft: "Clear",
            wizard: {
                step: 0,
                validity: [true, true, true, true]
            }
        };
    }
}));
