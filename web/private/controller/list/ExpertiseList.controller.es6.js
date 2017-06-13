import BaseWithTabs from "./BaseWithTabs";
import formatter from "spet/sbwo/web/private/model/formatter";

export default BaseWithTabs.extend("spet.sbwo.web.private.controller.list.ExpertiseList", {
    getEntityRoute() {
        return "expertise";
    },

    getListRoute() {
        return "expertise-list";
    },

    getUserAttribute() {
        return "Responsible";
    },

    getDefaultSortSettings() {
        return [{
            path: "NextHearing",
            descending: true,
            group: false
        }];
    },

    getFormatterForColumn(sColumn) {
        return sColumn === "Status" ? formatter.expertiseStatus : null;
    }

});
