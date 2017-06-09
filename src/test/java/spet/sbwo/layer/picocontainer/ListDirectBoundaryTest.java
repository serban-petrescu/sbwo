package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;

public class ListDirectBoundaryTest {
    private MutablePicoContainer container;

    @Before
    public void setUp() {
        container = new PicoBuilder().build();
        container.addComponent(E1.class);
        container.addComponent(E2.class);
        container.addComponent(E3.class);
        container.addComponent(G.class);
    }

    @Test
    public void testListSize() {
        G g = container.getComponent(G.class);
        assertEquals(3, g.es.size());
    }

    @Test
    public void testListContents() {
        G g = container.getComponent(G.class);
        assertTrue(Utils.collectionContainsInstanceOf(g.es, E1.class));
        assertTrue(Utils.collectionContainsInstanceOf(g.es, E2.class));
        assertTrue(Utils.collectionContainsInstanceOf(g.es, E3.class));
    }
}
