let oCache = null;

let mRoutes = {
    "person": "person-entity",
    "person-create": "person-create"
};

let fnGetPages = (oBundle) => {
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

export default {
    count: 2,

    pages(oBundle) {
        return fnGetPages(oBundle);
    },

    route(oBundle, sRoute) {
        let sPage = mRoutes[sRoute];
        if (sPage) {
            return fnGetPages(oBundle)[sPage];
        } else {
            return null;
        }
    }
};
