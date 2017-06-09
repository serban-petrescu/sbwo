package spet.sbwo.api.odata;

import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

import java.io.InputStream;

class ODataExtension implements JPAEdmExtension {
    @Override
    public void extendWithOperation(JPAEdmSchemaView view) {
        // no need for function imports (yet)
    }

    @Override
    public void extendJPAEdmSchema(JPAEdmSchemaView view) {
        // no need for custom entities (yet)
    }

    @Override
    public InputStream getJPAEdmMappingModelStream() {
        return ODataFactory.class.getResourceAsStream("/META-INF/olingo-jpa-mapping.xml");
    }
}
