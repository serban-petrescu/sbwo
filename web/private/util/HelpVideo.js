sap.ui.define(["sap/ui/core/Control", "sap/ui/Device", "sap/ui/Global", "jquery.sap.global"], function (_Control, _Device, _Global, _jquerySap) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Control2 = _interopRequireDefault(_Control);

    var _Device2 = _interopRequireDefault(_Device);

    var _Global2 = _interopRequireDefault(_Global);

    var _jquerySap2 = _interopRequireDefault(_jquerySap);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var LANGUAGE = _Global2.default.getCore().getConfiguration().getLanguage();
    var DEVICE = _Device2.default.system.phone ? "mobile" : "desktop";
    var BASE_PATH = "spet/sbwo/web/private/model/video";

    var fnGetVideoPath = function fnGetVideoPath(sName) {
        return _jquerySap2.default.sap.getModulePath(BASE_PATH, "/" + sName + "-" + LANGUAGE + ".mp4");
    };
    var fnGetTrackPath = function fnGetTrackPath(sName) {
        return _jquerySap2.default.sap.getModulePath(BASE_PATH, "/" + sName + "-" + DEVICE + ".vtt");
    };

    exports.default = _Control2.default.extend("spet.sbwo.web.private.util.HelpVideo", {
        metadata: {
            properties: {
                width: {
                    type: "sap.ui.core.CSSSize",
                    defaultValue: "100%"
                },
                height: {
                    type: "sap.ui.core.CSSSize",
                    defaultValue: "100%"
                },
                name: {
                    type: "string"
                },
                trackLabel: {
                    type: "string"
                }
            }
        },

        renderer: function renderer(oRm, oControl) {
            oRm.write("<video ");
            oRm.writeControlData(oControl);
            oRm.writeAttribute("width", oControl.getWidth());
            oRm.writeAttribute("height", oControl.getHeight());
            oRm.write(" controls>");

            if (oControl.getName()) {
                oRm.write("<source ");
                oRm.writeAttribute("src", fnGetVideoPath(oControl.getName()));
                oRm.writeAttribute("type", "video/mp4");
                oRm.write("/>");

                oRm.write("<track ");
                oRm.writeAttribute("src", fnGetTrackPath(oControl.getName()));
                oRm.writeAttribute("srclang", LANGUAGE);
                oRm.writeAttribute("kind", "subtitles");
                oRm.writeAttribute("label", oControl.getTrackLabel());
                oRm.write("default />");
            }

            oRm.write("</video>");
        }
    });
    return exports.default;
});
//# sourceMappingURL=HelpVideo.js.map
