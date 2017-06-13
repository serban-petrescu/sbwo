import Control from "sap/ui/core/Control";
import Device from "sap/ui/Device";
import sapui from "sap/ui/Global";
import jQuery from "jquery.sap.global";

const LANGUAGE = sapui.getCore().getConfiguration().getLanguage();
const DEVICE = Device.system.phone ? "mobile" : "desktop";
const BASE_PATH = "spet/sbwo/web/private/model/video";

const fnGetVideoPath = sName => jQuery.sap.getModulePath(BASE_PATH, "/" + sName + "-" + LANGUAGE + ".mp4");
const fnGetTrackPath = sName => jQuery.sap.getModulePath(BASE_PATH, "/" + sName + "-" + DEVICE + ".vtt");

export default Control.extend("spet.sbwo.web.private.util.HelpVideo", {
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

    renderer(oRm, oControl) {
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
