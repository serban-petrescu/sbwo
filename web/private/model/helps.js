sap.ui.define([], function () {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    var oCache = null;

    var mRoutes = {
        "person": "person-entity",
        "person-create": "person-create"
    };

    var fnGetPages = function fnGetPages(oBundle) {
        if (oCache === null) {
            oCache = {
                "person-entity": {
                    id: "person-entity",
                    title: oBundle.getText("titHelpPagePersonEntityText"),
                    length: "00:17"
                },
                "person-create": {
                    id: "person-create",
                    title: oBundle.getText("titHelpPagePersonCreateText"),
                    length: "00:17"
                }
            };
        }
        return oCache;
    };

    exports.default = {
        count: 2,

        pages: function pages(oBundle) {
            return fnGetPages(oBundle);
        },
        route: function route(oBundle, sRoute) {
            var sPage = mRoutes[sRoute];
            if (sPage) {
                return fnGetPages(oBundle)[sPage];
            } else {
                return null;
            }
        }
    };
    return exports.default;
});
//# sourceMappingURL=helps.js.map
