package spet.sbwo.api.service.misc;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import spet.sbwo.api.service.base.BaseService;
import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.scheduler.IScheduleManager;
import spet.sbwo.control.scheduler.ScheduleChannel;

@Path("/utility/file/schedules")
public class ScheduleService extends BaseService implements IPrivate {
	private final IScheduleManager scheduleManager;

	public ScheduleService(IScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public List<ScheduleChannel> readSchedules() {
		try {
			return scheduleManager.channels();
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
