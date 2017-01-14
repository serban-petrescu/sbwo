package spet.sbwo.api;

import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataProcessor;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetComplexPropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetServiceDocumentUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetSimplePropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

public class ODataFactory extends ODataJPAServiceFactory {
	private static EntityManagerFactory emf;
	private static String puName;

	@Override
	public ODataService createODataSingleProcessorService(EdmProvider provider, ODataSingleProcessor processor) {
		return super.createODataSingleProcessorService(provider, new ODataReadOnlyProcessor(processor));
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

	private class ODataExtension implements JPAEdmExtension {
		@Override
		public void extendWithOperation(JPAEdmSchemaView view) {
		}

		@Override
		public void extendJPAEdmSchema(JPAEdmSchemaView view) {
		}

		@Override
		public InputStream getJPAEdmMappingModelStream() {
			return ODataFactory.class.getResourceAsStream("/META-INF/olingo-jpa-mapping.xml");
		}
	}

	private class ODataReadOnlyProcessor extends ODataSingleProcessor {
		private ODataSingleProcessor delegate;

		public ODataReadOnlyProcessor(ODataSingleProcessor delegate) {
			super();
			this.delegate = delegate;
		}

		public ODataResponse countEntityLinks(GetEntitySetLinksCountUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.countEntityLinks(uriInfo, contentType);
		}

		public ODataResponse countEntitySet(GetEntitySetCountUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.countEntitySet(uriInfo, contentType);
		}

		public ODataResponse createEntity(PostUriInfo uriInfo, InputStream content, String requestContentType,
				String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse createEntityLink(PostUriInfo uriInfo, InputStream content, String requestContentType,
				String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse deleteEntity(DeleteUriInfo uriInfo, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse deleteEntityLink(DeleteUriInfo uriInfo, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse deleteEntityMedia(DeleteUriInfo uriInfo, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse deleteEntitySimplePropertyValue(DeleteUriInfo uriInfo, String contentType)
				throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse executeBatch(BatchHandler handler, String contentType, InputStream content)
				throws ODataException {
			return delegate.executeBatch(handler, contentType, content);
		}

		public BatchResponsePart executeChangeSet(BatchHandler handler, List<ODataRequest> requests)
				throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse executeFunctionImport(GetFunctionImportUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.executeFunctionImport(uriInfo, contentType);
		}

		public ODataResponse executeFunctionImportValue(GetFunctionImportUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.executeFunctionImportValue(uriInfo, contentType);
		}

		public ODataResponse existsEntity(GetEntityCountUriInfo uriInfo, String contentType) throws ODataException {
			return delegate.existsEntity(uriInfo, contentType);
		}

		public ODataResponse existsEntityLink(GetEntityLinkCountUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.existsEntityLink(uriInfo, contentType);
		}

		public ODataContext getContext() {
			return delegate.getContext();
		}

		public List<String> getCustomContentTypes(Class<? extends ODataProcessor> processorFeature)
				throws ODataException {
			return delegate.getCustomContentTypes(processorFeature);
		}

		public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
			return delegate.readEntity(uriInfo, contentType);
		}

		public ODataResponse readEntityComplexProperty(GetComplexPropertyUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.readEntityComplexProperty(uriInfo, contentType);
		}

		public ODataResponse readEntityLink(GetEntityLinkUriInfo uriInfo, String contentType) throws ODataException {
			return delegate.readEntityLink(uriInfo, contentType);
		}

		public ODataResponse readEntityLinks(GetEntitySetLinksUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.readEntityLinks(uriInfo, contentType);
		}

		public ODataResponse readEntityMedia(GetMediaResourceUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.readEntityMedia(uriInfo, contentType);
		}

		public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
			return delegate.readEntitySet(uriInfo, contentType);
		}

		public ODataResponse readEntitySimpleProperty(GetSimplePropertyUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.readEntitySimpleProperty(uriInfo, contentType);
		}

		public ODataResponse readEntitySimplePropertyValue(GetSimplePropertyUriInfo uriInfo, String contentType)
				throws ODataException {
			return delegate.readEntitySimplePropertyValue(uriInfo, contentType);
		}

		public ODataResponse readMetadata(GetMetadataUriInfo uriInfo, String contentType) throws ODataException {
			return delegate.readMetadata(uriInfo, contentType);
		}

		public ODataResponse readServiceDocument(GetServiceDocumentUriInfo arg0, String arg1) throws ODataException {
			return delegate.readServiceDocument(arg0, arg1);
		}

		public void setContext(ODataContext context) {
			delegate.setContext(context);
		}

		public String toString() {
			return delegate.toString();
		}

		public ODataResponse updateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType,
				boolean merge, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse updateEntityComplexProperty(PutMergePatchUriInfo uriInfo, InputStream content,
				String requestContentType, boolean merge, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse updateEntityLink(PutMergePatchUriInfo uriInfo, InputStream content,
				String requestContentType, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse updateEntityMedia(PutMergePatchUriInfo uriInfo, InputStream content,
				String requestContentType, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse updateEntitySimpleProperty(PutMergePatchUriInfo uriInfo, InputStream content,
				String requestContentType, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}

		public ODataResponse updateEntitySimplePropertyValue(PutMergePatchUriInfo uriInfo, InputStream content,
				String requestContentType, String contentType) throws ODataException {
			throw new ODataNotImplementedException();
		}
	}

}
