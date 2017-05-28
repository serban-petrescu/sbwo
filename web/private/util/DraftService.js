sap.ui.define(["jquery.sap.storage"], function() {
    var oLocal,
        bStorage;

    try {
        oLocal = jQuery.sap.storage("local", "draft");
        bStorage = oLocal.isSupported();
    } catch(e) {
        bStorage = false;
    }

    if (bStorage) {
        return {
            load: function(sKey) {
                var oDeferred = jQuery.Deferred(),
                    oResult;
                try {
                    oResult = JSON.parse(oLocal.get(sKey)) || null;
                }
                catch (e) {
                    oResult = null;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            },

            save: function(sKey, oData) {
                var oDeferred = jQuery.Deferred(),
                    oResult;
                try {
                    oResult = oLocal.put(sKey, JSON.stringify(oData));
                }
                catch (e) {
                    oResult = false;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            },

            remove: function(sKey) {
                var oDeferred = jQuery.Deferred(),
                    oResult;
                try {
                    oResult = oLocal.remove(sKey);
                }
                catch (e) {
                    oResult = false;
                }
                oDeferred.resolve(oResult);
                return oDeferred.promise();
            }
        };
    }
    else {
        return  {
            load: function() {
                var oDeferred = jQuery.Deferred();
                oDeferred.resolve(null);
                return oDeferred.promise();
            },

            save: function() {
                var oDeferred = jQuery.Deferred();
                oDeferred.resolve(true);
                return oDeferred.promise();
            },

            remove: function() {
                var oDeferred = jQuery.Deferred();
                oDeferred.resolve(true);
                return oDeferred.promise();
            }
        };
    }
});
