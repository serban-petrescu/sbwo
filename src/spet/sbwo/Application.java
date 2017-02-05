package spet.sbwo;

import spet.sbwo.layer.Control;
import spet.sbwo.layer.Database;
import spet.sbwo.layer.Filter;
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
		Filter filter = new Filter();
		Service service = new Service(control, schedule, database);
		Server server = new Server(filter, service, control);
		schedule.getScheduleManager().start();
		server.getServer().start();
	}

}
