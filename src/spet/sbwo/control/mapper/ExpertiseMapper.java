package spet.sbwo.control.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CourtChannel;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.table.Court;
import spet.sbwo.data.table.Expertise;

public class ExpertiseMapper extends JournalMapper<Expertise, ExpertiseChannel> {
	private static final Logger LOG = LoggerFactory.getLogger(ExpertiseMapper.class);

	public ExpertiseMapper(IDatabaseExecutor executor) {
		super(executor);
	}

	@Override
	public void merge(Expertise internal, ExpertiseChannel external) throws ControlException {
		super.merge(internal, external);
		internal.setCourt(toInternal(external.getCourt()));
		internal.setLastCheckedOn(toInternalTimestamp(external.getLastCheckedOn()));
		internal.setNote(external.getNote());
		internal.setNumber(external.getNumber());
		internal.setResponsible(toInternal(external.getResponsible()));
		internal.setStatus(getEnumValue(ExpertiseStatus.values(), external.getStatus()));
		internal.setTitle(external.getTitle());
		internal.setNextHearing(toInternalDate(external.getNextHearing()));
		this.ifNotNull(external.getYear(), internal::setYear);
	}

	@Override
	public void merge(ExpertiseChannel result, Expertise input) throws ControlException {
		super.merge(result, input);
		result.setCourt(toExternal(input.getCourt()));
		result.setLastCheckedOn(toExternalTimestamp(input.getLastCheckedOn()));
		result.setNote(input.getNote());
		result.setNumber(input.getNumber());
		result.setResponsible(toExternal(input.getResponsible()));
		result.setStatus(input.getStatus() == null ? null : input.getStatus().ordinal());
		result.setTitle(input.getTitle());
		result.setYear(input.getYear());
		result.setNextHearing(toExternalDate(input.getNextHearing()));
	}

	@Override
	protected Expertise newInternal(ExpertiseChannel external) throws ControlException {
		return new Expertise();
	}

	@Override
	protected ExpertiseChannel newExternal(Expertise internal) throws ControlException {
		return new ExpertiseChannel();
	}

	protected CourtChannel toExternal(Court court) {
		if (court != null) {
			CourtChannel channel = new CourtChannel();
			channel.setId(court.getId());
			channel.setCode(court.getCode());
			channel.setName(court.getName());
			return channel;
		} else {
			return null;
		}
	}

	protected Court toInternal(CourtChannel channel) throws ControlException {
		if (channel != null && channel.getId() != null) {
			try {
				return executor.find(Court.class, channel.getId());
			} catch (DatabaseException e) {
				LOG.error("Error while reading court for expertise.");
				throw new ControlException(e);
			}
		} else {
			return null;
		}
	}

}
