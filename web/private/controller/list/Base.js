sap.ui.define(["spet/sbwo/web/private/controller/Base", "sap/ui/model/Sorter", "sap/ui/model/Filter", "sap/m/GroupHeaderListItem"], function (_Base, _Sorter, _Filter, _GroupHeaderListItem) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _Sorter2 = _interopRequireDefault(_Sorter);

    var _Filter2 = _interopRequireDefault(_Filter);

    var _GroupHeaderListItem2 = _interopRequireDefault(_GroupHeaderListItem);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.list.Base", {
        _groupHeaderFormatter: null,
        _filters: [],
        _query: "",

        getEntityRoute: function getEntityRoute() /*oContext*/{},
        getListRoute: function getListRoute() {},
        getDefaultSortSettings: function getDefaultSortSettings() {
            return [];
        },
        getCustomFilterForKey: function getCustomFilterForKey() /* sKey */{
            return null;
        },
        getFormatterForColumn: function getFormatterForColumn() /* sColumn */{
            return null;
        },
        onInit: function onInit() {
            this.getRouter().getRoute(this.getListRoute()).attachPatternMatched(this.onRouteMatched, this);
            this.buildViewModel({ settings: { applied: false } });
            if (this.getViewSettingsDialog()) {
                this.getViewSettingsDialog().addStyleClass(this.getOwnerComponent().getContentDensityClass());
            }
        },
        getViewSettingsDialog: function getViewSettingsDialog() {
            return this.byId("vsdMain");
        },
        getList: function getList() {
            return this.byId("lstMain");
        },
        getSearchField: function getSearchField() {
            return this.byId("seaMain");
        },
        getSearchAttribute: function getSearchAttribute() {
            return "Search";
        },
        onSearch: function onSearch(oEvent) {
            this.getRouter().navTo(this.getListRoute(), {
                search: oEvent.getParameter("query")
            }, true);
        },
        onRouteMatched: function onRouteMatched(oEvent) {
            var sQuery = oEvent.getParameter("arguments").search || "",
                oField = this.getSearchField();
            if (oField.getValue() !== sQuery) {
                oField.setValue(sQuery);
            }
            this._query = sQuery;
            this._filterList();
        },
        onPressItem: function onPressItem(oEvent) {
            var oContext = oEvent.getSource().getBindingContext();
            this.getRouter().navTo(this.getEntityRoute(oContext), {
                id: oContext.getProperty("Id")
            });
        },
        onResetViewSettings: function onResetViewSettings() {
            var aSorters = this.getDefaultSortSettings().map(function (oSettings) {
                return new _Sorter2.default(oSettings.path, oSettings.descending, oSettings.group);
            });
            this.getModel("view").setData({ settings: { applied: false } });
            this.getList().getBinding("items").sort(aSorters);
            this._filters = [];
            this._filterList();
        },
        onOpenViewSettingsDialog: function onOpenViewSettingsDialog() {
            this.getViewSettingsDialog().open();
        },
        onViewSettingsConfirm: function onViewSettingsConfirm(oEvent) {
            var _this = this;

            var fnApplySorting = function fnApplySorting() {
                var aSorters = [],
                    oGroup = oEvent.getParameter("groupItem"),
                    oSort = oEvent.getParameter("sortItem");
                if (oGroup) {
                    aSorters.push(new _Sorter2.default(oGroup.getKey(), oEvent.getParameter("groupDescending"), true));
                    _this._groupHeaderFormatter = _this.getFormatterForColumn(oGroup.getKey());
                }
                if (oSort) {
                    aSorters.push(new _Sorter2.default(oSort.getKey(), oEvent.getParameter("sortDescending"), false));
                }
                _this.getList().getBinding("items").sort(aSorters);
            },
                fnMapParent = function fnMapParent(mKeys, sParentKey) {
                var aFilters = [];
                var _iteratorNormalCompletion = true;
                var _didIteratorError = false;
                var _iteratorError = undefined;

                try {
                    for (var _iterator = Object.keys(mKeys)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                        var sKey = _step.value;

                        if (mKeys[sKey]) {
                            aFilters.push(new _Filter2.default(sParentKey, "EQ", sKey));
                        }
                    }
                } catch (err) {
                    _didIteratorError = true;
                    _iteratorError = err;
                } finally {
                    try {
                        if (!_iteratorNormalCompletion && _iterator.return) {
                            _iterator.return();
                        }
                    } finally {
                        if (_didIteratorError) {
                            throw _iteratorError;
                        }
                    }
                }

                return aFilters;
            },
                fnBuildFilterFromArray = function fnBuildFilterFromArray(bAnd, aFilters) {
                if (aFilters.length === 0) {
                    return null;
                } else if (aFilters.length === 1) {
                    return aFilters[0];
                } else {
                    return new _Filter2.default({ filters: aFilters, and: bAnd });
                }
            },
                fnComputeFilters = function fnComputeFilters() {
                var oComposite = oEvent.getParameter("filterCompoundKeys") || {},
                    aFilters = [];

                var _iteratorNormalCompletion2 = true;
                var _didIteratorError2 = false;
                var _iteratorError2 = undefined;

                try {
                    for (var _iterator2 = Object.keys(oComposite)[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
                        var sKey = _step2.value;

                        var oFilter = fnBuildFilterFromArray(false, fnMapParent(oComposite[sKey], sKey));
                        if (oFilter) {
                            aFilters.push(oFilter);
                        }
                    }
                } catch (err) {
                    _didIteratorError2 = true;
                    _iteratorError2 = err;
                } finally {
                    try {
                        if (!_iteratorNormalCompletion2 && _iterator2.return) {
                            _iterator2.return();
                        }
                    } finally {
                        if (_didIteratorError2) {
                            throw _iteratorError2;
                        }
                    }
                }

                var oDefault = oComposite[""];
                if (oDefault) {
                    var _iteratorNormalCompletion3 = true;
                    var _didIteratorError3 = false;
                    var _iteratorError3 = undefined;

                    try {
                        for (var _iterator3 = oDefault[Symbol.iterator](), _step3; !(_iteratorNormalCompletion3 = (_step3 = _iterator3.next()).done); _iteratorNormalCompletion3 = true) {
                            var _sKey = _step3.value;

                            var oFilter = _this.getCustomFilterForKey(_sKey);
                            if (oFilter) {
                                aFilters.push(oFilter);
                            }
                        }
                    } catch (err) {
                        _didIteratorError3 = true;
                        _iteratorError3 = err;
                    } finally {
                        try {
                            if (!_iteratorNormalCompletion3 && _iterator3.return) {
                                _iterator3.return();
                            }
                        } finally {
                            if (_didIteratorError3) {
                                throw _iteratorError3;
                            }
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
        buildGroupHeader: function buildGroupHeader(oGroup) {
            var sTitle = this._groupHeaderFormatter ? this._groupHeaderFormatter(oGroup.key) : oGroup.key;
            return new _GroupHeaderListItem2.default({ title: sTitle });
        },
        getAllFilters: function getAllFilters() {
            var aFilters = this.getSearchFilters(this._query, this.getSearchAttribute());
            aFilters.push.apply(aFilters, this._filters);
            return aFilters;
        },
        _filterList: function _filterList() {
            var aFilters = this.getAllFilters(),
                oBinding = this.getList().getBinding("items");
            if (aFilters.length) {
                oBinding.filter(new _Filter2.default({
                    filters: aFilters,
                    and: true
                }));
            } else {
                oBinding.filter(aFilters);
            }
        }
    });
    return exports.default;
});
//# sourceMappingURL=Base.js.map
