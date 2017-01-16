sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/routing/History",
	"sap/ui/model/json/JSONModel",
	"sap/m/Input",
	"sap/ui/model/Filter",
	"sap/ui/model/FilterOperator",
	"sap/m/MessageBox"
], function(Controller, History, JSONModel, Input, Filter, FilterOperator, MessageBox) {
	"use strict";
	
	return Controller.extend("spet.sbwo.web.controller.Base", {
		/**
		 * Convenience method for accessing the router in every controller of the application.
		 * @public
		 * @returns {Router} the router for this component
		 */
		getRouter: function() {
			return this.getOwnerComponent().getRouter();
		},

		/**
		 * Convenience method for getting the view model by name in every controller of the application.
		 * @public
		 * @param {string} sName the model name
		 * @returns {sap.ui.model.Model} the model instance
		 */
		getModel: function(sName) {
			return this.getView().getModel(sName);
		},

		/**
		 * Convenience method for setting the view model in every controller of the application.
		 * @public
		 * @param {Model}	oModel	the model instance
		 * @param {string=} sName	the model name
		 * @returns {View} the view instance
		 */
		setModel: function(oModel, sName) {
			return this.getView().setModel(oModel, sName);
		},

		/**
		 * Convenience method for getting the resource bundle.
		 * @public
		 * @returns {ResourceBundle} The resource bundle of the component
		 */
		getResourceBundle: function() {
			return this.getOwnerComponent().getModel("i18n").getResourceBundle();
		},
		
		/**
		 * Event handler for navigating back.
		 * It checks if there is a history entry. If yes, history.go(-1) will happen.
		 * If not, it will replace the current entry of the browser history with the master route.
		 * @public
		 */
		onNavBack: function() {
			var sPreviousHash = History.getInstance().getPreviousHash();

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
		buildViewModel: function(oData) {
			this.setModel(new JSONModel(oData), "view");
			return this;
		},
		
		/**
		 * Refreshes the OData model.
		 */
		 onRefreshODataContent: function() {
		 	this.getModel().refresh();
		 },
		
		/** 
		 * Event handler for value changes.
		 * @protected
		 * @param	{Event}		oEvent		Event object.
		 * @returns {void}
		 */
		onValueChanged: function(oEvent) {
			if (!oEvent || !(oEvent.getSource() instanceof Input)) {
				return;
			}
			
			var oInput = oEvent.getSource(),
				oDataModel = this.getModel("data"),
				sContextPath = oInput.data("context"),
				oContext = this.getModel("context").getProperty(sContextPath),
				aMatch,
				i,
				sValue = oInput.getValue(),
				oBundle = this.getResourceBundle(),
				sDataBasePath = oInput.getBindingContext("data").getPath(),
				
				fnGetText = function(sText) {
					if (sText.indexOf("i18n>") === 0) {
						return oBundle.getText(sText.substring(5));
					}
					else {
						return sText;
					}
				},
				
				fnProcessCapture = function(oCtxt, sCapture) {
					var sKey,
						sPath = sDataBasePath + "/" + oCtxt.target;
					if (oCtxt.caseInsensitive) {
						sCapture = (sCapture || "").toLowerCase();
					}
					if (oCtxt.force || !oDataModel.getProperty(sPath)) {
						for (sKey in oCtxt.value) {
							if (oCtxt.value.hasOwnProperty(sKey) && sKey === sCapture) {
								oDataModel.setProperty(sPath, oCtxt.value[sKey]);
								return;
							}
						}
						if (oCtxt.hasOwnProperty("defaultValue")) {
							oDataModel.setProperty(sPath, fnGetText(oCtxt.defaultValue));
						}
					}
				};
				
			if (sContextPath && oContext) {
				if (sValue) {
					aMatch = sValue.match(oContext.regex);
					if (aMatch && aMatch.length) {
						oInput.setValueState("None");
						if (oContext.captures) {
							for (i = 1; i < aMatch.length && i <= oContext.captures.length; ++i) {
								fnProcessCapture(oContext.captures[i - 1], aMatch[i]);
							}
						}
					}
					else {
						oInput.setValueState(oContext.severity || "Error");
						oInput.setValueStateText(fnGetText(oContext.text));
					}
				}
				else {
					oInput.setValueState("None");
				}
			}
		},
		
		/**
		 * Does an external action (such as opening the phone app).
		 * The action and its target (the phone number, email, etc) should be present as
		 * custom data entries on the event source.
		 */
		doExternalAction: function(oEvent) {
			var oControl = oEvent.getSource();
			switch (oControl.data("action")) {
				case "email":
					sap.m.URLHelper.triggerEmail(oControl.data("target"));
					break;
				case "phone":
					sap.m.URLHelper.triggerTel(oControl.data("target"));
					break;
				case "sms":
					sap.m.URLHelper.triggerSms(oControl.data("target"));
					break;
				case "link":
					sap.m.URLHelper.redirect(oControl.data("target"), true);
					break;
			}
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
		applySearchFilter: function(sQuery, sAttribute, oBinding) {
			var aParts = sQuery.split(" "),
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
		},
		
		/**
		 * Retrieves the route name for the given entity type.
		 */
		getRouterForEntityType: function(iType) {
			switch(iType) {
				case 0: return "person";
			}
		},
		
		/**
		 * Navigates to an entity based on the entity type and id.
		 */
		navigateToEntity: function(iType, iId) {
			var oRouter = this.getRouter(),
				sRoute = this.getRouterForEntityType(iType);
			if (sRoute) {
				oRouter.navTo(sRoute, {id: iId});
			}
		},
		
		onRestApiError: function(oXhr) {
			switch (oXhr.status) {
			case 403:
				this.onForbiddenError(oXhr);
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
		
		onNotFoundError: function(/* oXhr */) {
			MessageBox.error(this.getResourceBundle().getText("txtErrorNotFoundText"));
		},
		
		onForbiddenError: function(/* oXhr */) {
			MessageBox.error(this.getResourceBundle().getText("txtErrorForbiddenText"));
		},
		
		onTechnicalError: function(/* oXhr */) {
			MessageBox.error(this.getResourceBundle().getText("txtErrorTechnicalText"));
		},
		
		onInternalServerError: function(oXhr) {
			var oBundle = this.getResourceBundle(),
			
				fnKeyExists = function(sKey) {
					return oBundle.hasText(sKey) || oBundle.getText(sKey) !== sKey;
				},
			
				fnTranformToHyphens = function(sString) {
					return (sString || "").replace(/_/g, "-").toLowerCase();
				},
				
				fnTransformToCamelCase = function(sString) {
					return jQuery.sap.camelCase((sString || "").replace(/-+/g, "-").toLowerCase());
				},
				
				fnGetControlErrorText = function(sError, sEntity) {
					var sKey = fnTransformToCamelCase("txt-error-control-" + jQuery.sap.hyphen(sEntity) + "-" 
						+ fnTranformToHyphens(sError) + "-text");
					if (fnKeyExists(sKey)) {
						return oBundle.getText(sKey);
					}
					sKey = fnTransformToCamelCase("txt-error-control-" + fnTranformToHyphens(sError) + "-text");
					if (fnKeyExists(sKey)) {
						return oBundle.getText(sKey);
					}
					return oBundle.getText("txtErrorControlText");
				},
				
				fnGetDatabaseErrorText = function(sError) {
					var sKey = fnTransformToCamelCase("txt-error-database-" + fnTranformToHyphens(sError) + "-text");
					if (fnKeyExists(sKey)) {
						return oBundle.getText(sKey);
					}
					return oBundle.getText("txtErrorDatabaseText");
				},
				
				fnGetErrorObject = function() {
					try {
						return JSON.parse(typeof oXhr === "string" ? oXhr : oXhr.responseText) || {};
					}
					catch(e) {
						return {};
					}
				},
				
				oError = fnGetErrorObject();
				
			switch(oError.type) {
			case "CONTROL":
				if (oError.error && oError.entity) {
					if (oError.cause) {
						MessageBox.error(fnGetControlErrorText(oError.error, oError.entity), {
							details: fnGetDatabaseErrorText(oError.cause.error) + 
								(oError.cause.details ? "\n" + oError.cause.details : undefined)
						});
					}
					else {
						MessageBox.error(fnGetControlErrorText(oError.error, oError.entity));
					}
				}
				else if (oError.cause) {
					MessageBox.error(fnGetDatabaseErrorText(oError.cause.error), {
						details: oError.cause.details || undefined
					});
				}
				else {
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

});