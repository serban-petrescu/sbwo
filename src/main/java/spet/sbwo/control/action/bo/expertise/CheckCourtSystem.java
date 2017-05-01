package spet.sbwo.control.action.bo.expertise;

import java.time.LocalDateTime;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.integration.api.court.ICourtSystemApi;
import spet.sbwo.integration.api.court.model.Case;
import spet.sbwo.integration.api.court.model.Hearing;

public class CheckCourtSystem extends BaseDatabaseAction<Expertise, Void> {
	private final ICourtSystemApi api;

	public CheckCourtSystem(ICourtSystemApi api) {
		super(ExpertiseChannel.class);
		this.api = api;
	}

	@Override
	protected Void doRun(Expertise input, IDatabaseExecutor executor) throws ControlException, DatabaseException {
		Case result = api.read(input.getNumber());
		if (result != null) {
			result.getHearings().stream().map(Hearing::getDate).reduce((a, b) -> a.isBefore(b) ? b : a)
					.ifPresent(input::setNextHearing);
		}
		input.setLastCheckedOn(LocalDateTime.now());
		return null;
	}
}
