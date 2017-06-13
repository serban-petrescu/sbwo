import storage from "jquery.sap.storage";
import jQuery from "jquery.sap.global";

let oLocal,
    bStorage,
    oExport;

try {
    oLocal = storage("local", "draft");
    bStorage = oLocal.isSupported();
} catch (e) {
    bStorage = false;
}

if (bStorage) {
    oExport = {
        load(sKey) {
            var oDeferred = jQuery.Deferred(),
                oResult;
            try {
                oResult = JSON.parse(oLocal.get(sKey)) || null;
            } catch (e) {
                oResult = null;
            }
            oDeferred.resolve(oResult);
            return oDeferred.promise();
        },

        save(sKey, oData) {
            var oDeferred = jQuery.Deferred(),
                oResult;
            try {
                oResult = oLocal.put(sKey, JSON.stringify(oData));
            } catch (e) {
                oResult = false;
            }
            oDeferred.resolve(oResult);
            return oDeferred.promise();
        },

        remove(sKey) {
            var oDeferred = jQuery.Deferred(),
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
        load() {
            var oDeferred = jQuery.Deferred();
            oDeferred.resolve(null);
            return oDeferred.promise();
        },

        save() {
            var oDeferred = jQuery.Deferred();
            oDeferred.resolve(true);
            return oDeferred.promise();
        },

        remove() {
            var oDeferred = jQuery.Deferred();
            oDeferred.resolve(true);
            return oDeferred.promise();
        }
    };
}

export default oExport;
