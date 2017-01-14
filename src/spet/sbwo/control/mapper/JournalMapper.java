package spet.sbwo.control.mapper;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.JournalChannel;
import spet.sbwo.control.channel.UserChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.base.JournalizedBaseEntity;
import spet.sbwo.data.table.User;

public abstract class JournalMapper<I extends JournalizedBaseEntity, O extends JournalChannel>
		extends BaseMapper<I, O> {
	private static final Logger LOG = LoggerFactory.getLogger(JournalMapper.class);

	public JournalMapper(IDatabaseExecutor executor) {
		super(executor);
	}

	@Override
	public void merge(O result, I input) throws ControlException {
		super.merge(result, input);
		result.setChangedBy(this.toExternal(input.getChangedBy()));
		result.setChangedOn(this.toExternal(input.getChangedOn()));
		result.setCreatedBy(this.toExternal(input.getChangedBy()));
		result.setCreatedOn(this.toExternal(input.getCreatedOn()));
		result.setDeleted(input.isDeleted());
	}

	@Override
	public void merge(I internal, O external) throws ControlException {
		super.merge(internal, external);
		internal.setChangedBy(this.toInternal(external.getChangedBy()));
		internal.setChangedOn(this.toInternal(external.getChangedOn()));
		internal.setCreatedBy(this.toInternal(external.getCreatedBy()));
		internal.setCreatedOn(this.toInternal(external.getCreatedOn()));
		this.ifNotNull(external.getDeleted(), internal::setDeleted);
	}

	protected Long toExternal(Timestamp ts) {
		if (ts != null) {
			return ts.getTime();
		} else {
			return null;
		}
	}

	protected Timestamp toInternal(Long ts) {
		if (ts != null) {
			return new Timestamp(ts);
		} else {
			return null;
		}
	}

	protected User toInternal(UserChannel user) throws ControlException {
		if (user != null && user.getId() != null) {
			try {
				return this.executor.find(User.class, user.getId());
			} catch (DatabaseException e) {
				LOG.warn("Unable to retrieve user during internal mapping.", e);
				throw new ControlException(e);
			}
		} else {
			return null;
		}
	}

	protected UserChannel toExternal(User user) {
		if (user != null) {
			UserChannel result = new UserChannel();
			result.setId(user.getId());
			result.setUsername(user.getUsername());
			result.setActive(user.isActive());
			return result;
		} else {
			return null;
		}
	}

}
