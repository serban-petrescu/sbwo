sap.ui.define([], function () {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });
    exports.default = {
        isCollectionEmpty: function isCollectionEmpty(oEvent, sCollection) {
            var oControl = oEvent.getSource(),
                oModel = oControl.getBindingContext("data").getModel(),
                sBasePath = oControl.getBindingContext("data").getPath() || "",
                sPath = sCollection ? sBasePath.slice(-1) === "/" ? sBasePath + sCollection : sBasePath + "/" + sCollection : sBasePath.substring(0, sBasePath.lastIndexOf("/")),
                aArray = oModel.getProperty(sPath) || [];
            return aArray.length === 0;
        },
        onAddCollection: function onAddCollection(oEvent, sCollection, oData) {
            var oControl = oEvent.getSource(),
                oModel = oControl.getBindingContext("data").getModel(),
                sBasePath = oControl.getBindingContext("data").getPath() || "",
                sPath = sBasePath.slice(-1) === "/" ? sBasePath + sCollection : sBasePath + "/" + sCollection,
                aArray = oModel.getProperty(sPath) || [];
            aArray.push(oData);
            oModel.setProperty(sPath, aArray);
            if (this.onValueChanged) {
                this.onValueChanged();
            }
        },
        onDeleteCollectionLastItem: function onDeleteCollectionLastItem(oEvent, sCollection) {
            var oControl = oEvent.getSource(),
                oModel = oControl.getBindingContext("data").getModel(),
                sBasePath = oControl.getBindingContext("data").getPath() || "",
                sPath = sBasePath.slice(-1) === "/" ? sBasePath + sCollection : sBasePath + "/" + sCollection,
                aArray = oModel.getProperty(sPath);
            if (aArray.length) {
                aArray.splice(aArray.length - 1, 1);
                oModel.setProperty(sPath, aArray);
                if (this.onValueChanged) {
                    this.onValueChanged();
                }
            }
        },
        onDeleteCollection: function onDeleteCollection(oEvent) {
            var oItem = oEvent.getParameter("listItem"),
                oModel = oItem.getBindingContext("data").getModel(),
                sBasePath = oItem.getBindingContext("data").getPath() || "",
                sPath = sBasePath.substring(0, sBasePath.lastIndexOf("/")),
                iIndex = parseInt(sBasePath.substring(sBasePath.lastIndexOf("/") + 1), 10),
                aArray = oModel.getProperty(sPath);
            aArray.splice(iIndex, 1);
            oModel.setProperty(sPath, aArray);
            if (this.onValueChanged) {
                this.onValueChanged();
            }
        }
    };
    return exports.default;
});
//# sourceMappingURL=BaseEdit.js.map
