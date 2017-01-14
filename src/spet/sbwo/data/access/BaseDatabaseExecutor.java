package spet.sbwo.data.access;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spet.sbwo.data.DatabaseError;
import spet.sbwo.data.DatabaseException;

abstract class BaseDatabaseExecutor implements IDatabaseExecutor {
	private static final Logger LOG = LoggerFactory.getLogger(BaseDatabaseExecutor.class);

	protected EntityManager em;
	protected EntityTransaction tr;
	protected SimpleCommandBuilder simpleCommandBuilder;

	BaseDatabaseExecutor(EntityManager em) throws DatabaseException {
		this.em = em;
		this.tr = null;
		this.simpleCommandBuilder = new SimpleCommandBuilder();
		this.start();
	}

	@Override
	public void start() throws DatabaseException {
		if (this.tr == null) {
			this.tr = em.getTransaction();
			this.tr.begin();
		} else {
			throw new DatabaseException(DatabaseError.OTHER, "Transaction already started.");
		}
	}

	@Override
	public void commit() throws DatabaseException {
		this.commit(false);
	}

	@Override
	public void commit(boolean start) throws DatabaseException {
		if (this.tr != null) {
			try {
				this.tr.commit();
			} catch (RollbackException e) {
				LOG.warn("Transaction commit has failed.", e);
				throw new DatabaseException(e);
			}
		} else {
			throw new DatabaseException(DatabaseError.OTHER, "Attempted to commit without a transaction.");
		}
		this.tr = null;
		if (start) {
			this.start();
		}
	}

	@Override
	public void rollback() throws DatabaseException {
		this.rollback(false);
	}

	@Override
	public void rollback(boolean start) throws DatabaseException {
		if (this.tr != null) {
			this.tr.rollback();
		} else {
			throw new DatabaseException(DatabaseError.OTHER, "Attempted to rolback without a transaction.");
		}
		this.tr = null;
		if (start) {
			this.start();
		}
	}

	@Override
	public void close() {
		try {
			if (this.tr != null) {
				this.tr.rollback();
			}
			if (this.em != null) {
				this.em.close();
			}
		} catch (Exception e) {
			LOG.error("Error while closing executor.", e);
		}
	}

	@Override
	public <T> IQueryFacade<Long> count(Class<T> entity) {
		return new CountFacade<T>(entity);
	}

	@Override
	public <T> IQueryFacade<List<T>> select(Class<T> entity) {
		return new SelectFacade<T>(entity);
	}

	@Override
	public <T> IQueryFacade<T> selectSingle(Class<T> entity) {
		return new SelectSingleFacade<T>(entity);
	}

	@Override
	public <T> T find(Class<T> clazz, Object id) throws DatabaseException {
		try {
			return em.find(clazz, id);
		} catch (Exception e) {
			LOG.error("Unable to find a entity", e);
			throw new DatabaseException(e);
		}
	}

	protected static class Criteria {
		private String attribute;
		private WhereOperator operator;
		private Object value;

		public Criteria(String attribute, WhereOperator operator, Object value) {
			super();
			this.attribute = attribute;
			this.operator = operator;
			this.value = value;
		}

		public String getAttribute() {
			return attribute;
		}

		public WhereOperator getOperator() {
			return operator;
		}

		public Object getValue() {
			return value;
		}

	}

	protected class SelectFacade<T> extends QueryFacade<List<T>> {
		protected Class<T> fromClazz;

		public SelectFacade(Class<T> clazz) {
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
				return values(em, createQuery).getResultList();
			} catch (Exception e) {
				LOG.error("Error while executing a select.", e);
				throw new DatabaseException(e);
			}
		}

	}

	protected class SelectSingleFacade<T> extends QueryFacade<T> {
		protected Class<T> fromClazz;

		public SelectSingleFacade(Class<T> clazz) {
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
				List<T> results = values(em, createQuery).getResultList();
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

	protected class CountFacade<K> extends QueryFacade<Long> {
		protected Class<K> fromClazz;

		public CountFacade(Class<K> clazz) {
			super();
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
				return values(em, createQuery).getSingleResult();
			} catch (Exception e) {
				LOG.error("Error while executing a select.", e);
				throw new DatabaseException(e);
			}
		}

	}

	private static abstract class QueryFacade<T> implements IQueryFacade<T> {
		protected List<Criteria> criterias;

		public QueryFacade() {
			this.criterias = new LinkedList<>();
		}

		@Override
		public IQueryFacade<T> where(String attribute, WhereOperator operator, Object value) {
			this.criterias.add(new Criteria(attribute, operator, value));
			return this;
		}

		@SuppressWarnings("unchecked")
		protected <K> CriteriaQuery<K> parameters(EntityManager em, CriteriaQuery<K> query, Root<?> root) {
			if (!criterias.isEmpty()) {
				CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
				List<Predicate> predicates = new LinkedList<>();
				for (Criteria criteria : this.criterias) {
					if (criteria.getValue() != null) {
						ParameterExpression<?> parameter = criteriaBuilder.parameter(criteria.getValue().getClass(),
								criteria.getAttribute());
						switch (criteria.getOperator()) {
						case GE:
							predicates.add(criteriaBuilder.ge(root.get(criteria.getAttribute()),
									(Expression<? extends Number>) parameter));
							break;
						case GT:
							predicates.add(criteriaBuilder.gt(root.get(criteria.getAttribute()),
									(Expression<? extends Number>) parameter));
							break;
						case LE:
							predicates.add(criteriaBuilder.le(root.get(criteria.getAttribute()),
									(Expression<? extends Number>) parameter));
							break;
						case LT:
							predicates.add(criteriaBuilder.lt(root.get(criteria.getAttribute()),
									(Expression<? extends Number>) parameter));
							break;
						case NEQ:
							predicates.add(criteriaBuilder.notEqual(root.get(criteria.getAttribute()), parameter));
							break;
						case EQ:
						default:
							predicates.add(criteriaBuilder.equal(root.get(criteria.getAttribute()), parameter));
							break;
						}
					} else {
						if (criteria.getOperator() == WhereOperator.NEQ) {
							predicates.add(criteriaBuilder.isNotNull(root.get(criteria.getAttribute())));
						} else {
							predicates.add(criteriaBuilder.isNull(root.get(criteria.getAttribute())));
						}
					}
				}
				query.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
			}
			return query;
		}

		protected <K> TypedQuery<K> values(EntityManager em, TypedQuery<K> query) {
			for (Criteria criteria : this.criterias) {
				if (criteria.getValue() != null) {
					query.setParameter(criteria.getAttribute(), criteria.getValue());
				}
			}
			return query;
		}
	}

}
