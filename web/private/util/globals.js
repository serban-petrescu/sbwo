sap.ui.define(["spet/sbwo/web/private/util/Period", "spet/sbwo/web/private/util/Duration", "spet/sbwo/web/private/model/formatter", "jquery.sap.global"], function (_Period, _Duration, _formatter, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    exports.default = function () {
        _jquerySap2.default.sap.setObject("spet.sbwo.web.private.util.Period", _Period2.default);
        _jquerySap2.default.sap.setObject("spet.sbwo.web.private.util.Duration", _Duration2.default);
        _jquerySap2.default.sap.setObject("spet.sbwo.web.private.model.formatter", _formatter2.default);
    };

    var _Period2 = _interopRequireDefault(_Period);

    var _Duration2 = _interopRequireDefault(_Duration);

    var _formatter2 = _interopRequireDefault(_formatter);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    return exports.default;
});
//# sourceMappingURL=globals.js.map
