sap.ui.define(["sap/ui/core/mvc/Controller", "sap/ui/core/routing/History", "sap/ui/model/json/JSONModel", "sap/m/Input", "sap/ui/model/Filter", "sap/ui/model/FilterOperator", "sap/m/MessageBox", "jquery.sap.global", "sap/m/library"], function (_Controller, _History, _JSONModel, _Input, _Filter, _FilterOperator, _MessageBox, _jquerySap, _library) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Controller2 = _interopRequireDefault(_Controller);

    var _History2 = _interopRequireDefault(_History);

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    var _Input2 = _interopRequireDefault(_Input);

    var _Filter2 = _interopRequireDefault(_Filter);

    var _FilterOperator2 = _interopRequireDefault(_FilterOperator);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    var _library2 = _interopRequireDefault(_library);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var PARSERS = new Map([["integer", parseInt], ["float", parseFloat]]);

    var ACTIONS = new Map([["email", function (s) {
        return _library2.default.URLHelper.triggerEmail(s);
    }], ["phone", function (s) {
        return _library2.default.URLHelper.triggerTel(s);
    }], ["sms", function (s) {
        return _library2.default.URLHelper.triggerSms(s);
    }], ["link", function (s) {
        return _library2.default.URLHelper.redirect(s, true);
    }], ["download", function (s) {
        return _library2.default.URLHelper.redirect(s, false);
    }]]);

    var ROUTES_FOR_ENTITIES = new Map([["PERSON", "person"], ["EXPERTISE", "expertise"]]);

    exports.default = _Controller2.default.extend("spet.sbwo.web.private.controller.Base", {
        /**
         * Convenience method for accessing the router in every controller of the application.
         * @public
         * @returns {Router} the router for this component
         */
        getRouter: function getRouter() {
            return this.getOwnerComponent().getRouter();
        },


        /**
         * Convenience method for getting the view model by name in every controller of the application.
         * @public
         * @param {string} sName the model name
         * @returns {sap.ui.model.Model} the model instance
         */
        getModel: function getModel(sName) {
            return this.getView().getModel(sName) || this.getOwnerComponent().getModel(sName);
        },


        /**
         * Convenience method for setting the view model in every controller of the application.
         * @public
         * @param {Model}	oModel	the model instance
         * @param {string=} sName	the model name
         * @returns {View} the view instance
         */
        setModel: function setModel(oModel, sName) {
            return this.getView().setModel(oModel, sName);
        },


        /**
         * Convenience method for getting the resource bundle.
         * @public
         * @returns {ResourceBundle} The resource bundle of the component
         */
        getResourceBundle: function getResourceBundle() {
            return this.getOwnerComponent().getModel("i18n").getResourceBundle();
        },


        /**
         * Event handler for navigating back.
         * It checks if there is a history entry. If yes, history.go(-1) will happen.
         * If not, it will replace the current entry of the browser history with the master route.
         * @public
         */
        onNavBack: function onNavBack() {
            var sPreviousHash = _History2.default.getInstance().getPreviousHash();

            if (sPreviousHash !== undefined) {
                // The history contains a previous entry
                history.go(-1);
            } else {
                // Otherwise we go backwards with a forward history
                this.getRouter().navTo("home", {}, /* bReplace = */true);
            }
        },


        /**
         * Utility method for building a view-specific model.
         * @param	{object=}	oData	The data to be held in the view model.
         * @returns	{Controller}	The current instance.
         */
        buildViewModel: function buildViewModel(oData) {
            this.setModel(new _JSONModel2.default(oData), "view");
            return this;
        },


        /**
         * Refreshes the OData model.
         */
        onRefreshODataContent: function onRefreshODataContent() {
            this.getModel().refresh();
        },


        /**
         * Event handler for value changes.
         * @protected
         * @param	{Event}		oEvent		Event object.
         * @returns {void}
         */
        onValueChanged: function onValueChanged(oEvent) {
            if (!oEvent || !(oEvent.getSource() instanceof _Input2.default)) {
                return;
            }

            var oInput = oEvent.getSource(),
                oDataModel = this.getModel("data"),
                sContextPath = oInput.data("context"),
                oContext = this.getModel("context").getProperty(sContextPath),
                sValue = oInput.getValue(),
                oBundle = this.getResourceBundle(),
                oBinding = oInput.getBindingContext("data"),
                sDataBasePath = oBinding ? oBinding.getPath() : "";

            var fnGetText = function fnGetText(sText) {
                return sText.indexOf("i18n>") === 0 ? oBundle.getText(sText.substring(5)) : sText;
            };
            var fnTrimPath = function fnTrimPath(sPath) {
                return sPath + (sPath.lastIndexOf("/") === sPath.length - 1 ? "" : "/");
            };

            var fnProcessCapture = function fnProcessCapture(oCtxt, sCap) {
                var sPath = fnTrimPath(sDataBasePath) + oCtxt.target;
                var sCapture = oCtxt.caseInsensitive ? (sCap || "").toLowerCase() : sCap;
                if (oCtxt.value) {
                    if (oCtxt.force || !oDataModel.getProperty(sPath)) {
                        var _iteratorNormalCompletion = true;
                        var _didIteratorError = false;
                        var _iteratorError = undefined;

                        try {
                            for (var _iterator = Object.keys(oCtxt.value)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                                var sKey = _step.value;

                                if (sKey === sCapture) {
                                    oDataModel.setProperty(sPath, oCtxt.value[sKey]);
                                    return;
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

                        if (oCtxt.hasOwnProperty("defaultValue")) {
                            oDataModel.setProperty(sPath, fnGetText(oCtxt.defaultValue));
                        }
                    }
                } else {
                    oDataModel.setProperty(sPath, PARSERS.get(oCtxt.type) || function (o) {
                        return o;
                    });
                }
            };

            if (sDataBasePath && sContextPath && oContext) {
                if (sValue) {
                    var aMatch = sValue.match(oContext.regex);
                    if (aMatch && aMatch.length) {
                        oInput.setValueState("None");
                        if (oContext.captures) {
                            for (var i = 1; i < aMatch.length && i <= oContext.captures.length; ++i) {
                                fnProcessCapture(oContext.captures[i - 1], aMatch[i]);
                            }
                        }
                    } else {
                        oInput.setValueState(oContext.severity || "Error");
                        oInput.setValueStateText(fnGetText(oContext.text));
                    }
                } else {
                    oInput.setValueState("None");
                }
            }
        },


        /**
         * Does an external action (such as opening the phone app).
         * The action and its target (the phone number, email, etc) should be present as
         * custom data entries on the event source.
         */
        doExternalAction: function doExternalAction(oEvent) {
            var oControl = oEvent.getSource(),
                fnAction = ACTIONS.get(oControl.data("action"));
            if (fnAction) {
                fnAction(oControl.data("target"));
            }
        },


        /**
         * Applies a simple contains filter.
         * @param	{string}	sQuery		The search query.
         * @param	{string}	sAttribute	The target search attribute.
         * @param	{Binding}	oBinding	The target binding which should be filtered.
         * @returns {void}
         */
        applyContainsFilter: function applyContainsFilter(sQuery, sAttribute, oBinding) {
            oBinding.filter(sQuery ? new _Filter2.default(sAttribute, _FilterOperator2.default.Contains, sQuery) : []);
        },


        /**
         * Applies a search filter. Search filters work by splitting the search query into words
         * and applying a filter on a single attribute. This filter will be a conjunction of Contains
         * filters for each word. The search is case insensitive (the words are converted to upper case)
         * and the target attribute is expected to be upper case as well.
         * @param	{string}	sQuery		The search query.
         * @param	{string}	sAttribute	The target search attribute.
         * @param	{Binding}	oBinding	The target binding which should be filtered.
         * @returns {void}
         */
        applySearchFilter: function applySearchFilter(sQuery, sAttribute, oBinding) {
            var aFilters = this.getSearchFilters(sQuery, sAttribute);
            var fnApplyAnd = function fnApplyAnd(aFlt) {
                return new _Filter2.default({
                    filters: aFlt,
                    and: true
                });
            };
            oBinding.filter(aFilters.length ? fnApplyAnd(aFilters) : aFilters);
        },


        /**
         * Gets the search filters. Search filters work by splitting the search query into words
         * and applying a filter on a single attribute. This filter will be a conjunction of Contains
         * filters for each word. The search is case insensitive (the words are converted to upper case)
         * and the target attribute is expected to be upper case as well.
         * @param	{string}	sQuery		The search query.
         * @param	{string}	sAttribute	The target search attribute.
         * @returns {Filter[]}	The array of filters which should be applied;
         */
        getSearchFilters: function getSearchFilters(sQuery, sAttribute) {
            var fnMap = function fnMap(sPart) {
                return new _Filter2.default(sAttribute, _FilterOperator2.default.Contains, (sPart || "").toUpperCase());
            };
            var fnFilter = function fnFilter(sPart) {
                return !!sPart;
            };
            return (sQuery || "").split(" ").filter(fnFilter).map(fnMap);
        },


        /**
         * Retrieves the route name for the given entity type.
         */
        getRouteForEntityType: function getRouteForEntityType(sType) {
            return ROUTES_FOR_ENTITIES.get(sType);
        },


        /**
         * Navigates to an entity based on the entity type and id.
         */
        navigateToEntity: function navigateToEntity(iType, iId) {
            var oRouter = this.getRouter(),
                sRoute = this.getRouteForEntityType(iType);
            if (sRoute) {
                oRouter.navTo(sRoute, {
                    id: iId
                });
            }
        },
        get: function get(sUrl, fnSuccess) {
            return _jquerySap2.default.ajax({
                url: sUrl,
                method: "GET",
                success: fnSuccess.bind(this),
                error: this.onRestApiError.bind(this)
            });
        },
        post: function post(sUrl, oData, fnSuccess) {
            return _jquerySap2.default.ajax({
                url: sUrl,
                method: "POST",
                contentType: "json",
                data: JSON.stringify(oData),
                success: fnSuccess.bind(this),
                headers: {
                    "X-CSRF-TOKEN": this.getModel().getSecurityToken()
                },
                error: this.onRestApiError.bind(this)
            });
        },
        put: function put(sUrl, oData, fnSuccess) {
            return _jquerySap2.default.ajax({
                url: sUrl,
                method: "PUT",
                contentType: "json",
                success: fnSuccess.bind(this),
                data: JSON.stringify(oData),
                error: this.onRestApiError.bind(this)
            });
        },
        del: function del(sUrl, fnSuccess) {
            return _jquerySap2.default.ajax({
                url: sUrl,
                method: "DELETE",
                success: fnSuccess.bind(this),
                error: this.onRestApiError.bind(this)
            });
        },
        onRestApiError: function onRestApiError(oXhr) {
            switch (oXhr.status) {
                case 403:
                    // do nothing because this is handeled by the global listener
                    break;
                case 404:
                    this.onNotFoundError(oXhr);
                    break;
                case 500:
                    this.onInternalServerError(oXhr);
                    break;
                default:
                    this.onTechnicalError(oXhr);
                    break;
            }
        },
        onNotFoundError: function onNotFoundError() /* oXhr */{
            _MessageBox2.default.error(this.getResourceBundle().getText("txtErrorNotFoundText"));
        },
        onTechnicalError: function onTechnicalError() /* oXhr */{
            _MessageBox2.default.error(this.getResourceBundle().getText("txtErrorTechnicalText"));
        },
        onInternalServerError: function onInternalServerError(oXhr) {
            var oBundle = this.getResourceBundle();

            var fnKeyExists = function fnKeyExists(sKey) {
                return oBundle.hasText(sKey) || oBundle.getText(sKey) !== sKey;
            };

            var fnTranformToHyphens = function fnTranformToHyphens(sString) {
                return (sString || "").replace(/_/g, "-").toLowerCase();
            };

            var fnTransformToCamelCase = function fnTransformToCamelCase(sString) {
                return _jquerySap2.default.sap.camelCase((sString || "").replace(/-+/g, "-").toLowerCase());
            };

            var fnGetControlErrorText = function fnGetControlErrorText(sError, sEntity) {
                var sKey = fnTransformToCamelCase("txt-error-control-" + _jquerySap2.default.sap.hyphen(sEntity) + "-" + fnTranformToHyphens(sError) + "-text");
                if (fnKeyExists(sKey)) {
                    return oBundle.getText(sKey);
                }
                sKey = fnTransformToCamelCase("txt-error-control-" + fnTranformToHyphens(sError) + "-text");
                if (fnKeyExists(sKey)) {
                    return oBundle.getText(sKey);
                }
                return oBundle.getText("txtErrorControlText");
            };

            var fnGetDatabaseErrorText = function fnGetDatabaseErrorText(sError) {
                var sKey = fnTransformToCamelCase("txt-error-database-" + fnTranformToHyphens(sError) + "-text");
                if (fnKeyExists(sKey)) {
                    return oBundle.getText(sKey);
                }
                return oBundle.getText("txtErrorDatabaseText");
            };

            var fnGetErrorObject = function fnGetErrorObject() {
                try {
                    return JSON.parse(typeof oXhr === "string" ? oXhr : oXhr.responseText) || {};
                } catch (e) {
                    return {};
                }
            };

            var oError = fnGetErrorObject();

            switch (oError.type) {
                case "CONTROL":
                    if (oError.error && oError.entity) {
                        if (oError.cause) {
                            _MessageBox2.default.error(fnGetControlErrorText(oError.error, oError.entity), {
                                details: fnGetDatabaseErrorText(oError.cause.error) + (oError.cause.details ? "\n" + oError.cause.details : "")
                            });
                        } else {
                            _MessageBox2.default.error(fnGetControlErrorText(oError.error, oError.entity));
                        }
                    } else if (oError.cause) {
                        _MessageBox2.default.error(fnGetDatabaseErrorText(oError.cause.error), {
                            details: oError.cause.details || undefined
                        });
                    } else {
                        _MessageBox2.default.error(this.getResourceBundle().getText("txtErrorTechnicalText"));
                    }
                    break;
                case "DATABASE":
                    _MessageBox2.default.error(fnGetDatabaseErrorText(oError.error), {
                        details: oError.details || undefined
                    });
                    break;
                default:
                    _MessageBox2.default.error(this.getResourceBundle().getText("txtErrorTechnicalText"), {
                        details: oError.details || undefined
                    });
                    break;
            }
        }
    });
    return exports.default;
});
//# sourceMappingURL=Base.js.map
