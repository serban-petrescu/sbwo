package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;

public class ListInstancesBoundaryTest {
    private MutablePicoContainer container;
    private final E1 e1 = new E1();
    private final E2 e2 = new E2();
    private final E3 e3 = new E3();

    @Before
    public void setup() {
        container = new PicoBuilder().build();
        container.addComponent(e1);
        container.addComponent(e2);
        container.addComponent(e3);
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
        assertTrue(g.es.indexOf(e1) >= 0);
        assertTrue(g.es.indexOf(e2) >= 0);
        assertTrue(g.es.indexOf(e3) >= 0);
    }
}
