package spet.sbwo.layer;

import javax.persistence.EntityManagerFactory;

import org.h2.server.web.WebServlet;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;
import org.picocontainer.parameters.ConstantParameter;

import spet.sbwo.api.odata.ODataFactory;
import spet.sbwo.api.service.bo.ExpertiseService;
import spet.sbwo.api.service.bo.PersonService;
import spet.sbwo.api.service.misc.ConfigurationService;
import spet.sbwo.api.service.misc.CountService;
import spet.sbwo.api.service.misc.FileExploreService;
import spet.sbwo.api.service.misc.GeocodingService;
import spet.sbwo.api.service.misc.LogService;
import spet.sbwo.api.service.misc.ScheduleService;
import spet.sbwo.api.service.misc.TrashService;
import spet.sbwo.api.service.transfer.CourtImportService;
import spet.sbwo.api.service.transfer.DataImportService;
import spet.sbwo.api.service.transfer.LocationImportService;
import spet.sbwo.api.service.user.FavouriteService;
import spet.sbwo.api.service.user.ManagementService;
import spet.sbwo.api.service.user.PreferenceService;
import spet.sbwo.api.service.user.SelfService;
import spet.sbwo.api.service.user.SessionService;
import spet.sbwo.api.service.user.TileService;

public class Service {
	private static final String LOG_PATH = "log";
	private static final String LOGIN_RELATIVE_PATH = "/../login/index.html";

	private Service() {
		super();
	}

	public static void install(MutablePicoContainer container) {
		ODataFactory.setEmf(container.getComponent(EntityManagerFactory.class));
		ODataFactory.setPuName(Database.SBWO_PU);
		container.addComponent(WebServlet.class);
		container.addComponent(PersonService.class);
		container.addComponent(ConfigurationService.class);
		container.addComponent(CountService.class);
		container.addComponent(FileExploreService.class);
		container.addComponent(LogService.class, LogService.class, new ConstantParameter(LOG_PATH));
		container.addComponent(ScheduleService.class);
		container.addComponent(TrashService.class);
		container.addComponent(DataImportService.class);
		container.addComponent(LocationImportService.class);
		container.addComponent(FavouriteService.class);
		container.addComponent(ManagementService.class);
		container.addComponent(SessionService.class, SessionService.class, new ComponentParameter(),
				new ConstantParameter(LOGIN_RELATIVE_PATH));
		container.addComponent(PreferenceService.class);
		container.addComponent(TileService.class);
		container.addComponent(SelfService.class);
		container.addComponent(CourtImportService.class);
		container.addComponent(ExpertiseService.class);
		container.addComponent(GeocodingService.class);
	}
}
