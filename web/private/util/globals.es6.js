import Period from "spet/sbwo/web/private/util/Period";
import Duration from "spet/sbwo/web/private/util/Duration";
import formatter from "spet/sbwo/web/private/model/formatter";
import jQuery from "jquery.sap.global";

export default function() {
    jQuery.sap.setObject("spet.sbwo.web.private.util.Period", Period);
    jQuery.sap.setObject("spet.sbwo.web.private.util.Duration", Duration);
    jQuery.sap.setObject("spet.sbwo.web.private.model.formatter", formatter);
}
