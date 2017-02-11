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

public class SelectAttributeFacade<T> extends QueryFacade<List<T>> {
	private static final Logger LOG = LoggerFactory.getLogger(SelectAttributeFacade.class);
	private final EntityManager em;
	protected Class<?> entityClazz;
	protected Class<T> attributeClazz;
	protected String attribute;

	public SelectAttributeFacade(EntityManager em, Class<?> entityClazz, Class<T> attributeClazz, String attribute) {
		this.em = em;
		this.entityClazz = entityClazz;
		this.attributeClazz = attributeClazz;
		this.attribute = attribute;
	}

	@Override
	public List<T> execute() throws DatabaseException {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<T> query = criteriaBuilder.createQuery(attributeClazz);
			Root<?> root = query.from(entityClazz);
			parameters(em, query.select(root.get(attribute)), root);
			TypedQuery<T> createQuery = em.createQuery(query);
			return values(createQuery).getResultList();
		} catch (Exception e) {
			LOG.error("Error while executing a select (attribute).", e);
			throw new DatabaseException(e);
		}
	}

}