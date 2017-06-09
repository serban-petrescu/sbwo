package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;

public class ArrayInstancesBoundaryTest {
    private MutablePicoContainer container;
    private final E1 e1 = new E1();
    private final E2 e2 = new E2();
    private final E3 e3 = new E3();

    @Before
    public void setUp() {
        container = new PicoBuilder().build();
        container.addComponent(e1);
        container.addComponent(e2);
        container.addComponent(e3);
        container.addComponent(F.class);
    }

    @Test
    public void testArrayLength() {
        F f = container.getComponent(F.class);
        assertEquals(3, f.es.length);
    }

    @Test
    public void testArrayContents() {
        F f = container.getComponent(F.class);
        assertTrue(Utils.arrayContains(f.es, e1));
        assertTrue(Utils.arrayContains(f.es, e2));
        assertTrue(Utils.arrayContains(f.es, e3));
    }
}
