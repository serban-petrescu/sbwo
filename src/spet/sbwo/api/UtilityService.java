package spet.sbwo.api;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CountChannel;
import spet.sbwo.control.config.ConfigChannel;
import spet.sbwo.control.config.Configuration;
import spet.sbwo.control.controller.UtilityController;
import spet.sbwo.control.scheduler.IScheduleManager;
import spet.sbwo.control.scheduler.ScheduleChannel;
import spet.sbwo.control.util.FolderScanner;

@Path("/utility")
public class UtilityService extends BaseService {
	private final UtilityController controller;
	private final Configuration configuration;
	private final IScheduleManager scheduleManager;
	private final ObjectWriter countWriter;
	private final ObjectWriter fileWriter;
	private final ObjectWriter folderWriter;
	private final ObjectWriter rootsWriter;
	private final ObjectWriter configWriter;
	private final ObjectReader configReader;
	private final ObjectWriter schedulesWriter;
	private final String logs;

	public UtilityService(UtilityController utilityController, Configuration configuration,
			IScheduleManager scheduleManager, String logs) {
		this.controller = utilityController;
		this.configuration = configuration;
		this.scheduleManager = scheduleManager;
		this.logs = logs;

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory factory = mapper.getTypeFactory();
		JavaType fileType = factory.constructParametricType(FolderScanner.QueryResult.class,
				FolderScanner.FileInfo.class);
		JavaType folderType = factory.constructParametricType(FolderScanner.QueryResult.class, String.class);
		JavaType schedulesType = factory.constructCollectionType(List.class, ScheduleChannel.class);

		this.countWriter = mapper.writerFor(CountChannel.class);
		this.fileWriter = mapper.writerFor(fileType);
		this.folderWriter = mapper.writerFor(folderType);
		this.rootsWriter = mapper.writerFor(FolderScanner.RootResult.class);
		this.configReader = mapper.readerFor(ConfigChannel.class);
		this.configWriter = mapper.writerFor(ConfigChannel.class);
		this.schedulesWriter = mapper.writerFor(schedulesType);
	}

	@GET
	@Path("/count")
	@Produces("application/json")
	public String count() {
		try {
			return this.countWriter.writeValueAsString(this.controller.readCounts());
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@GET
	@Path("/file/explore/files")
	@Produces("application/json")
	public String exploreFiles(@QueryParam("base") String base,
			@QueryParam("extension") @DefaultValue("") String extension) {
		try {
			return fileWriter.writeValueAsString(new FolderScanner(base).files(extension));
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	@GET
	@Path("/file/explore/folders")
	@Produces("application/json")
	public String exploreFolders(@QueryParam("base") String base) {
		try {
			return folderWriter.writeValueAsString(new FolderScanner(base).folders());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/file/explore/roots")
	@Produces("application/json")
	public String exploreRoots() {
		try {
			return rootsWriter.writeValueAsString(FolderScanner.roots());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@GET
	@Path("/file/config/read")
	@Produces("application/json")
	public String readConfiguration() {
		try {
			return configWriter.writeValueAsString(configuration.external());
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@GET
	@Path("/file/logs")
	@Produces("application/json")
	public String readLogs() {
		try {
			return fileWriter.writeValueAsString(new FolderScanner(logs).files("txt"));
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	@GET
	@Path("/file/log/{name}")
	@Produces("application/json")
	public Response readLog(@PathParam("name") String name) {
		try {
			return Response.ok(new JsonFragmentStream(new File(logs + File.separator + name + ".txt"))).build();
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}

	@PUT
	@Path("/file/config/update")
	@Produces("application/json")
	@Consumes("application/json")
	public String updateConfiguration(InputStream body) {
		try {
			configuration.internal(configReader.readValue(body));
			return configWriter.writeValueAsString(configuration.external());
		} catch (ControlException e) {
			this.handleException(e);
		} catch (Exception e) {
			this.handleException(e);
		}
		throw new InternalServerErrorException();
	}

	@GET
	@Path("/file/schedules")
	@Produces("application/json")
	public String readSchedules() {
		try {
			return schedulesWriter.writeValueAsString(scheduleManager.channels());
		} catch (Exception e) {
			throw new NotFoundException(e);
		}
	}
}
