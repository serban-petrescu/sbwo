import Base from "./Base";
import Filter from "sap/ui/model/Filter";

export default Base.extend("spet.sbwo.web.private.controller.list.BaseWithTabs", {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this._buildTabsModel();
    },

    getUserAttribute() {
        return "User";
    },

    getAllFilters() {
        var aFilters = Base.prototype.getAllFilters.apply(this, arguments),
            sKey = this.getModel("view").getProperty("/currentTab");
        if (sKey !== "all") {
            aFilters.push(new Filter(this.getUserAttribute(), "EQ", parseInt(sKey, 10)));
        }
        return aFilters;
    },

    onTabChanged() {
        this._filterList();
    },

    _buildTabsModel() {
        var oUserModel = this.getModel("user"),
            iUserId = oUserModel.getProperty("/id"),
            oViewModel = this.getModel("view"),
            fnMapOther = function (oOther) {
                return {
                    key: oOther.id,
                    title: oOther.username
                };
            },
            aOthers = oUserModel.getProperty("/others"),
            aStatic = [{
                key: "all",
                title: this.getResourceBundle().getText("itfAllUsersTitle")
            }, {
                key: iUserId,
                title: oUserModel.getProperty("/username")
            }],
            aTabs = aOthers.map(fnMapOther).concat(aStatic);
        oViewModel.setProperty("/tabs", aTabs);
        oViewModel.setProperty("/currentTab", iUserId);
    }

});
