package spet.sbwo.control.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.CourtChannel;
import spet.sbwo.control.channel.ExpertiseChannel;
import spet.sbwo.control.channel.ExpertiseChannel.Fine;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.domain.ExpertiseStatus;
import spet.sbwo.data.embed.Tariff;
import spet.sbwo.data.table.Court;
import spet.sbwo.data.table.Expertise;
import spet.sbwo.data.table.ExpertiseFine;

public class ExpertiseMapper extends JournalMapper<Expertise, ExpertiseChannel> {
	private static final Logger LOG = LoggerFactory.getLogger(ExpertiseMapper.class);
	private final FineMapper fineMapper;
	private final LocationMapper locationMapper;

	public ExpertiseMapper(IDatabaseExecutor executor) {
		super(executor);
		fineMapper = new FineMapper(executor);
		locationMapper = new LocationMapper(executor);
	}

	@Override
	public void merge(Expertise internal, ExpertiseChannel external) throws ControlException {
		super.merge(internal, external);
		internal.setCourt(toInternal(external.getCourt()));
		internal.setLastCheckedOn(external.getLastCheckedOn());
		internal.setNote(external.getNote());
		internal.setNumber(external.getNumber());
		internal.setResponsible(toInternal(external.getResponsible()));
		internal.setStatus(getEnumValue(ExpertiseStatus.values(), external.getStatus()));
		internal.setTitle(external.getTitle());
		internal.setNextHearing(external.getNextHearing());
		ifNotNull(external.getYear(), internal::setYear);
		internal.setTariff(new Tariff(external.getPrice(), external.getAdvance()));
		internal.setFines(fineMapper.merge(internal.getFines(), external.getFines()));
		addOperations(fineMapper);
		internal.setLocation(locationMapper.toInternalMandatory(internal.getLocation(), external.getLocation()));
		addOperations(0, locationMapper);
	}

	@Override
	public void merge(ExpertiseChannel external, Expertise internal) throws ControlException {
		super.merge(external, internal);
		external.setCourt(toExternal(internal.getCourt()));
		external.setLastCheckedOn(internal.getLastCheckedOn());
		external.setNote(internal.getNote());
		external.setNumber(internal.getNumber());
		external.setResponsible(toExternal(internal.getResponsible()));
		external.setStatus(internal.getStatus() == null ? null : internal.getStatus().ordinal());
		external.setTitle(internal.getTitle());
		external.setYear(internal.getYear());
		external.setNextHearing(internal.getNextHearing());
		ifNotNull(internal.getTariff(), t -> {
			external.setPrice(t.getPrice());
			external.setAdvance(t.getAdvance());
		});
		external.setFines(fineMapper.toExternal(internal.getFines()));
		external.setLocation(locationMapper.toExternal(internal.getLocation()));
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

	protected static class FineMapper extends BaseMapper<ExpertiseFine, Fine> {
		public FineMapper(IDatabaseExecutor executor) {
			super(executor);
		}

		@Override
		protected ExpertiseFine newInternal(Fine external) throws ControlException {
			return new ExpertiseFine();
		}

		@Override
		protected Fine newExternal(ExpertiseFine internal) throws ControlException {
			return new Fine();
		}

		@Override
		public void merge(ExpertiseFine internal, Fine external) throws ControlException {
			super.merge(internal, external);
			internal.setDate(external.getDate());
			internal.setSum(external.getSum());
		}

		@Override
		public void merge(Fine external, ExpertiseFine internal) throws ControlException {
			super.merge(external, internal);
			external.setDate(internal.getDate());
			external.setSum(internal.getSum());
		}

	}
}
