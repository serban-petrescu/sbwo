package spet.sbwo.layer.picocontainer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import static spet.sbwo.layer.picocontainer.Model.*;

public class ArrayDirectBoundaryTest {
    private MutablePicoContainer container;

    @Before
    public void setup() {
        container = new PicoBuilder().build();
        container.addComponent(E1.class);
        container.addComponent(E2.class);
        container.addComponent(E3.class);
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
        assertTrue(Utils.arrayContainsInstanceOf(f.es, E1.class));
        assertTrue(Utils.arrayContainsInstanceOf(f.es, E2.class));
        assertTrue(Utils.arrayContainsInstanceOf(f.es, E3.class));
    }
}
