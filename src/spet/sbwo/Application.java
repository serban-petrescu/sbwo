package spet.sbwo;

import java.io.File;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.server.web.WebServlet;

import spet.sbwo.api.AjaxFilter;
import spet.sbwo.api.AuthConditionalFilter;
import spet.sbwo.api.CsrfTokenFilter;
import spet.sbwo.api.ImportService;
import spet.sbwo.api.LocalAddressFilter;
import spet.sbwo.api.ODataFactory;
import spet.sbwo.api.PersonService;
import spet.sbwo.api.PublicService;
import spet.sbwo.api.TrashService;
import spet.sbwo.api.UserService;
import spet.sbwo.api.UtilityService;
import spet.sbwo.control.config.Configuration;
import spet.sbwo.control.controller.ImportController;
import spet.sbwo.control.controller.PersonController;
import spet.sbwo.control.controller.TrashController;
import spet.sbwo.control.controller.UserController;
import spet.sbwo.control.controller.UtilityController;
import spet.sbwo.control.importer.DataImportFacade;
import spet.sbwo.control.scheduler.IScheduleManager;
import spet.sbwo.control.scheduler.ScheduleBuilder;
import spet.sbwo.control.util.SessionManager;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.server.ServerBuilder;

public class Application {

	private static final String WEB_INDEX_PATH = "/private/web/index.html";
	private static final String LOGIN_PATH = "/public/login/index.html";

	private Application() {
		super();
	}

	public static void main(String[] args) {

		// Database layer
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sbwo");
		DatabaseFacade database = new DatabaseFacade(emf);

		// Config
		Configuration configuration = new Configuration("server.json");

		// Control layer
		UserController userController = new UserController(database);
		ImportController importController = new ImportController(database);
		PersonController personController = new PersonController(database, configuration.getDirectDeleteInterval());
		TrashController trashController = new TrashController(database, personController);
		UtilityController utilityController = new UtilityController(database);
		DataImportFacade dataImportFacade = new DataImportFacade(database);
		SessionManager sessionManager = new SessionManager(database);

		// Schedulers
		ScheduleBuilder scheduleBuilder = new ScheduleBuilder().threads(configuration.getSchedulerThreads());
		scheduleBuilder.backup().backuper(database).directory(configuration.getDatabaseBackupLocation())
				.intervalDays(configuration.getDatabaseBackupInterval())
				.delayMillis(configuration.getDatabaseBackupStart());
		scheduleBuilder.cleanup().delayMillis(configuration.getCleanupStart())
				.maxAgeDays(configuration.getCleanupThreshold())
				.addBackupBased(configuration.getDatabaseBackupLocation())
				.addPatternBased(new File("logs"), "yyyyMMdd", "log_(\\d{8})_\\d+");
		IScheduleManager scheduleManager = scheduleBuilder.build();

		// Filters
		CsrfTokenFilter csrfTokenFilter = new CsrfTokenFilter();
		LocalAddressFilter localAddressFilter = new LocalAddressFilter();
		AuthConditionalFilter authConditionalFilter = new AuthConditionalFilter(LOGIN_PATH, WEB_INDEX_PATH);
		AjaxFilter ajaxDenyFilter = new AjaxFilter(false);

		// Services
		ODataFactory.setEmf(emf);
		ODataFactory.setPuName("sbwo");
		PublicService publicService = new PublicService(userController, "/../login/index.html");
		WebServlet dbWebServlet = new WebServlet();
		ImportService importService = new ImportService(importController, dataImportFacade);
		PersonService personService = new PersonService(personController);
		TrashService trashService = new TrashService(trashController);
		UtilityService utilityService = new UtilityService(utilityController, configuration, scheduleManager, "log");
		UserService userService = new UserService(userController);

		// Server
		ServerBuilder serverBuilder = new ServerBuilder();
		serverBuilder.setPort(8080);

		// Root redirect
		serverBuilder.createFilterBuilder().setPath("/").setFilter(authConditionalFilter);
		serverBuilder.createFilterBuilder().setPath("/index.html").setFilter(authConditionalFilter);

		// Public area
		serverBuilder.createFilterBuilder().setPath("/public/rest/*").setFilter(csrfTokenFilter);
		serverBuilder.createFilterBuilder().setPath("/public/rest/user/manage/*").setFilter(localAddressFilter);
		serverBuilder.createFilterBuilder().setPath("/public/users/*").setFilter(localAddressFilter);
		serverBuilder.createFilterBuilder().setPath(LOGIN_PATH).setFilter(ajaxDenyFilter);
		serverBuilder.createServiceBuilder().setPath("/public/rest/*").addService(publicService);
		serverBuilder.createFileBuilder().setPath("/public/*").setBaseDirectory("public");

		// Db console
		serverBuilder.createFilterBuilder().setPath("/db/*").setFilter(localAddressFilter);
		serverBuilder.createServletBuilder().setPath("/db/*").setServlet(dbWebServlet);

		// Private area
		serverBuilder.createFilterBuilder().setPath("/private/api/*").setFilter(csrfTokenFilter);
		serverBuilder.createFilterBuilder().setPath("/public/api/rest/utility/file").setFilter(localAddressFilter);
		serverBuilder.createFileBuilder().setPath("/private/web/*").setBaseDirectory("web");
		serverBuilder.createODataBuilder().setPath("/private/api/odata/*").setFactoryClass(ODataFactory.class);
		serverBuilder.createServiceBuilder().setPath("/private/api/rest/*").addServices(importService, personService,
				trashService, utilityService, userService);

		// Server security
		serverBuilder.setSecuredPath("/private/*");
		serverBuilder.setLoginPage(LOGIN_PATH);
		serverBuilder.setErrorPage("/public/login/index.html#/error");
		serverBuilder.setLoginProvider(userController);

		// Session management
		serverBuilder.setSessionManager(sessionManager);
		serverBuilder.setSessionTimeout(configuration.getSessionTimeout());

		scheduleManager.start();
		serverBuilder.build().start();
	}

}
