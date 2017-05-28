package spet.sbwo.control.mapper;

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
    public void merge(O external, I internal) {
        super.merge(external, internal);
        external.setChangedBy(toExternal(internal.getChangedBy()));
        external.setChangedOn(internal.getChangedOn());
        external.setCreatedBy(toExternal(internal.getChangedBy()));
        external.setCreatedOn(internal.getCreatedOn());
        external.setDeleted(internal.isDeleted());
    }

    @Override
    public void merge(I internal, O external) {
        super.merge(internal, external);
        internal.setChangedBy(toInternal(external.getChangedBy()));
        internal.setChangedOn(external.getChangedOn());
        internal.setCreatedBy(toInternal(external.getCreatedBy()));
        internal.setCreatedOn(external.getCreatedOn());
        this.ifNotNull(external.getDeleted(), internal::setDeleted);
    }

    protected User toInternal(UserChannel user) {
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
