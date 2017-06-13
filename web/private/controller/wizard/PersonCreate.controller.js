sap.ui.define(["./Base", "spet/sbwo/web/private/controller/mixin/PersonEdit", "jquery.sap.global"], function (_Base, _PersonEdit, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _PersonEdit2 = _interopRequireDefault(_PersonEdit);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.wizard.PersonCreate", _jquerySap2.default.extend({}, _PersonEdit2.default, {
        getEntityRoute: function getEntityRoute() {
            return "person";
        },
        getCreateApiPath: function getCreateApiPath() {
            return "/private/api/rest/person/create";
        },
        onInit: function onInit() {
            _Base2.default.prototype.onInit.apply(this, arguments);
            this.getRouter().getRoute("person-create").attachPatternMatched(this.onRouteMatched, this);
        },
        getInitialDataModelContent: function getInitialDataModelContent() {
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
        getInitialViewModelContent: function getInitialViewModelContent() {
            return {
                draft: "Clear",
                wizard: {
                    step: 0,
                    validity: [true, true, true, true]
                }
            };
        }
    }));
    return exports.default;
});
//# sourceMappingURL=PersonCreate.controller.js.map
