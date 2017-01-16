package spet.sbwo.data.access;

class SimpleCommandBuilder {

	public <T> IUpdateCommand update(T entity) {
		return em -> em.merge(entity);
	}

	public <T> IUpdateCommand delete(T entity) {
		return em -> em.remove(entity);
	}

	public <T> IUpdateCommand create(T entity) {
		return em -> em.persist(entity);
	}

}
