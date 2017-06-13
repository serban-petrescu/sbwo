import helps from "./helps";

export default function (oBundle) {
    return {
        "person-create": {
            name: "person-create",
            title: oBundle.getText("sttHomePersonCreateTitle"),
            icon: "sap-icon://add-contact",
            type: "Create",
            order: 1,
            visible: true,
            navinfo: ["person-create", {}]
        },
        "import": {
            name: "import",
            title: oBundle.getText("sttHomeImportTitle"),
            icon: "sap-icon://add-contact",
            type: "None",
            order: 14,
            visible: /localhost:\d+/.test(window.location.host),
            navinfo: ["import", {}]
        },
        "customizing": {
            name: "customizing",
            title: oBundle.getText("sttHomeCustomizingTitle"),
            icon: "sap-icon://customize",
            type: "None",
            order: 15,
            visible: true,
            navinfo: ["customizing", {}]
        },
        "person-list": {
            name: "person-list",
            title: oBundle.getText("sttHomePersonListTitle"),
            icon: "sap-icon://customer-briefing",
            type: "None",
            order: 2,
            visible: true,
            count: "person",
            unit: oBundle.getText("sttHomePersonNumberUnit"),
            navinfo: ["person-list", {}]
        },
        "deleted-list": {
            name: "deleted-list",
            title: oBundle.getText("sttHomeTrashCanTitle"),
            icon: "sap-icon://delete",
            type: "None",
            order: 4,
            visible: true,
            count: "deleted",
            unit: oBundle.getText("sttHomeDeletedNumberUnit"),
            navinfo: ["deleted-list", {}]
        },
        "server-settings": {
            name: "server-settings",
            title: oBundle.getText("sttHomeServerSettingsTitle"),
            icon: "sap-icon://settings",
            type: "None",
            order: 16,
            visible: /localhost:\d+/.test(window.location.host),
            navinfo: ["server-settings", {}]
        },
        "help": {
            name: "help",
            title: oBundle.getText("sttHomeHelpTitle"),
            icon: "sap-icon://sys-help",
            type: "None",
            order: 100,
            visible: true,
            number: helps.count,
            unit: oBundle.getText("sttHomeHelpTopicsUnit"),
            navinfo: ["help", {}]
        },
        "expertise-create": {
            name: "expertise-create",
            title: oBundle.getText("sttHomeExpertiseCreateTitle"),
            icon: "sap-icon://official-service",
            type: "Create",
            order: 3,
            visible: true,
            navinfo: ["expertise-create", {}]
        },
        "expertise-list": {
            name: "expertise-list",
            title: oBundle.getText("sttHomeExpertiseListTitle"),
            icon: "sap-icon://compare",
            type: "None",
            order: 4,
            visible: true,
            count: "expertise",
            unit: oBundle.getText("sttHomeExpertiseNumberUnit"),
            navinfo: ["expertise-list", {}]
        }
    };
};
