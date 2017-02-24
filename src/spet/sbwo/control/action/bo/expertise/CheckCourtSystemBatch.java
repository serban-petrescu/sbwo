package spet.sbwo.control.action.bo.expertise;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.action.base.BaseDatabaseAction;
import spet.sbwo.control.action.base.IDatabaseAction;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.integration.api.court.ICourtSystemApi;

public class CheckCourtSystemBatch extends BaseDatabaseAction<CheckCourtSystemBatch.Input, Void> {
	private final CheckCourtSystem check;

	public CheckCourtSystemBatch(ICourtSystemApi api) {
		super(ExpertiseChannel.class);
		this.check = new CheckCourtSystem(api);
	}

	@Override
	protected Void doRun(Input input, IDatabaseExecutor executor) throws ControlException, DatabaseException {
		LocalDateTime newest = LocalDateTime.now().minus(input.getDuration());
		List<Expertise> exps = executor.queryListLimit("Expertise.selectByLastChecked", Expertise.class,
				input.getCount(), newest);
		for (Expertise expertise : exps) {
			check.run(expertise, executor);
		}
		return null;
	}

	public static IDatabaseAction<Void, Void> forInput(Duration duration, int count, ICourtSystemApi api) {
		return (i, e) -> new CheckCourtSystemBatch(api).run(new Input(duration, count), e);
	}

	public static class Input {
		private final Duration duration;
		private final int count;

		public Input(Duration duration, int count) {
			this.duration = duration;
			this.count = count;
		}

		public Duration getDuration() {
			return duration;
		}

		public int getCount() {
			return count;
		}

	}
}