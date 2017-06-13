import sapui from "sap/ui/Global";

let oComponent,
    oRootView,
    mDialogs = new Map();

export default {
    initialize(oC, oV) {
        oComponent = oC;
        oRootView = oV;
    },

    get(sName) {
        if (sName.indexOf("spet") !== 0) {
            sName = "spet.sbwo.web.private.view.vh." + sName;
        }

        if (mDialogs.has(sName)) {
            return mDialogs.get(sName);
        } else {
            return oComponent.runAsOwner(function () {
                var oView = sapui.xmlview(sName);
                mDialogs.put(sName, oView.getController());
                oRootView.addDependent(oView);
                return oView.getController();
            });
        }
    }
};
