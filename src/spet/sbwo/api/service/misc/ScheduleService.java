package spet.sbwo.api.service.misc;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import spet.sbwo.api.service.BaseService;
import spet.sbwo.control.scheduler.IScheduleManager;
import spet.sbwo.control.scheduler.ScheduleChannel;

@Path("/utility/file/schedules")
public class ScheduleService extends BaseService {
	private final IScheduleManager scheduleManager;
	private final ObjectWriter schedulesWriter;

	public ScheduleService(
			IScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory factory = mapper.getTypeFactory();
		JavaType schedulesType = factory.constructCollectionType(List.class, ScheduleChannel.class);

		this.schedulesWriter = mapper.writerFor(schedulesType);
	}

	@GET
	@Path("/read")
	@Produces("application/json")
	public String readSchedules() {
		try {
			return schedulesWriter.writeValueAsString(scheduleManager.channels());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
