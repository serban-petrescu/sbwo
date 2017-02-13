package spet.sbwo.layer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.integration.api.court.ICourtSystemApi;
import spet.sbwo.integration.web.rojustportal.RoJustPortalFacade;

public class Integration {
	private static final Logger LOG = LoggerFactory.getLogger(Integration.class);
	private ICourtSystemApi courtSystemApi;

	public Integration() {
		try {
			courtSystemApi = new RoJustPortalFacade();
		} catch (Exception e) {
			LOG.error("Unable to initialize the just.ro integration.", e);
			courtSystemApi = n -> null;
		}
	}

	public ICourtSystemApi getCourtSystemApi() {
		return courtSystemApi;
	}

}
