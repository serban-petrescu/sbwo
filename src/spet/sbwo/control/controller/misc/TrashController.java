package spet.sbwo.control.controller.misc;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.control.ControlException;
import spet.sbwo.control.channel.TrashChannel;
import spet.sbwo.control.controller.BaseController;
import spet.sbwo.control.controller.bo.IDeleteRestoreController;
import spet.sbwo.data.access.IDatabaseExecutorCreator;
import spet.sbwo.data.domain.EntityType;
import spet.sbwo.data.view.DeletedEntity;

public class TrashController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(TrashController.class);
	private static final String READ_ALL_ERROR = "Unable to read all deleted entities.";
	private static final String UNKNOWN_TYPE_ERROR = "Entity type {} is unknown.";

	protected final Map<EntityType, IDeleteRestoreController> controllers;

	public TrashController(IDatabaseExecutorCreator database, IDeleteRestoreController... controllers) {
		super(database, TrashChannel.class);
		this.controllers = new EnumMap<>(EntityType.class);
		for (IDeleteRestoreController controller : controllers) {
			this.controllers.put(controller.getEntityType(), controller);
		}
	}

	public void delete(List<TrashChannel> entities, String username) throws ControlException {
		for (TrashChannel entity : entities) {
			IDeleteRestoreController controller = controllers.get(entity.getTypeAsEnum());
			if (controller != null) {
				controller.delete(entity.getId(), true, username);
			} else {
				LOG.error(UNKNOWN_TYPE_ERROR, entity.getType());
			}
		}
	}

	public void restore(List<TrashChannel> entities, String username) throws ControlException {
		for (TrashChannel entity : entities) {
			IDeleteRestoreController controller = controllers.get(entity.getTypeAsEnum());
			if (controller != null) {
				controller.restore(entity.getId(), username);
			} else {
				LOG.error(UNKNOWN_TYPE_ERROR, entity.getType());
			}
		}
	}

	public void deleteAll(String username) throws ControlException {
		IAction<List<TrashChannel>> action = executor -> {
			List<TrashChannel> channels = new LinkedList<>();
			List<DeletedEntity> entities = executor.select(DeletedEntity.class).execute();
			for (DeletedEntity entity : entities) {
				TrashChannel channel = new TrashChannel();
				channel.setId(entity.getKey().getId());
				channel.setType(entity.getKey().getType().ordinal());
				channels.add(channel);
			}
			return channels;
		};
		delete(execute(READ_ALL_ERROR, action), username);
	}

}
