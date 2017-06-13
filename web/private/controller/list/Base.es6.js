import Base from "spet/sbwo/web/private/controller/Base";
import Sorter from "sap/ui/model/Sorter";
import Filter from "sap/ui/model/Filter";
import GroupHeaderListItem from "sap/m/GroupHeaderListItem";

export default Base.extend("spet.sbwo.web.private.controller.list.Base", {
    _groupHeaderFormatter: null,
    _filters: [],
    _query: "",

    getEntityRoute(/*oContext*/){},

    getListRoute(){},

    getDefaultSortSettings() {
        return [];
    },

    getCustomFilterForKey(/* sKey */) {
        return null;
    },

    getFormatterForColumn(/* sColumn */) {
        return null;
    },

    onInit() {
        this.getRouter().getRoute(this.getListRoute()).attachPatternMatched(this.onRouteMatched, this);
        this.buildViewModel({settings: {applied: false}});
        if (this.getViewSettingsDialog()) {
            this.getViewSettingsDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
        }
    },

    getViewSettingsDialog() {
        return this.byId("vsdMain");
    },

    getList() {
        return this.byId("lstMain");
    },

    getSearchField() {
        return this.byId("seaMain");
    },

    getSearchAttribute() {
        return "Search";
    },

    onSearch(oEvent) {
        this.getRouter().navTo(this.getListRoute(), {
            search: oEvent.getParameter("query")
        }, true);
    },

    onRouteMatched(oEvent) {
        var sQuery = oEvent.getParameter("arguments").search || "",
            oField = this.getSearchField();
        if (oField.getValue() !== sQuery) {
            oField.setValue(sQuery);
        }
        this._query = sQuery;
        this._filterList();
    },

    onPressItem(oEvent) {
        var oContext = oEvent.getSource().getBindingContext();
        this.getRouter().navTo(this.getEntityRoute(oContext), {
            id: oContext.getProperty("Id")
        });
    },

    onResetViewSettings() {
        var aSorters = this.getDefaultSortSettings()
            .map(oSettings => new Sorter(oSettings.path, oSettings.descending, oSettings.group));
        this.getModel("view").setData({settings: {applied: false}});
        this.getList().getBinding("items").sort(aSorters);
        this._filters = [];
        this._filterList();
    },

    onOpenViewSettingsDialog() {
        this.getViewSettingsDialog().open();
    },

    onViewSettingsConfirm(oEvent) {
        var fnApplySorting = () => {
                var aSorters = [],
                    oGroup = oEvent.getParameter("groupItem"),
                    oSort = oEvent.getParameter("sortItem");
                if (oGroup) {
                    aSorters.push(new Sorter(oGroup.getKey(), oEvent.getParameter("groupDescending"), true));
                    this._groupHeaderFormatter = this.getFormatterForColumn(oGroup.getKey());
                }
                if (oSort) {
                    aSorters.push(new Sorter(oSort.getKey(), oEvent.getParameter("sortDescending"), false));
                }
                this.getList().getBinding("items").sort(aSorters);
            },

            fnMapParent = (mKeys, sParentKey) => {
                var aFilters = [];
                for (let sKey of Object.keys(mKeys)) {
                    if (mKeys[sKey]) {
                        aFilters.push(new Filter(sParentKey, "EQ", sKey));
                    }
                }
                return aFilters;
            },

            fnBuildFilterFromArray = (bAnd, aFilters) => {
                if (aFilters.length === 0) {
                    return null;
                }
                else if (aFilters.length === 1) {
                    return aFilters[0];
                }
                else {
                    return new Filter({filters: aFilters, and: bAnd});
                }
            },

            fnComputeFilters = () => {
                var oComposite = oEvent.getParameter("filterCompoundKeys") || {},
                    aFilters = [];

                for (let sKey of Object.keys(oComposite)) {
                    let oFilter = fnBuildFilterFromArray(false, fnMapParent(oComposite[sKey], sKey));
                    if (oFilter) {
                        aFilters.push(oFilter);
                    }
                }

                let oDefault = oComposite[""];
                if (oDefault) {
                    for (let sKey of oDefault) {
                        let oFilter = this.getCustomFilterForKey(sKey);
                        if (oFilter) {
                            aFilters.push(oFilter);
                        }
                    }
                }

                return aFilters;
            };

        fnApplySorting();
        this._filters = fnComputeFilters();
        this._filterList();
        this.getModel("view").setProperty("/settings/applied", true);
    },

    buildGroupHeader(oGroup) {
        var sTitle = this._groupHeaderFormatter ? this._groupHeaderFormatter(oGroup.key) : oGroup.key;
        return new GroupHeaderListItem({title: sTitle});
    },

    getAllFilters() {
        var aFilters = this.getSearchFilters(this._query, this.getSearchAttribute());
        aFilters.push.apply(aFilters, this._filters);
        return aFilters;
    },

    _filterList() {
        var aFilters = this.getAllFilters(),
            oBinding = this.getList().getBinding("items");
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

});
