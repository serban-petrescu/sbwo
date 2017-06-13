import Base from "./Base";
import PersonEdit from "spet/sbwo/web/private/controller/mixin/PersonEdit";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.entity.Person", jQuery.extend({}, PersonEdit, {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.getRouter().getRoute("person").attachPatternMatched(this.onRouteMatched, this);
    },

    getBaseApiUrl() {
        return "/private/api/rest/person";
    },

    getEntityListRoute() {
        return "person-list";
    },

    formatExportPath(sId) {
        return this.getBaseApiUrl() + "/export/" + sId;
    },

    onOpenLocationMapDialog() {
        this.byId("dlgLocationMap").open();
    },

    onCloseLocationMapDialog() {
        this.byId("dlgLocationMap").close();
    }
}));
