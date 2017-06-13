sap.ui.define(["sap/ui/model/json/JSONModel", "sap/ui/Device", "./user", "jquery.sap.global"], function (_JSONModel, _Device, _user, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _JSONModel2 = _interopRequireDefault(_JSONModel);

    var _Device2 = _interopRequireDefault(_Device);

    var _user2 = _interopRequireDefault(_user);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    exports.default = {
        createDeviceModel: function createDeviceModel() {
            var oModel = new _JSONModel2.default(_Device2.default);
            oModel.setDefaultBindingMode("OneWay");
            return oModel;
        },
        createContextModel: function createContextModel() {
            var oModel = new _JSONModel2.default(_jquerySap2.default.sap.getModulePath("spet.sbwo.web.private.model") + "/context.json");
            oModel.setDefaultBindingMode("OneWay");
            _Device2.default.resize.attachHandler(function () {
                oModel.setProperty("/resize/height", _Device2.default.resize.height);
                oModel.setProperty("/resize/width", _Device2.default.resize.width);
            });
            return oModel;
        },
        createUserModel: function createUserModel() {
            var oModel = new _JSONModel2.default(_user2.default);
            oModel.setDefaultBindingMode("OneWay");
            return oModel;
        }
    };
    return exports.default;
});
//# sourceMappingURL=models.js.map
