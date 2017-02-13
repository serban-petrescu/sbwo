package spet.sbwo;

import spet.sbwo.layer.Control;
import spet.sbwo.layer.Database;
import spet.sbwo.layer.Filter;
import spet.sbwo.layer.Integration;
import spet.sbwo.layer.Producer;
import spet.sbwo.layer.Schedule;
import spet.sbwo.layer.Server;
import spet.sbwo.layer.Service;

public class Application {

	private Application() {
		super();
	}

	public static void main(String[] args) {
		Database database = new Database();
		Control control = new Control(database);
		Schedule schedule = new Schedule(database, control);
		Integration integration = new Integration();
		Filter filter = new Filter();
		Service service = new Service(integration, control, schedule, database);
		Producer producer = new Producer();
		Server server = new Server(filter, service, producer, control);
		schedule.getScheduleManager().start();
		server.getServer().start();
	}

}
