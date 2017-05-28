package spet.sbwo.api.odata;

import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODataErrorHandlingCallback implements ODataErrorCallback {
    private static final Logger LOG = LoggerFactory.getLogger(ODataErrorHandlingCallback.class);

    @Override
    public ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException {
        LOG.error("Error in OData call.", context.getException());
        return EntityProvider.writeErrorDocument(context);
    }

}
