sap.ui.define([
	"sap/ui/core/Control",
	"sap/ui/Device"
], function(Control, Device) {
	
	var sLanguage = sap.ui.getCore().getConfiguration().getLanguage();
	
	var fnGetVideoPath = function(sName) {
		var sDevice = Device.system.phone ? "mobile" : "desktop";
		return jQuery.sap.getModulePath("spet/sbwo/web/model/video", "/" + sName + "-" + sDevice + ".mp4");
	};
	
	var fnGetTrackPath = function(sName) {
		return jQuery.sap.getModulePath("spet/sbwo/web/model/video", "/" + sName + "-" + sLanguage + ".vtt");
	};
	
	return Control.extend("spet.sbwo.web.util.HelpVideo", {
		metadata: {
			properties: {
				width: {type: "sap.ui.core.CSSSize", defaultValue: "100%"},
				height: {type: "sap.ui.core.CSSSize", defaultValue: "100%"},
				name: {type: "string"},
				trackLabel: {type: "string"}
			}
		},
		
		renderer: function(oRm, oControl) {
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
				oRm.writeAttribute("srclang", sLanguage);
				oRm.writeAttribute("kind", "subtitles");
				oRm.writeAttribute("label", oControl.getTrackLabel());
				oRm.write("default />");
			}
			
			oRm.write("</video>");
		}
	});
	
});