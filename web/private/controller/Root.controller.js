sap.ui.define(["./Base", "sap/ui/core/routing/HashChanger", "spet/sbwo/web/private/model/helps", "spet/sbwo/web/private/util/vhManager", "jquery.sap.global"], function (_Base, _HashChanger, _helps, _vhManager, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _HashChanger2 = _interopRequireDefault(_HashChanger);

    var _helps2 = _interopRequireDefault(_helps);

    var _vhManager2 = _interopRequireDefault(_vhManager);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.Root", {
        onInit: function onInit() {
            var sClass = this.getOwnerComponent().getContentDensityClass(),
                oOverlay = (0, _jquerySap2.default)("#overlay");

            _vhManager2.default.initialize(this.getOwnerComponent(), this.getView());
            _jquerySap2.default.sap.delayedCall(1000, null, function () {
                return oOverlay.fadeOut(1000, oOverlay.remove.bind(oOverlay));
            });

            this.buildViewModel({
                menu: false,
                edit: false,
                favourites: [],
                isFavourite: false,
                hash: _HashChanger2.default.getInstance().getHash(),
                help: null
            });

            this.getView().addStyleClass(sClass);
            this.byId("shlMain").getDependents().forEach(function (oControl) {
                return oControl.addStyleClass(sClass);
            });
            this.attachHashAndRoutes();
        },
        attachHashAndRoutes: function attachHashAndRoutes() {
            var _this = this;

            var fnHelp = function fnHelp(sRoute) {
                return _this.getModel("view").setProperty("/help", _helps2.default.route(_this.getResourceBundle(), sRoute));
            };

            var fnBuildHandler = function fnBuildHandler(sParameter) {
                return function (oEvent) {
                    _this.getModel("view").setProperty("/hash", oEvent.getParameter(sParameter));
                    _this.updateFavoriteModel();
                };
            };

            _HashChanger2.default.getInstance().attachEvent("hashChanged", fnBuildHandler("newHash")).attachEvent("hashReplaced", fnBuildHandler("sHash")).attachEvent("hashSet", fnBuildHandler("sHash"));

            this.getRouter().attachRouteMatched(function (oEvent) {
                return fnHelp(oEvent.getParameter("name"));
            }).attachBypassed(function () {
                return fnHelp("home");
            });
        },
        onCloseHelpDialog: function onCloseHelpDialog() {
            this.byId("dlgHelpVideo").close();
        },
        onOpenHelpDialog: function onOpenHelpDialog() {
            this.byId("dlgHelpVideo").open();
        },
        onLogoff: function onLogoff() {
            window.location.assign("/public/rest/user/logout");
        },
        onShowFavourites: function onShowFavourites() {
            var oModel = this.getModel("view"),
                bOpen = void 0;

            if (this.getModel("device").getProperty("/system/phone")) {
                oModel.setProperty("/menu", true);
                this.byId("dlgFavourites").open();
                bOpen = true;
            } else {
                bOpen = !oModel.getProperty("/menu");
                oModel.setProperty("/menu", bOpen);
            }

            if (bOpen) {
                this.get("/private/api/rest/user/favourites/read", this.updateFavoriteModel);
            }
        },
        onToggleEdit: function onToggleEdit(oEvent) {
            if (!oEvent.getSource().getPressed()) {
                var oData = this.getModel("view").getProperty("/favourites");
                this.put("/private/api/rest/user/favourites/update", oData, this.updateFavoriteModel);
            }
        },
        updateFavoriteModel: function updateFavoriteModel(aFavData) {
            var oModel = this.getModel("view"),
                sHash = oModel.getProperty("/hash"),
                aFavs = aFavData === undefined ? oModel.getProperty("/favourites") : aFavData;

            aFavs.sort(function (a, b) {
                return a.title.toUpperCase().localeCompare(b.title.toUpperCase());
            });

            oModel.setProperty("/favourites", aFavs);
            oModel.setProperty("/isFavourite", !!aFavs.find(function (oFav) {
                return oFav.hash === sHash;
            }));
        },
        onToggleFavourite: function onToggleFavourite(oEvent) {
            var _this2 = this;

            var oModel = this.getModel("view"),
                aFavs = oModel.getProperty("/favourites"),
                sHash = oModel.getProperty("/hash");

            var fnCreate = function fnCreate() {
                var oData = {
                    hash: sHash,
                    title: window.document.title
                };
                _this2.post("/private/api/rest/user/favourites/create", oData, function (oDt) {
                    aFavs.push(oDt);
                    _this2.updateFavoriteModel(aFavs);
                });
            };

            var fnDelete = function fnDelete() {
                var iIndex = aFavs.findIndex(function (oFav) {
                    return oFav.hash === sHash;
                });
                if (iIndex !== undefined) {
                    _this2.del("/private/api/rest/user/favourites/delete/" + aFavs[iIndex].id, function () {
                        aFavs.splice(iIndex, 1);
                        _this2.updateFavoriteModel(aFavs);
                    });
                }
            };

            oEvent.getSource().getPressed() ? fnCreate() : fnDelete();
        },
        onDeleteFavouriteEdit: function onDeleteFavouriteEdit(oEvent) {
            var oItem = oEvent.getParameter("listItem"),
                sPath = oItem.getBindingContext("view").getPath(),
                iIndex = parseInt(sPath.substring(sPath.lastIndexOf("/") + 1), 10),
                aFavs = this.getModel("view").getProperty("/favourites");
            aFavs.splice(iIndex, 1);
            this.updateFavoriteModel(aFavs);
        },
        onCloseFavourites: function onCloseFavourites() {
            this.getModel("view").setProperty("/menu", false);
            this.byId("dlgFavourites").close();
        },
        onNavigateToFavourite: function onNavigateToFavourite(oEvent) {
            var sHash = oEvent.getSource().getBindingContext("view").getProperty("hash");
            _HashChanger2.default.getInstance().setHash(sHash);
        },
        onPressHome: function onPressHome() {
            this.getRouter().navTo("home", {});
        },
        onPressPersonalization: function onPressPersonalization() {
            this.getRouter().navTo("user-settings", {});
        },
        onOpenGlobalSearch: function onOpenGlobalSearch() {
            this.byId("sedGlobalSearch").open("");
        },
        onSearchField: function onSearchField(oEvent) {
            var oPopover = this.byId("popGlobalSearch"),
                oSearch = oEvent.getSource(),
                oBinding = this.byId("lstSearchPopover").getBinding("items");
            this.applySearchFilter(oEvent.getParameter("query"), "Search", oBinding);
            oPopover.setContentWidth(oSearch.$().width() + "px");
            oBinding.attachEventOnce("dataReceived", function () {
                return oPopover.openBy(oSearch);
            });
        },
        onSearchDialog: function onSearchDialog(oEvent) {
            this.applySearchFilter(oEvent.getParameter("value"), "Search", oEvent.getParameter("itemsBinding"));
        },
        onSelectSearchDialog: function onSelectSearchDialog(oEvent) {
            var oContext = oEvent.getParameter("selectedItem").getBindingContext();
            this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
        },
        onSelectSearchPopover: function onSelectSearchPopover(oEvent) {
            var oContext = oEvent.getSource().getBindingContext();
            this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
            this.byId("popGlobalSearch").close();
        }
    });
    return exports.default;
});
//# sourceMappingURL=Root.controller.js.map
