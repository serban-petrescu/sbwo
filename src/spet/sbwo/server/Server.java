package spet.sbwo.server;

import org.eclipse.jetty.server.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements IServer {
	private static final Logger LOG = LoggerFactory.getLogger(Server.class);
	private org.eclipse.jetty.server.Server server;

	public Server(int port) {
		this.server = new org.eclipse.jetty.server.Server(port);
	}

	@Override
	public void start() {
		try {
			this.server.start();
		} catch (Exception e) {
			LOG.error("Unable to start server.", e);
		}
	}

	@Override
	public void stop() {
		try {
			this.server.stop();
		} catch (Exception e) {
			LOG.error("Unable to stop server.", e);
		}
	}

	void setHandler(Handler handler) {
		this.server.setHandler(handler);
	}

	org.eclipse.jetty.server.Server getInnerServer() {
		return server;
	}

}
