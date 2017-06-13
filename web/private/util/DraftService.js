sap.ui.define(["jquery.sap.storage", "jquery.sap.global"], function (_jquerySap, _jquerySap3) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    var _jquerySap4 = _interopRequireDefault(_jquerySap3);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var oLocal = void 0,
        bStorage = void 0,
        oExport = void 0;

    try {
        oLocal = (0, _jquerySap2.default)("local", "draft");
        bStorage = oLocal.isSupported();
    } catch (e) {
        bStorage = false;
    }

    if (bStorage) {
        oExport = {
            load: function load(sKey) {
                var oDeferred = _jquerySap4.default.Deferred(),
                    oResult;
                try {
                    oResult = JSON.parse(oLocal.get(sKey)) || null;
                } catch (e) {
                    oResult = null;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            },
            save: function save(sKey, oData) {
                var oDeferred = _jquerySap4.default.Deferred(),
                    oResult;
                try {
                    oResult = oLocal.put(sKey, JSON.stringify(oData));
                } catch (e) {
                    oResult = false;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            },
            remove: function remove(sKey) {
                var oDeferred = _jquerySap4.default.Deferred(),
                    oResult;
                try {
                    oResult = oLocal.remove(sKey);
                } catch (e) {
                    oResult = false;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            }
        };
    } else {
        oExport = {
            load: function load() {
                var oDeferred = _jquerySap4.default.Deferred();
                oDeferred.resolve(null);
                return oDeferred.promise();
            },
            save: function save() {
                var oDeferred = _jquerySap4.default.Deferred();
                oDeferred.resolve(true);
                return oDeferred.promise();
            },
            remove: function remove() {
                var oDeferred = _jquerySap4.default.Deferred();
                oDeferred.resolve(true);
                return oDeferred.promise();
            }
        };
    }

    exports.default = oExport;
    return exports.default;
});
//# sourceMappingURL=DraftService.js.map
