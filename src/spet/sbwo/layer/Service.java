package spet.sbwo.layer;

import java.util.Arrays;
import java.util.List;

import org.h2.server.web.WebServlet;

import spet.sbwo.api.odata.ODataFactory;
import spet.sbwo.api.service.bo.ExpertiseService;
import spet.sbwo.api.service.bo.PersonService;
import spet.sbwo.api.service.misc.ConfigurationService;
import spet.sbwo.api.service.misc.CountService;
import spet.sbwo.api.service.misc.FileExploreService;
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

	private final WebServlet dbWebServlet;
	private final PersonService personService;
	private final ConfigurationService configurationService;
	private final CountService countService;
	private final FileExploreService fileExploreService;
	private final LogService logService;
	private final ScheduleService scheduleService;
	private final TrashService trashService;
	private final DataImportService dataImportService;
	private final LocationImportService locationImportService;
	private final FavouriteService favouriteService;
	private final ManagementService managementService;
	private final PreferenceService preferenceService;
	private final SessionService sessionService;
	private final TileService tileService;
	private final SelfService selfService;
	private final CourtImportService courtImportService;
	private final ExpertiseService expertiseService;

	public Service(Integration integration, Control control, Schedule schedule, Database database) {
		ODataFactory.setEmf(database.getEmf());
		ODataFactory.setPuName("sbwo");
		dbWebServlet = new WebServlet();
		personService = new PersonService(control.getPersonController());
		configurationService = new ConfigurationService(control.getConfiguration());
		countService = new CountService(control.getCountController());
		fileExploreService = new FileExploreService();
		logService = new LogService(LOG_PATH);
		scheduleService = new ScheduleService(schedule.getScheduleManager());
		trashService = new TrashService(control.getTrashController());
		dataImportService = new DataImportService(control.getDataImportFacade());
		locationImportService = new LocationImportService(control.getLocationImportController());
		favouriteService = new FavouriteService(control.getFavouriteController());
		managementService = new ManagementService(control.getManagementController());
		preferenceService = new PreferenceService(control.getPreferenceController());
		sessionService = new SessionService(LOGIN_RELATIVE_PATH);
		tileService = new TileService(control.getTileController());
		selfService = new SelfService(control.getManagementController());
		courtImportService = new CourtImportService(control.getCourtImportController());
		expertiseService = new ExpertiseService(control.getExpertiseController(), integration.getCourtSystemApi());
	}

	public List<Object> getPublicServices() {
		return Arrays.asList(managementService, sessionService);
	}

	public List<Object> getPrivateServices() {
		return Arrays.asList(personService, configurationService, countService, fileExploreService, logService,
				scheduleService, trashService, dataImportService, locationImportService, favouriteService,
				preferenceService, tileService, selfService, expertiseService, courtImportService);
	}

	public WebServlet getDbWebServlet() {
		return dbWebServlet;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public CountService getCountService() {
		return countService;
	}

	public FileExploreService getFileExploreService() {
		return fileExploreService;
	}

	public LogService getLogService() {
		return logService;
	}

	public ScheduleService getScheduleService() {
		return scheduleService;
	}

	public TrashService getTrashService() {
		return trashService;
	}

	public DataImportService getDataImportService() {
		return dataImportService;
	}

	public LocationImportService getLocationImportService() {
		return locationImportService;
	}

	public FavouriteService getFavouriteService() {
		return favouriteService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public PreferenceService getPreferenceService() {
		return preferenceService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public TileService getTileService() {
		return tileService;
	}

	public SelfService getSelfService() {
		return selfService;
	}

	public CourtImportService getCourtImportService() {
		return courtImportService;
	}

	public ExpertiseService getExpertiseService() {
		return expertiseService;
	}
}
