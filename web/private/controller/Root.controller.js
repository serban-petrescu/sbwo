sap.ui.define([
    "./Base",
    "sap/ui/core/routing/HashChanger",
    "spet/sbwo/web/model/helps",
    "spet/sbwo/web/util/vhManager"
], function(Base, HashChanger, helps, vhManager) {
    "use strict";

    return Base.extend("spet.sbwo.web.controller.Root", {
        onInit: function() {
            var sClass = this.getOwnerComponent().getContentDensityClass(),
                oOverlay = jQuery("#overlay");

            vhManager.initialize(this.getOwnerComponent(), this.getView());

            jQuery.sap.delayedCall(1000, null, function(){
                oOverlay.fadeOut(1000, oOverlay.remove.bind(oOverlay));
            });

            this.buildViewModel({
                menu: false,
                edit: false,
                favourites: [],
                isFavourite: false,
                hash: HashChanger.getInstance().getHash(),
                help: null
            });

            this.getView().addStyleClass(sClass);
            this.byId("shlMain").getDependents().forEach(function(oControl){
                oControl.addStyleClass(sClass);
            });

            this.attachHashAndRoutes();
        },

        attachHashAndRoutes: function() {
            var oModel = this.getModel("view"),
                fnUpdateModel = this.updateFavoriteModel.bind(this),
                oHash = HashChanger.getInstance(),
                oRouter = this.getRouter(),
                fnReadHelp = function(sRoute) {
                    this.getModel("view").setProperty("/help", helps.route(this.getResourceBundle(), sRoute));
                }.bind(this);
            oHash.attachEvent("hashChanged", function(oEvent) {
                oModel.setProperty("/hash", oEvent.getParameter("newHash"));
                fnUpdateModel();
            });
            oHash.attachEvent("hashReplaced", function(oEvent) {
                oModel.setProperty("/hash", oEvent.getParameter("sHash"));
                fnUpdateModel();
            });
            oHash.attachEvent("hashSet", function(oEvent) {
                oModel.setProperty("/hash", oEvent.getParameter("sHash"));
                fnUpdateModel();
            });

            oRouter.attachRouteMatched(function(oEvent){
                fnReadHelp(oEvent.getParameter("name"));
            });
            oRouter.attachBypassed(function(){
                fnReadHelp("home");
            });
        },

        onCloseHelpDialog: function() {
            this.byId("dlgHelpVideo").close();
        },

        onOpenHelpDialog: function() {
            this.byId("dlgHelpVideo").open();
        },

        onLogoff: function() {
            window.location.assign("/public/rest/user/logout");
        },

        onShowFavourites: function() {
            var oModel = this.getModel("view"),
                bOpen;

            if (this.getModel("device").getProperty("/system/phone")) {
                oModel.setProperty("/menu", true);
                this.byId("dlgFavourites").open();
                bOpen = true;
            }
            else {
                bOpen = !oModel.getProperty("/menu");
                oModel.setProperty("/menu", bOpen);
            }

            if (bOpen) {
                this.get("/private/api/rest/user/favourites/read", this.updateFavoriteModel);
            }
        },

        onToggleEdit: function(oEvent) {
            if (!oEvent.getSource().getPressed()) {
                var oData = this.getModel("view").getProperty("/favourites");
                this.put("/private/api/rest/user/favourites/update", oData, this.updateFavoriteModel);
            }
        },

        updateFavoriteModel: function(aFavData) {
            var oModel = this.getModel("view"),
                sHash = oModel.getProperty("/hash"),
                aFavs = aFavData === undefined ? oModel.getProperty("/favourites") : aFavData,
                i,
                bFound = false;
            for (i = 0; i < aFavs.length; ++i) {
                if (aFavs[i].hash === sHash) {
                    bFound = true;
                    break;
                }
            }
            aFavs.sort(function(a, b) {
                var sTitleA = a.title.toUpperCase();
                var sTitleB = b.title.toUpperCase();
                if (sTitleA < sTitleB) {
                    return -1;
                }
                if (sTitleA > sTitleB) {
                    return 1;
                }
                return 0;
            });
            oModel.setProperty("/favourites", aFavs);
            oModel.setProperty("/isFavourite", bFound);
        },

        onToggleFavourite: function(oEvent) {
            var oModel = this.getModel("view"),
                aFavs = oModel.getProperty("/favourites"),
                sHash = oModel.getProperty("/hash"),
                i,
                oData,
                fnSuccess;

            if (oEvent.getSource().getPressed()) {
                oData = {
                    hash: sHash,
                    title: window.document.title
                };
                fnSuccess = function(oDt) {
                    aFavs.push(oDt);
                    this.updateFavoriteModel(aFavs);
                };
                this.post("/private/api/rest/user/favourites/create", oData, fnSuccess);
            }
            else {
                for (i = 0; i < aFavs.length; ++i) {
                    if (aFavs[i].hash === sHash) {
                        break;
                    }
                }
                if (i < aFavs.length) {
                    fnSuccess = function() {
                        aFavs.splice(i, 1);
                        this.updateFavoriteModel(aFavs);
                    };
                    this.del("/private/api/rest/user/favourites/delete/" + aFavs[i].id, fnSuccess);
                }
            }
        },

        onDeleteFavouriteEdit: function(oEvent) {
            var oItem = oEvent.getParameter("listItem"),
                sPath = oItem.getBindingContext("view").getPath(),
                iIndex = parseInt(sPath.substring(sPath.lastIndexOf("/") + 1), 10),
                aFavs = this.getModel("view").getProperty("/favourites");
            aFavs.splice(iIndex, 1);
            this.updateFavoriteModel(aFavs);
        },

        onCloseFavourites: function() {
            this.getModel("view").setProperty("/menu", false);
            this.byId("dlgFavourites").close();
        },

        onNavigateToFavourite: function(oEvent) {
            var sHash = oEvent.getSource().getBindingContext("view").getProperty("hash");
            HashChanger.getInstance().setHash(sHash);
        },

        onPressHome: function() {
            this.getRouter().navTo("home", {});
        },

        onPressPersonalization: function() {
            this.getRouter().navTo("user-settings", {});
        },

        onOpenGlobalSearch: function() {
            this.byId("sedGlobalSearch").open("");
        },

        onSearchField: function(oEvent) {
            var oPopover = this.byId("popGlobalSearch"),
                oSearch = oEvent.getSource();
            this.applySearchFilter(oEvent.getParameter("query"), "Search", this.byId("lstSearchPopover").getBinding("items"));
            oPopover.setContentWidth(oSearch.$().width() + "px");
            jQuery.sap.delayedCall(500, null, function(){
                oPopover.openBy(oSearch);
            });
        },

        onSearchDialog: function(oEvent) {
            this.applySearchFilter(oEvent.getParameter("value"), "Search", oEvent.getParameter("itemsBinding"));
        },

        onSelectSearchDialog: function(oEvent) {
            var oContext = oEvent.getParameter("selectedItem").getBindingContext();
            this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
        },

        onSelectSearchPopover: function(oEvent) {
            var oContext = oEvent.getSource().getBindingContext();
            this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
            this.byId("popGlobalSearch").close();
        }
    });

});
