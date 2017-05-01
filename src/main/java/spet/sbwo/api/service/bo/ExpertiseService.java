package spet.sbwo.api.service.bo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import spet.sbwo.api.service.base.IPrivate;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.control.controller.bo.ExpertiseController;
import spet.sbwo.integration.api.court.ICourtSystemApi;
import spet.sbwo.integration.api.court.model.Case;

@Path("/expertise")
public class ExpertiseService extends BaseBoService<ExpertiseChannel, ExpertiseController>implements IPrivate {
	private final ICourtSystemApi courtSystemApi;

	public ExpertiseService(ExpertiseController controller, ICourtSystemApi courtSystemApi) {
		super(controller);
		this.courtSystemApi = courtSystemApi;
	}

	@GET
	@Path("/case")
	@Produces("application/json")
	public Case readExternalCase(@QueryParam("number") String number) {
		try {
			return courtSystemApi.read(number);
		} catch (Exception e) {
			throw mapException(e);
		}
	}
}
