sap.ui.define(["spet/sbwo/web/private/controller/entity/Base", "sap/m/MessageBox"], function (_Base, _MessageBox) {
    "use strict";

    var exports = {};
    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Base2 = _interopRequireDefault(_Base);

    var _MessageBox2 = _interopRequireDefault(_MessageBox);

    function _interopRequireDefault(obj) {
        return obj && obj.__esModule ? obj : {
            default: obj
        };
    }

    var DEFAULT_LOG_COLOR = "Neutral";
    // import DateFormat from "sap/ui/core/format/DateFormat";

    var LEVEL_TO_LOG_COLOR = new Map([["ERROR", "Negative"], ["WARN", "Critical"]]);

    var DEFAULT_SCHEDULE_TYPE_I18N = "txtScheduleTypeUnknownText";
    var SCHEDULE_TYPE_TO_I18N = new Map([["BACKUP", "txtScheduleTypeBackupText"], ["CLEANUP", "txtScheduleTypeCleanupText"], ["SESSION_CACHE", "txtScheduleTypeSessionFlushText"], ["COURT_API_BATCH", "txtScheduleTypeCheckCourtText"], ["GEOCODING_BATCH", "txtScheduleTypeGeocodeText"]]);

    exports.default = _Base2.default.extend("spet.sbwo.web.private.controller.misc.ServerSettings", {
        onInit: function onInit() {
            var _this = this;

            _Base2.default.prototype.onInit.apply(this, arguments);
            [this.onRouteMatched, this.onReadLogs, this.onReadSchedules].forEach(function (fnListener) {
                _this.getRouter().getRoute("server-settings").attachPatternMatched(fnListener, _this);
            });
        },
        getBaseApiUrl: function getBaseApiUrl() {
            return "/private/api/rest/utility/file";
        },
        formatLogColorFromLevel: function formatLogColorFromLevel(sLevel) {
            return LEVEL_TO_LOG_COLOR.get(sLevel) || DEFAULT_LOG_COLOR;
        },
        formatScheduleType: function formatScheduleType(sType) {
            var sKey = SCHEDULE_TYPE_TO_I18N.get(sType) || DEFAULT_SCHEDULE_TYPE_I18N;
            return this.getResourceBundle().getText(sKey);
        },
        onDatabaseBackupLocationVh: function onDatabaseBackupLocationVh(oEvent) {
            var _this2 = this;

            var fnOnSelect = function fnOnSelect(oE) {
                var aSels = oE.getParameter("result");
                if (aSels.length) {
                    _this2.getModel("data").setProperty("/databaseBackupLocation", aSels[0]);
                    _this2.onValueChanged();
                }
            };
            this.byId("viewFileExporer").getController().multiselect(false).current(oEvent.getSource().getValue()).folder().attachEventOnce("select", fnOnSelect).open();
        },
        onSearchLogsOrEvents: function onSearchLogsOrEvents(oEvent, fnPressHandler) {
            var sQuery = oEvent.getParameter("query");
            this.applyContainsFilter(sQuery, "name", this.byId("lstLogs").getBinding("items"));
            if (oEvent.getParameter("refreshButtonPressed")) {
                fnPressHandler();
            }
        },
        onSearchLogs: function onSearchLogs(oEvent) {
            var _this3 = this;

            this.onSearchLogsOrEvents(oEvent, function () {
                return _this3.onReadLogs();
            });
        },
        onSearchLogEvents: function onSearchLogEvents(oEvent) {
            var _this4 = this;

            var fnHandler = function fnHandler() {
                return _this4.onReadLogEvents(_this4.getModel("view").getProperty("/log/name"));
            };
            this.onSearchLogsOrEvents(oEvent, fnHandler);
        },
        onShowLogEvents: function onShowLogEvents(oEvent) {
            var sName = oEvent.getSource().getBindingContext("view").getProperty("name");
            this.getModel("view").setProperty("/log", {
                name: sName,
                events: []
            });
            this.byId("navLogs").to(this.byId("sccLogEvents"));
            this.onReadLogEvents(sName);
        },
        onShowLogList: function onShowLogList() {
            this.byId("navLogs").back();
        },
        onReadLogs: function onReadLogs() {
            var _this5 = this;

            this.get(this.getBaseApiUrl() + "/logs", function (oData) {
                return _this5.getModel("view").setProperty("/logs", oData);
            });
        },
        onReadLogEvents: function onReadLogEvents(sName) {
            var _this6 = this;

            var fnHandler = function fnHandler(oData) {
                return _this6.getModel("view").setProperty("/log/events", oData);
            };
            this.get(this.getBaseApiUrl() + "/log/" + sName, fnHandler);
        },
        onReadSchedules: function onReadSchedules() {
            var _this7 = this;

            var fnHandler = function fnHandler(oData) {
                return _this7.getModel("view").setProperty("/schedules", oData);
            };
            this.get(this.getBaseApiUrl() + "/schedules/read", fnHandler);
        },
        onSaveSuccess: function onSaveSuccess() {
            _Base2.default.prototype.onSaveSuccess.apply(this, arguments);
            _MessageBox2.default.information(this.getResourceBundle().getText("txtServerSettingsUpdatedRefreshInfo"));
        }
    });
    return exports.default;
});
//# sourceMappingURL=ServerSettings.controller.js.map
