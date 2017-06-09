package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;

public class SimpleFactoryBoundaryTest {
    private MutablePicoContainer container;

    @Before
    public void setUp() {
        container = new PicoBuilder().withCaching().build();
        container.addAdapter(new AFactory());
        container.addAdapter(new BFactory());
        container.addComponent(IC.class, C.class);
    }

    @Test
    public void testFactoryInstances() {
        C c = (C)container.getComponent(IC.class);
        assertTrue(c.a == AFactory.a);
        assertTrue(c.b == BFactory.b);
    }

}
