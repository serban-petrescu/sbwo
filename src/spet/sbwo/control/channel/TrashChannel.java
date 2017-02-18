package spet.sbwo.control.channel;

import spet.sbwo.data.domain.EntityType;

public class TrashChannel {
	private EntityType type;
	private int id;

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
