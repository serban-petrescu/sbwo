package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import static spet.sbwo.layer.picocontainer.Model.*;
import org.picocontainer.PicoBuilder;

import static org.junit.Assert.assertTrue;

public class SimpleSingletonBoundaryTest {
    private MutablePicoContainer container;
    private final A a = new A();
    private final B b = new B();

    @Before
    public void setUp() {
        container = new PicoBuilder().withCaching().build();
        container.addComponent(IA.class, a);
        container.addComponent(IB.class, b);
        container.addComponent(D.class);
        container.addComponent(IC.class, C.class);
    }

    @Test
    public void testParent() {
        C c = (C)container.getComponent(IC.class);
        assertTrue(c.a == a);
        assertTrue(c.b == b);
    }

    @Test
    public void testTopLevel() {
        D d = container.getComponent(D.class);
        C c = (C)d.c;
        assertTrue(d.a == a);
        assertTrue(d.b == b);
        assertTrue(c.a == a);
        assertTrue(c.b == b);
    }

    @Test
    public void testRepeated() {
        assertTrue(container.getComponent(D.class) == container.getComponent(D.class));
    }

}
