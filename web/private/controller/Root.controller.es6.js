import Base from "./Base";
import HashChanger from "sap/ui/core/routing/HashChanger";
import helps from "spet/sbwo/web/private/model/helps";
import vhManager from "spet/sbwo/web/private/util/vhManager";
import jQuery from "jquery.sap.global";

export default Base.extend("spet.sbwo.web.private.controller.Root", {
    onInit() {
        let sClass = this.getOwnerComponent().getContentDensityClass(),
            oOverlay = jQuery("#overlay");

        vhManager.initialize(this.getOwnerComponent(), this.getView());
        jQuery.sap.delayedCall(1000, null, () => oOverlay.fadeOut(1000, oOverlay.remove.bind(oOverlay)));

        this.buildViewModel({
            menu: false,
            edit: false,
            favourites: [],
            isFavourite: false,
            hash: HashChanger.getInstance().getHash(),
            help: null
        });

        this.getView().addStyleClass(sClass);
        this.byId("shlMain").getDependents().forEach(oControl => oControl.addStyleClass(sClass));
        this.attachHashAndRoutes();
    },

    attachHashAndRoutes() {
        const fnHelp = sRoute => this.getModel("view").setProperty("/help", helps.route(this.getResourceBundle(), sRoute));

        const fnBuildHandler = sParameter => {
            return oEvent => {
                this.getModel("view").setProperty("/hash", oEvent.getParameter(sParameter));
                this.updateFavoriteModel();
            };
        };

        HashChanger.getInstance()
            .attachEvent("hashChanged", fnBuildHandler("newHash"))
            .attachEvent("hashReplaced", fnBuildHandler("sHash"))
            .attachEvent("hashSet", fnBuildHandler("sHash"));

        this.getRouter()
            .attachRouteMatched(oEvent => fnHelp(oEvent.getParameter("name")))
            .attachBypassed(() => fnHelp("home"));
    },

    onCloseHelpDialog() {
        this.byId("dlgHelpVideo").close();
    },

    onOpenHelpDialog() {
        this.byId("dlgHelpVideo").open();
    },

    onLogoff() {
        window.location.assign("/public/rest/user/logout");
    },

    onShowFavourites() {
        let oModel = this.getModel("view"),
            bOpen;

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

    onToggleEdit(oEvent) {
        if (!oEvent.getSource().getPressed()) {
            let oData = this.getModel("view").getProperty("/favourites");
            this.put("/private/api/rest/user/favourites/update", oData, this.updateFavoriteModel);
        }
    },

    updateFavoriteModel(aFavData) {
        let oModel = this.getModel("view"),
            sHash = oModel.getProperty("/hash"),
            aFavs = aFavData === undefined ? oModel.getProperty("/favourites") : aFavData;

        aFavs.sort((a, b) => a.title.toUpperCase().localeCompare(b.title.toUpperCase()));

        oModel.setProperty("/favourites", aFavs);
        oModel.setProperty("/isFavourite", !!aFavs.find(oFav => oFav.hash === sHash));
    },

    onToggleFavourite(oEvent) {
        var oModel = this.getModel("view"),
            aFavs = oModel.getProperty("/favourites"),
            sHash = oModel.getProperty("/hash");

        const fnCreate = () => {
            let oData = {
                hash: sHash,
                title: window.document.title
            };
            this.post("/private/api/rest/user/favourites/create", oData, (oDt) => {
                aFavs.push(oDt);
                this.updateFavoriteModel(aFavs);
            });
        };

        const fnDelete = () => {
            let iIndex = aFavs.findIndex(oFav => oFav.hash === sHash);
            if (iIndex !== undefined) {
                this.del("/private/api/rest/user/favourites/delete/" + aFavs[iIndex].id, () => {
                    aFavs.splice(iIndex, 1);
                    this.updateFavoriteModel(aFavs);
                });
            }
        };

        oEvent.getSource().getPressed() ? fnCreate() : fnDelete();
    },

    onDeleteFavouriteEdit(oEvent) {
        let oItem = oEvent.getParameter("listItem"),
            sPath = oItem.getBindingContext("view").getPath(),
            iIndex = parseInt(sPath.substring(sPath.lastIndexOf("/") + 1), 10),
            aFavs = this.getModel("view").getProperty("/favourites");
        aFavs.splice(iIndex, 1);
        this.updateFavoriteModel(aFavs);
    },

    onCloseFavourites() {
        this.getModel("view").setProperty("/menu", false);
        this.byId("dlgFavourites").close();
    },

    onNavigateToFavourite(oEvent) {
        let sHash = oEvent.getSource().getBindingContext("view").getProperty("hash");
        HashChanger.getInstance().setHash(sHash);
    },

    onPressHome() {
        this.getRouter().navTo("home", {});
    },

    onPressPersonalization() {
        this.getRouter().navTo("user-settings", {});
    },

    onOpenGlobalSearch() {
        this.byId("sedGlobalSearch").open("");
    },

    onSearchField(oEvent) {
        let oPopover = this.byId("popGlobalSearch"),
            oSearch = oEvent.getSource(),
            oBinding = this.byId("lstSearchPopover").getBinding("items");
        this.applySearchFilter(oEvent.getParameter("query"), "Search", oBinding);
        oPopover.setContentWidth(oSearch.$().width() + "px");
        oBinding.attachEventOnce("dataReceived", () => oPopover.openBy(oSearch));
    },

    onSearchDialog(oEvent) {
        this.applySearchFilter(oEvent.getParameter("value"), "Search", oEvent.getParameter("itemsBinding"));
    },

    onSelectSearchDialog(oEvent) {
        let oContext = oEvent.getParameter("selectedItem").getBindingContext();
        this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
    },

    onSelectSearchPopover(oEvent) {
        let oContext = oEvent.getSource().getBindingContext();
        this.navigateToEntity(oContext.getProperty("Type"), oContext.getProperty("Id"));
        this.byId("popGlobalSearch").close();
    }
});
