package spet.sbwo.data.query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseException;

public class SelectSingleFacade<T> extends QueryFacade<T> {
	private static final Logger LOG = LoggerFactory.getLogger(SelectSingleFacade.class);
	protected final EntityManager em;
	protected final Class<T> fromClazz;

	public SelectSingleFacade(EntityManager em, Class<T> clazz) {
		this.em = em;
		this.fromClazz = clazz;
	}

	@Override
	public T execute() throws DatabaseException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<T> query = criteriaBuilder.createQuery(fromClazz);
			Root<T> root = query.from(fromClazz);
			parameters(em, query.select(root), root);
			TypedQuery<T> createQuery = em.createQuery(query);
			List<T> results = values(createQuery).getResultList();
			if (results.isEmpty()) {
				return null;
			} else {
				return results.get(0);
			}
		} catch (Exception e) {
			LOG.error("Error while executing a single select.", e);
			throw new DatabaseException(e);
		}
	}

}