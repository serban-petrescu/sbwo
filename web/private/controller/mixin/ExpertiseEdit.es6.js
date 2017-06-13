import BaseEdit from "./BaseEdit";
import Filter from "sap/ui/model/Filter";
import vhManager from "spet/sbwo/web/private/util/vhManager";
import jQuery from "jquery.sap.global";

export default jQuery.extend({}, BaseEdit, {
    onAddExpertiseFine(oEvent) {
        this.onAddCollection(oEvent, "fines", {
            date: new Date().getTime(),
            sum: 0
        });
    },

    onDeleteExpertiseLastFine(oEvent) {
        this.onDeleteCollectionLastItem(oEvent, "fines");
    },

    getCaseNumberInput() {
        // must be overriden by target controllers
    },

    getNextHearing(oData) {
        let sMax = "";
        if (oData && oData.hearings) {
            for (let oHearing of oData.hearings) {
                sMax = sMax > oHearing.date ? sMax : oHearing.date;
            }
        }
        return sMax === "" ? null : sMax;
    },

    onLoadCase() {
        let sNumber = this.getModel("data").getProperty("/number");
        this.getModel("view").setProperty("/case", null);
        if (sNumber) {
            this.get("/private/api/rest/expertise/case?number=" + encodeURIComponent(sNumber),
                oData => oData ? this.onCaseLoaded(oData) : this.onCaseNotFound()
            );
        }
    },

    onCaseLoaded(oData) {
        this.getModel("view").setProperty("/case", oData);
        this.getModel("data").setProperty("/nextHearing", this.getNextHearing(oData));
        if (oData.matter) {
            this.getModel("data").setProperty("/title", oData.matter.substring(0, 100));
        }
    },

    onCaseNotFound() {
        var oInput = this.getCaseNumberInput();
        if (oInput) {
            oInput.setValueState("Warning");
            oInput.setValueStateText(this.getResourceBundle().getText("txtExpertiseUnableToFindCaseText"));
        }
    },

    onCourtLoaded(oData) {
        var oModel = this.getModel("data");
        oModel.setProperty("/court/code", oData.Code);
        oModel.setProperty("/court/id", oData.Id);
        oModel.setProperty("/court/name", oData.Name);
    },

    onCourtClear() {
        this.getModel("data").setProperty("/court", {
            id: null,
            name: "",
            code: ""
        });
    },

    onCaseNumberChanged(oEvent) {
        var oModel = this.getModel("data"),
            sOldCode = oModel.getProperty("/court/code"),
            sNewCode;

        const fnUpdateCourt = oData => {
            if (oData && oData.results && oData.results.length) {
                this.onCourtLoaded(oData.results[0]);
            } else {
                this.onCourtClear();
            }
        };

        this.onValueChanged(oEvent);
        sNewCode = oModel.getProperty("/court/code");

        if (sOldCode !== sNewCode) {
            if (sNewCode) {
                this.getModel().read("/Courts", {
                    filters: [new Filter("Code", "EQ", sNewCode)],
                    success: fnUpdateCourt
                });
            } else {
                this.onCourtClear();
            }
        }

        this.onLoadCase();
    },

    onOpenCourtValueHelp() {
        vhManager.get("Court").open(this.onCourtLoaded, this);
    }
});
