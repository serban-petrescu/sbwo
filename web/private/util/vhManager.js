sap.ui.define(["sap/ui/Global"], function (_Global) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Global2 = _interopRequireDefault(_Global);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var oComponent = void 0,
        oRootView = void 0,
        mDialogs = new Map();

    exports.default = {
        initialize: function initialize(oC, oV) {
            oComponent = oC;
            oRootView = oV;
        },
        get: function get(sName) {
            if (sName.indexOf("spet") !== 0) {
                sName = "spet.sbwo.web.private.view.vh." + sName;
            }

            if (mDialogs.has(sName)) {
                return mDialogs.get(sName);
            } else {
                return oComponent.runAsOwner(function () {
                    var oView = _Global2.default.xmlview(sName);
                    mDialogs.put(sName, oView.getController());
                    oRootView.addDependent(oView);
                    return oView.getController();
                });
            }
        }
    };
    return exports.default;
});
//# sourceMappingURL=vhManager.js.map
