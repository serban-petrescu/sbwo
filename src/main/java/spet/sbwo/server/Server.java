package spet.sbwo.server;

import org.eclipse.jetty.server.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default server implementation.
 *
 * @author Serban Petrescu
 */
public class Server implements IServer {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private org.eclipse.jetty.server.Server inner;

    public Server(int port) {
        this.inner = new org.eclipse.jetty.server.Server(port);
    }

    @Override
    public void start() {
        try {
            this.inner.start();
        } catch (Exception e) {
            LOG.error("Unable to start server.", e);
        }
    }

    @Override
    public void stop() {
        try {
            this.inner.stop();
        } catch (Exception e) {
            LOG.error("Unable to stop server.", e);
        }
    }

    /**
     * Sets the root handler.
     */
    void setHandler(Handler handler) {
        this.inner.setHandler(handler);
    }

    org.eclipse.jetty.server.Server getInnerServer() {
        return inner;
    }

}
