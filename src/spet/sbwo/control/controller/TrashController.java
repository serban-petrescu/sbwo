package spet.sbwo.control.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.data.DatabaseException;
import spet.sbwo.data.access.IDatabaseExecutor;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.view.DeletedEntity;

public class TrashController extends BaseMainController {
	private static final Logger LOG = LoggerFactory.getLogger(TrashController.class);
	protected final PersonController personController;

	public TrashController(IDatabaseExecutorCreator database, PersonController personController) {
		super(database);
		this.personController = personController;
	}

	public void delete(List<TrashChannel> entities, String username) throws ControlException {
		for (TrashChannel entity : entities) {
			switch (entity.getType()) {
			case 0:
				this.personController.deletePerson(entity.getId(), true, username);
				break;
			default:
				LOG.error("Entity type {} is unknown.", entity.getType());
				break;
			}
		}
	}

	public void restore(List<TrashChannel> entities, String username) throws ControlException {
		for (TrashChannel entity : entities) {
			switch (entity.getType()) {
			case 0:
				this.personController.restorePerson(entity.getId(), username);
				break;
			default:
				LOG.error("Entity type {} is unknown.", entity.getType());
				break;
			}
		}
	}

	public void deleteAll(String username) throws ControlException {
		List<TrashChannel> channels = new LinkedList<>();
		try (IDatabaseExecutor executor = this.database.createExecutor(false)) {
			List<DeletedEntity> entities = executor.select(DeletedEntity.class).execute();
			for (DeletedEntity entity : entities) {
				TrashChannel channel = new TrashChannel();
				channel.setId(entity.getKey().getId());
				channel.setType(entity.getKey().getType());
				channels.add(channel);
			}
		} catch (DatabaseException e) {
			LOG.error("Unable to read all deleted entities.");
			throw new ControlException(e, TrashChannel.class);
		}
		this.delete(channels, username);
	}

}
