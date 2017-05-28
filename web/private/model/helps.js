sap.ui.define([], function() {
    var oCache = null;

    var mRoutes = {
        "person": "person-entity",
        "person-create": "person-create"
    };

    var fnGetPages = function(oBundle) {
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

    return {
        count: 2,
        pages: function(oBundle) {
            return fnGetPages(oBundle);
        },
        route: function(oBundle, sRoute) {
            var sPage = mRoutes[sRoute];
            if (sPage) {
                return fnGetPages(oBundle)[sPage];
            }
            else {
                return null;
            }
        }
    };
});
