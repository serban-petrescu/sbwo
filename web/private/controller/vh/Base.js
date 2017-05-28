sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/Filter",
    "sap/ui/model/FilterOperator"
], function(Controller, Filter, FilterOperator) {
    "use strict";

    return Controller.extend("spet.sbwo.web.controller.vh.Base", {
        _callback: null,

        getDialog: function() {
            return this.byId("dlgMain");
        },

        getList: function() {
            return this.byId("lstMain");
        },

        getSearchAttribute: function() {
            return "Search";
        },

        getSearchField: function() {
            return this.byId("seaMain");
        },

        onInit: function() {
            this.getDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },

        search: function(oEvent) {
            if (oEvent.getParameter("refreshButtonPressed")) {
                this.refresh();
            }
            else {
                var sAttribute = this.getSearchAttribute(),
                    sQuery = oEvent.getParameter("query"),
                    oBinding = this.getList().getBinding("items"),
                    aParts = sQuery.split(" "),
                    aFilters = aParts.map(function(sPart) {
                        return new Filter(sAttribute, FilterOperator.Contains, (sPart || "").toUpperCase());
                    });
                if (aFilters.length){
                    oBinding.filter(new Filter({
                        filters: aFilters,
                        and: true
                    }));
                }
                else {
                    oBinding.filter(aFilters);
                }
            }
        },

        open: function(fnCallback, oListener) {
            this._callback = fnCallback && oListener ? fnCallback.bind(oListener) : fnCallback;
            this.getSearchField().setValue("");
            this.getList().getBinding("items").filter([]);
            this.getDialog().open();
        },

        close: function() {
            this.getDialog().close();
            if (this._callback) {
                this._callback();
                this._callback = null;
            }
        },

        select: function(oEvent) {
            this.getDialog().close();
            if (this._callback) {
                this._callback(oEvent.getSource().getBindingContext().getObject());
                this._callback = null;
            }
        },

        refresh: function() {
            this.getList().getBinding("items").refresh();
        }
    });

});
