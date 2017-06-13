import Base from "./Base";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.list.DeletedEntityList", {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        this.buildViewModel({
            selection:	false
        });
    },

    getEntityRoute(oContext) {
        return this.getRouteForEntityType(oContext.getProperty("Type"));
    },

    getListRoute() {
        return "deleted-list";
    },

    onRouteMatched() {
        Base.prototype.onRouteMatched.apply(this, arguments);
        this.onDataReceived();
    },

    onSelectionChange(oEvent) {
        this.getModel("view").setProperty("/selection", oEvent.getSource().getSelectedItems().length > 0);
    },

    getSelectionEntities() {
        var aItems = this.byId("lstMain").getSelectedItems(),
            aData = aItems.map(oItem => {
                var oContext = oItem.getBindingContext();
                return {
                    id:	oContext.getProperty("Id"),
                    type: oContext.getProperty("Type")
                };
            });
        return aData;
    },

    onDeleteSelected() {
        jQuery.ajax({
            method: "DELETE",
            url: "/private/api/rest/trash/delete",
            contentType: "application/json",
            data: JSON.stringify(this.getSelectionEntities()),
            success: () => this.onDataReceived()
        });
    },

    onRestoreSelected() {
        this.put("/private/api/rest/trash/restore", this.getSelectionEntities(), this.onDataReceived);
    },

    onDeleteAll() {
        this.del("/private/api/rest/trash/delete/all", this.onDataReceived);
    },

    onDataReceived() {
        this.getModel().refresh();
        this.clearSelection();
    },

    clearSelection(){
        this.byId("lstMain").removeSelections(true);
        this.getModel("view").setProperty("/selection", false);
    }

});

