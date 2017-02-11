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

public class SelectFacade<T> extends QueryFacade<List<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(SelectFacade.class);
	private final EntityManager em;
	protected Class<T> fromClazz;

	public SelectFacade(EntityManager em, Class<T> clazz) {
		this.em = em;
		this.fromClazz = clazz;
	}

	@Override
	public List<T> execute() throws DatabaseException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<T> query = criteriaBuilder.createQuery(fromClazz);
			Root<T> root = query.from(fromClazz);
			parameters(em, query.select(root), root);
			TypedQuery<T> createQuery = em.createQuery(query);
			return values(createQuery).getResultList();
		} catch (Exception e) {
			LOG.error("Error while executing a select.", e);
			throw new DatabaseException(e);
		}
	}

}