package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.integration.api.court.ICourtSystemApi;
import spet.sbwo.integration.web.googlemaps.GoogleMapsGeocodingApi;
import spet.sbwo.integration.web.rojustportal.RoJustPortalFacade;

public class Integration {
	private static final Logger LOG = LoggerFactory.getLogger(Integration.class);

	private Integration() {
		super();
	}

	public static void install(MutablePicoContainer container) {
		container.addComponent(court());
		container.addComponent(GoogleMapsGeocodingApi.class);
	}

	protected static ICourtSystemApi court() {
		try {
			return new RoJustPortalFacade();
		} catch (Exception e) {
			LOG.error("Unable to initialize the just.ro integration.", e);
			return n -> null;
		}
	}

}
