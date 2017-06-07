package spet.sbwo.layer.picocontainer;

import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;
import org.picocontainer.parameters.ComponentParameter;

import static org.junit.Assert.assertTrue;
import static spet.sbwo.layer.picocontainer.Model.*;

public class GenericsInstancesBoundaryTest {
    private MutablePicoContainer container;
    private final HA ha = new HA();
    private final HB hb = new HB();

    @Before
    public void setup() {
        container = new PicoBuilder().build();
        container.addComponent(ha);
        container.addComponent(hb);
        container.addComponent(J.class, J.class, new ComponentParameter(HA.class), new ComponentParameter(HB.class));
    }

    @Test
    public void testCorrectTypes() {
        J j = container.getComponent(J.class);
        assertTrue(ha == j.ha);
        assertTrue(hb == j.hb);
    }

}
