package spet.sbwo.data.access;

import spet.sbwo.data.base.BaseEntity;

class SimpleCommandBuilder {

	public <T extends BaseEntity> IUpdateCommand update(T entity) {
		return em -> em.merge(entity);
	}

	public <T extends BaseEntity> IUpdateCommand delete(T entity) {
		return em -> em.remove(entity);
	}

	public <T extends BaseEntity> IUpdateCommand create(T entity) {
		return em -> em.persist(entity);
	}

}
