import Base from "./Base";

export default Base.extend("spet.sbwo.web.private.controller.list.PersonList", {
    getEntityRoute() {
        return "person";
    },

    getListRoute() {
        return "person-list";
    }
});
