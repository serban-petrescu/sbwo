import Controller from "sap/ui/core/mvc/Controller";
import Filter from "sap/ui/model/Filter";
import FilterOperator from "sap/ui/model/FilterOperator";

export default Controller.extend("spet.sbwo.web.private.controller.vh.Base", {
    _callback: null,

    getDialog() {
        return this.byId("dlgMain");
    },

    getList() {
        return this.byId("lstMain");
    },

    getSearchAttribute() {
        return "Search";
    },

    getSearchField() {
        return this.byId("seaMain");
    },

    onInit() {
        this.getDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
    },

    search(oEvent) {
        if (oEvent.getParameter("refreshButtonPressed")) {
            this.refresh();
        } else {
            let sAttribute = this.getSearchAttribute(),
                oBinding = this.getList().getBinding("items"),
                fnMap = sPart => new Filter(sAttribute, FilterOperator.Contains, (sPart || "").toUpperCase()),
                aFilters = oEvent.getParameter("query").split(" ").map(fnMap);
            if (aFilters.length) {
                oBinding.filter(new Filter({
                    filters: aFilters,
                    and: true
                }));
            } else {
                oBinding.filter(aFilters);
            }
        }
    },

    open(fnCallback, oListener) {
        this._callback = fnCallback && oListener ? fnCallback.bind(oListener) : fnCallback;
        this.getSearchField().setValue("");
        this.getList().getBinding("items").filter([]);
        this.getDialog().open();
    },

    close() {
        this.getDialog().close();
        if (this._callback) {
            this._callback();
            this._callback = null;
        }
    },

    select(oEvent) {
        this.getDialog().close();
        if (this._callback) {
            this._callback(oEvent.getSource().getBindingContext().getObject());
            this._callback = null;
        }
    },

    refresh() {
        this.getList().getBinding("items").refresh();
    }
});
