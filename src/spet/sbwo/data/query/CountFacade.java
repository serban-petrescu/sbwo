package spet.sbwo.data.query;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseException;

public class CountFacade<K> extends QueryFacade<Long> {
	private static final Logger LOG = LoggerFactory.getLogger(CountFacade.class);
	protected final EntityManager em;
	protected final Class<K> fromClazz;

	public CountFacade(EntityManager em, Class<K> clazz) {
		super();
		this.em = em;
		this.fromClazz = clazz;
	}

	@Override
	public Long execute() throws DatabaseException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
			Root<K> root = query.from(fromClazz);
			parameters(em, query.select(criteriaBuilder.count(root)), root);
			TypedQuery<Long> createQuery = em.createQuery(query);
			return values(createQuery).getSingleResult();
		} catch (Exception e) {
			LOG.error("Error while executing a select.", e);
			throw new DatabaseException(e);
		}
	}

}