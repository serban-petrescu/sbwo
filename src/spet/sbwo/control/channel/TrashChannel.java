package spet.sbwo.control.channel;

import spet.sbwo.data.domain.EntityType;

public class TrashChannel {
	private int type;
	private int id;

	public EntityType getTypeAsEnum() {
		if (type >= 0 && type < EntityType.values().length) {
			return EntityType.values()[type];
		} else {
			return null;
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
