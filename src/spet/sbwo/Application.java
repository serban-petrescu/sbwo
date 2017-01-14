package spet.sbwo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.h2.server.web.WebServlet;

import spet.sbwo.api.CsrfTokenFilter;
import spet.sbwo.api.ImportService;
import spet.sbwo.api.LocalAddressFilter;
import spet.sbwo.api.ODataFactory;
import spet.sbwo.api.PersonService;
import spet.sbwo.api.PublicService;
import spet.sbwo.api.TrashService;
import spet.sbwo.api.UserService;
import spet.sbwo.api.UtilityService;
import spet.sbwo.control.controller.ImportController;
import spet.sbwo.control.controller.PersonController;
import spet.sbwo.control.controller.TrashController;
import spet.sbwo.control.controller.UserController;
import spet.sbwo.control.controller.UtilityController;
import spet.sbwo.control.importer.DataImportFacade;
import spet.sbwo.data.access.DatabaseFacade;
import spet.sbwo.server.ServerBuilder;

public class Application {

	public static void main(String[] args) {

		// Database layer
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("sbwo");
		DatabaseFacade database = new DatabaseFacade(emf);

		// Control layer
		UserController userController = new UserController(database);
		ImportController importController = new ImportController(database);
		PersonController personController = new PersonController(database);
		TrashController trashController = new TrashController(database, personController);
		UtilityController utilityController = new UtilityController(database);
		DataImportFacade dataImportFacade = new DataImportFacade(database);

		// Service layer
		ODataFactory.setEmf(emf);
		ODataFactory.setPuName("sbwo");
		PublicService publicService = new PublicService(userController);
		WebServlet dbWebServlet = new WebServlet();
		CsrfTokenFilter csrfTokenFilter = new CsrfTokenFilter();
		LocalAddressFilter localAddressFilter = new LocalAddressFilter();
		ImportService importService = new ImportService(importController, dataImportFacade);
		PersonService personService = new PersonService(personController);
		TrashService trashService = new TrashService(trashController);
		UtilityService utilityService = new UtilityService(utilityController);
		UserService userService = new UserService(userController);

		// Server
		ServerBuilder serverBuilder = new ServerBuilder();
		serverBuilder.setPort(8080);

		// Public area
		serverBuilder.createFilterBuilder().setPath("/public/rest/*").setFilter(csrfTokenFilter);
		serverBuilder.createFilterBuilder().setPath("/public/rest/user/manage/*").setFilter(localAddressFilter);
		serverBuilder.createFilterBuilder().setPath("/public/users/*").setFilter(localAddressFilter);
		serverBuilder.createServiceBuilder().setPath("/public/rest/*").addService(publicService);
		serverBuilder.createFileBuilder().setPath("/public/*").setBaseDirectory("public");

		// Db console
		serverBuilder.createFilterBuilder().setPath("/db/*").setFilter(localAddressFilter);
		serverBuilder.createServletBuilder().setPath("/db/*").setServlet(dbWebServlet);

		// Private area
		serverBuilder.createFilterBuilder().setPath("/private/api/*").setFilter(csrfTokenFilter);
		serverBuilder.createFileBuilder().setPath("/private/web/*").setBaseDirectory("web");
		serverBuilder.createODataBuilder().setPath("/private/api/odata/*").setFactoryClass(ODataFactory.class);
		serverBuilder.createServiceBuilder().setPath("/private/api/rest/*").addService(importService)
				.addService(personService).addService(trashService).addService(utilityService).addService(userService);

		// Server security
		serverBuilder.setSecuredPath("/private/private/*");
		serverBuilder.setLoginPage("/public/login/index.html");
		serverBuilder.setErrorPage("/public/login/index.html#/error");
		serverBuilder.setLoginProvider(userController);

		serverBuilder.build().start();
	}

}
