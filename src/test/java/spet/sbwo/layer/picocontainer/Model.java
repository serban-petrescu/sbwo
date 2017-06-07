package spet.sbwo.layer.picocontainer;

import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.FactoryInjector;

import java.lang.reflect.Type;
import java.util.List;

class Model {

    public interface IA {
    }

    public interface IB {
    }

    public interface IC {
    }

    public static class A implements IA {
    }

    public static class B implements IB {
    }

    public static class C implements IC {
        final IA a;
        final IB b;

        public C(IA a, IB b) {
            this.a = a;
            this.b = b;
        }
    }

    public static class D {
        final IA a;
        final IB b;
        final IC c;

        public D(IA a, IB b, IC c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    interface IE {

    }

    public static class E1 implements IE {

    }

    public static class E2 implements IE {

    }

    public static class E3 implements IE {

    }

    public static class F {
        final IE[] es;

        public F(IE[] es) {
            this.es = es;
        }

    }

    public static class G {
        final List<IE> es;

        public G(List<IE> es) {
            this.es = es;
        }
    }

    public interface IH<T> {
        T something();
    }

    public static class HA implements IH<IA> {
        public IA something() {
            return new A();
        }
    }

    public static class HB implements IH<IB> {
        public IB something() {
            return new B();
        }
    }

    public static class J {
        final IH<IA> ha;
        final IH<IB> hb;
        public J(IH<IA> ha, final IH<IB> hb) {
            this.ha = ha;
            this.hb = hb;
        }
    }

    public static class AFactory extends FactoryInjector<IA> {
        static final A a = new A();

        @Override
        public IA getComponentInstance(PicoContainer container, Type into) {
            return a;
        }
    }

    public static class BFactory extends FactoryInjector<IB> {
        static final B b = new B();

        @Override
        public IB getComponentInstance(PicoContainer container, Type into) {
            return b;
        }
    }
}
