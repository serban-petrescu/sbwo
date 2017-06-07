package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.parameters.ComponentParameter;

import static spet.sbwo.layer.picocontainer.Model.*;

public class GenericsDirectBoundaryTest {
    private MutablePicoContainer container;

    @Before
    public void setup() {
        container = new PicoBuilder().build();
        container.addComponent(HA.class);
        container.addComponent(HB.class);
        container.addComponent(J.class, J.class, new ComponentParameter(HA.class), new ComponentParameter(HB.class));
    }

    @Test
    public void testCorrectTypes() {
        J j = container.getComponent(J.class);
        assertTrue(j.ha instanceof HA);
        assertTrue(j.hb instanceof HB);
    }

}
