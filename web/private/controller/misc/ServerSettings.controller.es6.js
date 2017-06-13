import Base from "spet/sbwo/web/private/controller/entity/Base";
// import DateFormat from "sap/ui/core/format/DateFormat";
import MessageBox from "sap/m/MessageBox";

const DEFAULT_LOG_COLOR = "Neutral";
const LEVEL_TO_LOG_COLOR = new Map([
    ["ERROR", "Negative"],
    ["WARN", "Critical"]
]);

const DEFAULT_SCHEDULE_TYPE_I18N = "txtScheduleTypeUnknownText";
const SCHEDULE_TYPE_TO_I18N = new Map([
    ["BACKUP", "txtScheduleTypeBackupText"],
    ["CLEANUP", "txtScheduleTypeCleanupText"],
    ["SESSION_CACHE", "txtScheduleTypeSessionFlushText"],
    ["COURT_API_BATCH", "txtScheduleTypeCheckCourtText"],
    ["GEOCODING_BATCH", "txtScheduleTypeGeocodeText"]
]);

export default Base.extend("spet.sbwo.web.private.controller.misc.ServerSettings", {
    onInit() {
        Base.prototype.onInit.apply(this, arguments);
        [this.onRouteMatched, this.onReadLogs, this.onReadSchedules].forEach(fnListener => {
            this.getRouter().getRoute("server-settings").attachPatternMatched(fnListener, this);
        });
    },

    getBaseApiUrl() {
        return "/private/api/rest/utility/file";
    },

    formatLogColorFromLevel(sLevel) {
        return LEVEL_TO_LOG_COLOR.get(sLevel) || DEFAULT_LOG_COLOR;
    },

    formatScheduleType(sType) {
        let sKey = SCHEDULE_TYPE_TO_I18N.get(sType) || DEFAULT_SCHEDULE_TYPE_I18N;
        return this.getResourceBundle().getText(sKey);
    },

    onDatabaseBackupLocationVh(oEvent) {
        const fnOnSelect = (oE) => {
            var aSels = oE.getParameter("result");
            if (aSels.length) {
                this.getModel("data").setProperty("/databaseBackupLocation", aSels[0]);
                this.onValueChanged();
            }
        };
        this.byId("viewFileExporer").getController()
            .multiselect(false)
            .current(oEvent.getSource().getValue())
            .folder()
            .attachEventOnce("select", fnOnSelect)
            .open();
    },

    onSearchLogsOrEvents(oEvent, fnPressHandler) {
        const sQuery = oEvent.getParameter("query");
        this.applyContainsFilter(sQuery, "name", this.byId("lstLogs").getBinding("items"));
        if (oEvent.getParameter("refreshButtonPressed")) {
            fnPressHandler();
        }
    },

    onSearchLogs(oEvent) {
        this.onSearchLogsOrEvents(oEvent, () => this.onReadLogs());
    },

    onSearchLogEvents(oEvent) {
        const fnHandler = () => this.onReadLogEvents(this.getModel("view").getProperty("/log/name"));
        this.onSearchLogsOrEvents(oEvent, fnHandler);
    },

    onShowLogEvents(oEvent) {
        const sName = oEvent.getSource().getBindingContext("view").getProperty("name");
        this.getModel("view").setProperty("/log", {
            name: sName,
            events: []
        });
        this.byId("navLogs").to(this.byId("sccLogEvents"));
        this.onReadLogEvents(sName);
    },

    onShowLogList() {
        this.byId("navLogs").back();
    },

    onReadLogs() {
        this.get(this.getBaseApiUrl() + "/logs", oData => this.getModel("view").setProperty("/logs", oData));
    },

    onReadLogEvents(sName) {
        const fnHandler = oData => this.getModel("view").setProperty("/log/events", oData);
        this.get(this.getBaseApiUrl() + "/log/" + sName, fnHandler);
    },

    onReadSchedules() {
        const fnHandler = oData => this.getModel("view").setProperty("/schedules", oData);
        this.get(this.getBaseApiUrl() + "/schedules/read", fnHandler);
    },

    onSaveSuccess() {
        Base.prototype.onSaveSuccess.apply(this, arguments);
        MessageBox.information(this.getResourceBundle().getText("txtServerSettingsUpdatedRefreshInfo"));
    }
});
