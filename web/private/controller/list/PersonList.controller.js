sap.ui.define([
    "./Base"
], function(Base) {
    "use strict";

    return Base.extend("spet.sbwo.web.controller.list.PersonList", {
        getEntityRoute: function() {
            return "person";
        },

        getListRoute: function() {
            return "person-list";
        }

    });

});
