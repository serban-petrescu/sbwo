package spet.sbwo.layer;

import spet.sbwo.control.config.Configuration;
import spet.sbwo.control.controller.bo.PersonController;
import spet.sbwo.control.controller.misc.CountController;
import spet.sbwo.control.controller.misc.TrashController;
import spet.sbwo.control.controller.transfer.LocationImportController;
import spet.sbwo.control.controller.user.FavouriteController;
import spet.sbwo.control.controller.user.LoginController;
import spet.sbwo.control.controller.user.ManagementController;
import spet.sbwo.control.controller.user.PreferenceController;
import spet.sbwo.control.controller.user.SessionManager;
import spet.sbwo.control.controller.user.TileController;
import spet.sbwo.control.importer.DataImportFacade;

public class Control {
	private final Configuration configuration;
	private final FavouriteController favouriteController;
	private final PreferenceController preferenceController;
	private final LoginController loginController;
	private final ManagementController managementController;
	private final TileController tileController;
	private final LocationImportController locationImportController;
	private final PersonController personController;
	private final TrashController trashController;
	private final CountController countController;
	private final DataImportFacade dataImportFacade;
	private final SessionManager sessionManager;

	public Control(Database database) {
		configuration = new Configuration("server.json");
		favouriteController = new FavouriteController(database.getFacade());
		preferenceController = new PreferenceController(database.getFacade());
		loginController = new LoginController(database.getFacade());
		managementController = new ManagementController(database.getFacade());
		tileController = new TileController(database.getFacade());
		locationImportController = new LocationImportController(database.getFacade());
		personController = new PersonController(database.getFacade(), configuration.getDirectDeleteInterval());
		trashController = new TrashController(database.getFacade(), personController);
		countController = new CountController(database.getFacade());
		dataImportFacade = new DataImportFacade(database.getFacade());
		sessionManager = new SessionManager(database.getFacade());
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public FavouriteController getFavouriteController() {
		return favouriteController;
	}

	public PreferenceController getPreferenceController() {
		return preferenceController;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public ManagementController getManagementController() {
		return managementController;
	}

	public TileController getTileController() {
		return tileController;
	}

	public LocationImportController getLocationImportController() {
		return locationImportController;
	}

	public PersonController getPersonController() {
		return personController;
	}

	public TrashController getTrashController() {
		return trashController;
	}

	public CountController getCountController() {
		return countController;
	}

	public DataImportFacade getDataImportFacade() {
		return dataImportFacade;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

}
