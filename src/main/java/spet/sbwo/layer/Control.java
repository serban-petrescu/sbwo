package spet.sbwo.layer;

import org.picocontainer.MutablePicoContainer;

import spet.sbwo.control.controller.bo.ExpertiseController;
import spet.sbwo.control.controller.bo.PersonController;
import spet.sbwo.control.controller.misc.CountController;
import spet.sbwo.control.controller.misc.TrashController;
import spet.sbwo.control.controller.transfer.CourtImportController;
import spet.sbwo.control.controller.transfer.LocationImportController;
import spet.sbwo.control.controller.user.CachedSessionManager;
import spet.sbwo.control.controller.user.FavouriteController;
import spet.sbwo.control.controller.user.ISessionManager;
import spet.sbwo.control.controller.user.LoginController;
import spet.sbwo.control.controller.user.ManagementController;
import spet.sbwo.control.controller.user.PreferenceController;
import spet.sbwo.control.controller.user.SessionManager;
import spet.sbwo.control.controller.user.TileController;
import spet.sbwo.control.importer.DataImportFacade;
import spet.sbwo.control.runnable.RunCheckCourtBatch;
import spet.sbwo.control.runnable.RunGeocodeLocationBatch;

public class Control {

    private Control() {
        super();
    }

    public static void install(MutablePicoContainer container) {
        container.addComponent(FavouriteController.class);
        container.addComponent(PreferenceController.class);
        container.addComponent(LoginController.class);
        container.addComponent(ManagementController.class);
        container.addComponent(TileController.class);
        container.addComponent(LocationImportController.class);
        container.addComponent(PersonController.class);
        container.addComponent(TrashController.class);
        container.addComponent(CountController.class);
        container.addComponent(DataImportFacade.class);
        container.addComponent(SessionManager.class);
        container.addComponent(ExpertiseController.class);
        container.addComponent(CourtImportController.class);
        container.addComponent(ISessionManager.class, CachedSessionManager.class);
        container.addComponent(RunCheckCourtBatch.class);
        container.addComponent(RunGeocodeLocationBatch.class);
    }
}
