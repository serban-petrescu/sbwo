package spet.sbwo.api.odata;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

public class ODataFactory extends ODataJPAServiceFactory {
    private static EntityManagerFactory emf;
    private static String puName;

    @Override
    public ODataService createODataSingleProcessorService(EdmProvider provider, ODataSingleProcessor processor) {
        return super.createODataSingleProcessorService(provider, new ODataReadDelegateProcessor(processor));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
        if (callbackInterface.isAssignableFrom(ODataErrorHandlingCallback.class)) {
            return (T) new ODataErrorHandlingCallback();
        } else {
            return super.getCallback(callbackInterface);
        }
    }

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
        ODataJPAContext oDataJPAContext = getODataJPAContext();
        oDataJPAContext.setEntityManagerFactory(emf);
        oDataJPAContext.setPersistenceUnitName(puName);
        oDataJPAContext.setJPAEdmExtension(new ODataExtension());
        oDataJPAContext.setDefaultNaming(true);
        return oDataJPAContext;
    }

    public static void setEmf(EntityManagerFactory emf) {
        ODataFactory.emf = emf;
    }

    public static void setPuName(String puName) {
        ODataFactory.puName = puName;
    }

}
