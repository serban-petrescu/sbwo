import JSONModel from "sap/ui/model/json/JSONModel";
import Device from "sap/ui/Device";
import user from "./user";
import jQuery from "jquery.sap.global";

export default {

    createDeviceModel() {
        var oModel = new JSONModel(Device);
        oModel.setDefaultBindingMode("OneWay");
        return oModel;
    },

    createContextModel() {
        var oModel = new JSONModel(jQuery.sap.getModulePath("spet.sbwo.web.private.model") + "/context.json");
        oModel.setDefaultBindingMode("OneWay");
        Device.resize.attachHandler(() => {
            oModel.setProperty("/resize/height", Device.resize.height);
            oModel.setProperty("/resize/width", Device.resize.width);
        });
        return oModel;
    },

    createUserModel() {
        var oModel = new JSONModel(user);
        oModel.setDefaultBindingMode("OneWay");
        return oModel;
    }

};
