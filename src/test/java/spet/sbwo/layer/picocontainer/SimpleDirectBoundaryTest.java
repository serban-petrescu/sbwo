package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

public class SimpleDirectBoundaryTest {
    private MutablePicoContainer container;

    @Before
    public void setup() {
        container = new PicoBuilder().build();
        container.addComponent(IA.class, A.class);
        container.addComponent(IB.class, B.class);
        container.addComponent(IC.class, C.class);
        container.addComponent(D.class);
    }

    @Test
    public void testConcrete() {
        D d = container.getComponent(D.class);
        assertTrue(d.a instanceof A);
        assertTrue(d.b instanceof B);
        assertTrue(d.c instanceof C);
    }

    @Test
    public void testInterface() {
        IC c = container.getComponent(IC.class);
        assertTrue(c instanceof C);
        assertTrue(((C)c).a instanceof A);
        assertTrue(((C)c).b instanceof B);
    }


}
