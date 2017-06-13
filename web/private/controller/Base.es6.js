import Controller from "sap/ui/core/mvc/Controller";
import History from "sap/ui/core/routing/History";
import JSONModel from "sap/ui/model/json/JSONModel";
import Input from "sap/m/Input";
import Filter from "sap/ui/model/Filter";
import FilterOperator from "sap/ui/model/FilterOperator";
import MessageBox from "sap/m/MessageBox";
import jQuery from "jquery.sap.global";
import sapm from "sap/m/library";

const PARSERS = new Map([
    ["integer", parseInt],
    ["float", parseFloat]
]);

const ACTIONS = new Map([
    ["email", s => sapm.URLHelper.triggerEmail(s)],
    ["phone", s => sapm.URLHelper.triggerTel(s)],
    ["sms", s => sapm.URLHelper.triggerSms(s)],
    ["link", s => sapm.URLHelper.redirect(s, true)],
    ["download", s => sapm.URLHelper.redirect(s, false)],
]);

const ROUTES_FOR_ENTITIES = new Map([
    ["PERSON", "person"],
    ["EXPERTISE", "expertise"]
]);

export default Controller.extend("spet.sbwo.web.private.controller.Base", {
    /**
     * Convenience method for accessing the router in every controller of the application.
     * @public
     * @returns {Router} the router for this component
     */
    getRouter() {
        return this.getOwnerComponent().getRouter();
    },

    /**
     * Convenience method for getting the view model by name in every controller of the application.
     * @public
     * @param {string} sName the model name
     * @returns {sap.ui.model.Model} the model instance
     */
    getModel(sName) {
        return this.getView().getModel(sName) || this.getOwnerComponent().getModel(sName);
    },

    /**
     * Convenience method for setting the view model in every controller of the application.
     * @public
     * @param {Model}	oModel	the model instance
     * @param {string=} sName	the model name
     * @returns {View} the view instance
     */
    setModel(oModel, sName) {
        return this.getView().setModel(oModel, sName);
    },

    /**
     * Convenience method for getting the resource bundle.
     * @public
     * @returns {ResourceBundle} The resource bundle of the component
     */
    getResourceBundle() {
        return this.getOwnerComponent().getModel("i18n").getResourceBundle();
    },

    /**
     * Event handler for navigating back.
     * It checks if there is a history entry. If yes, history.go(-1) will happen.
     * If not, it will replace the current entry of the browser history with the master route.
     * @public
     */
    onNavBack() {
        var sPreviousHash = History.getInstance().getPreviousHash();

        if (sPreviousHash !== undefined) {
            // The history contains a previous entry
            history.go(-1);
        } else {
            // Otherwise we go backwards with a forward history
            this.getRouter().navTo("home", {}, /* bReplace = */ true);
        }
    },

    /**
     * Utility method for building a view-specific model.
     * @param	{object=}	oData	The data to be held in the view model.
     * @returns	{Controller}	The current instance.
     */
    buildViewModel(oData) {
        this.setModel(new JSONModel(oData), "view");
        return this;
    },

    /**
     * Refreshes the OData model.
     */
    onRefreshODataContent() {
        this.getModel().refresh();
    },

    /**
     * Event handler for value changes.
     * @protected
     * @param	{Event}		oEvent		Event object.
     * @returns {void}
     */
    onValueChanged(oEvent) {
        if (!oEvent || !(oEvent.getSource() instanceof Input)) {
            return;
        }

        let oInput = oEvent.getSource(),
            oDataModel = this.getModel("data"),
            sContextPath = oInput.data("context"),
            oContext = this.getModel("context").getProperty(sContextPath),
            sValue = oInput.getValue(),
            oBundle = this.getResourceBundle(),
            oBinding = oInput.getBindingContext("data"),
            sDataBasePath = oBinding ? oBinding.getPath() : "";

        const fnGetText = sText => sText.indexOf("i18n>") === 0 ? oBundle.getText(sText.substring(5)) : sText;
        const fnTrimPath = sPath => sPath + (sPath.lastIndexOf("/") === sPath.length - 1 ? "" : "/");

        const fnProcessCapture = function (oCtxt, sCap) {
            let sPath = fnTrimPath(sDataBasePath) + oCtxt.target;
            let sCapture = oCtxt.caseInsensitive ? (sCap || "").toLowerCase() : sCap;
            if (oCtxt.value) {
                if (oCtxt.force || !oDataModel.getProperty(sPath)) {
                    for (let sKey of Object.keys(oCtxt.value)) {
                        if (sKey === sCapture) {
                            oDataModel.setProperty(sPath, oCtxt.value[sKey]);
                            return;
                        }
                    }
                    if (oCtxt.hasOwnProperty("defaultValue")) {
                        oDataModel.setProperty(sPath, fnGetText(oCtxt.defaultValue));
                    }
                }
            } else {
                oDataModel.setProperty(sPath, PARSERS.get(oCtxt.type) || (o => o));
            }
        };

        if (sDataBasePath && sContextPath && oContext) {
            if (sValue) {
                let aMatch = sValue.match(oContext.regex);
                if (aMatch && aMatch.length) {
                    oInput.setValueState("None");
                    if (oContext.captures) {
                        for (let i = 1; i < aMatch.length && i <= oContext.captures.length; ++i) {
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
    doExternalAction(oEvent) {
        let oControl = oEvent.getSource(),
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
    applyContainsFilter(sQuery, sAttribute, oBinding) {
        oBinding.filter(sQuery ? new Filter(sAttribute, FilterOperator.Contains, sQuery) : []);
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
    applySearchFilter(sQuery, sAttribute, oBinding) {
        let aFilters = this.getSearchFilters(sQuery, sAttribute);
        const fnApplyAnd = function (aFlt) {
            return new Filter({
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
    getSearchFilters(sQuery, sAttribute) {
        const fnMap = sPart => new Filter(sAttribute, FilterOperator.Contains, (sPart || "").toUpperCase());
        const fnFilter = sPart => !!sPart;
        return (sQuery || "").split(" ").filter(fnFilter).map(fnMap);
    },

    /**
     * Retrieves the route name for the given entity type.
     */
    getRouteForEntityType(sType) {
        return ROUTES_FOR_ENTITIES.get(sType);
    },

    /**
     * Navigates to an entity based on the entity type and id.
     */
    navigateToEntity(iType, iId) {
        var oRouter = this.getRouter(),
            sRoute = this.getRouteForEntityType(iType);
        if (sRoute) {
            oRouter.navTo(sRoute, {
                id: iId
            });
        }
    },

    get(sUrl, fnSuccess) {
        return jQuery.ajax({
            url: sUrl,
            method: "GET",
            success: fnSuccess.bind(this),
            error: this.onRestApiError.bind(this)
        });
    },

    post(sUrl, oData, fnSuccess) {
        return jQuery.ajax({
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

    put(sUrl, oData, fnSuccess) {
        return jQuery.ajax({
            url: sUrl,
            method: "PUT",
            contentType: "json",
            success: fnSuccess.bind(this),
            data: JSON.stringify(oData),
            error: this.onRestApiError.bind(this)
        });
    },

    del(sUrl, fnSuccess) {
        return jQuery.ajax({
            url: sUrl,
            method: "DELETE",
            success: fnSuccess.bind(this),
            error: this.onRestApiError.bind(this)
        });

    },

    onRestApiError(oXhr) {
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

    onNotFoundError( /* oXhr */ ) {
        MessageBox.error(this.getResourceBundle().getText("txtErrorNotFoundText"));
    },

    onTechnicalError( /* oXhr */ ) {
        MessageBox.error(this.getResourceBundle().getText("txtErrorTechnicalText"));
    },

    onInternalServerError(oXhr) {
        var oBundle = this.getResourceBundle();

        const fnKeyExists = sKey => oBundle.hasText(sKey) || oBundle.getText(sKey) !== sKey;

        const fnTranformToHyphens = sString => (sString || "").replace(/_/g, "-").toLowerCase();

        const fnTransformToCamelCase = sString => jQuery.sap.camelCase((sString || "").replace(/-+/g, "-").toLowerCase());

        const fnGetControlErrorText = function (sError, sEntity) {
            let sKey = fnTransformToCamelCase("txt-error-control-" + jQuery.sap.hyphen(sEntity) + "-" +
                fnTranformToHyphens(sError) + "-text");
            if (fnKeyExists(sKey)) {
                return oBundle.getText(sKey);
            }
            sKey = fnTransformToCamelCase("txt-error-control-" + fnTranformToHyphens(sError) + "-text");
            if (fnKeyExists(sKey)) {
                return oBundle.getText(sKey);
            }
            return oBundle.getText("txtErrorControlText");
        };

        const fnGetDatabaseErrorText = function (sError) {
            let sKey = fnTransformToCamelCase("txt-error-database-" + fnTranformToHyphens(sError) + "-text");
            if (fnKeyExists(sKey)) {
                return oBundle.getText(sKey);
            }
            return oBundle.getText("txtErrorDatabaseText");
        };

        const fnGetErrorObject = function () {
            try {
                return JSON.parse(typeof oXhr === "string" ? oXhr : oXhr.responseText) || {};
            } catch (e) {
                return {};
            }
        };

        let oError = fnGetErrorObject();

        switch (oError.type) {
            case "CONTROL":
                if (oError.error && oError.entity) {
                    if (oError.cause) {
                        MessageBox.error(fnGetControlErrorText(oError.error, oError.entity), {
                            details: fnGetDatabaseErrorText(oError.cause.error) +
                                (oError.cause.details ? "\n" + oError.cause.details : "")
                        });
                    } else {
                        MessageBox.error(fnGetControlErrorText(oError.error, oError.entity));
                    }
                } else if (oError.cause) {
                    MessageBox.error(fnGetDatabaseErrorText(oError.cause.error), {
                        details: oError.cause.details || undefined
                    });
                } else {
                    MessageBox.error(this.getResourceBundle().getText("txtErrorTechnicalText"));
                }
                break;
            case "DATABASE":
                MessageBox.error(fnGetDatabaseErrorText(oError.error), {
                    details: oError.details || undefined
                });
                break;
            default:
                MessageBox.error(this.getResourceBundle().getText("txtErrorTechnicalText"), {
                    details: oError.details || undefined
                });
                break;
        }
    }
});
