package spet.sbwo.data.query;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class QueryFacade<T> implements IQueryFacade<T> {
	protected List<Criteria> criterias;

	public QueryFacade() {
		this.criterias = new LinkedList<>();
	}

	@Override
	public IQueryFacade<T> where(String attribute, WhereOperator operator, Object value) {
		this.criterias.add(new Criteria(attribute, operator, value));
		return this;
	}

	protected <K> CriteriaQuery<K> parameters(EntityManager em, CriteriaQuery<K> query, Root<?> root) {
		if (!criterias.isEmpty()) {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			List<Predicate> predicates = new LinkedList<>();
			for (Criteria criteria : this.criterias) {
				predicates.add(buildSinglePredicte(criteriaBuilder, criteria, root));
			}
			query.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	protected Predicate buildSinglePredicte(CriteriaBuilder criteriaBuilder, Criteria criteria, Root<?> root) {
		if (criteria.getValue() != null) {
			ParameterExpression<?> parameter = criteriaBuilder.parameter(criteria.getValue().getClass(),
					criteria.getAttribute());
			switch (criteria.getOperator()) {
			case GE:
				return criteriaBuilder.ge(root.get(criteria.getAttribute()),
						(Expression<? extends Number>) parameter);
			case GT:
				return criteriaBuilder.gt(root.get(criteria.getAttribute()),
						(Expression<? extends Number>) parameter);
			case LE:
				return criteriaBuilder.le(root.get(criteria.getAttribute()),
						(Expression<? extends Number>) parameter);
			case LT:
				return criteriaBuilder.lt(root.get(criteria.getAttribute()),
						(Expression<? extends Number>) parameter);
			case NEQ:
				return criteriaBuilder.notEqual(root.get(criteria.getAttribute()), parameter);
			case EQ:
			default:
				return criteriaBuilder.equal(root.get(criteria.getAttribute()), parameter);
			}
		} else {
			if (criteria.getOperator() == WhereOperator.NEQ) {
				return criteriaBuilder.isNotNull(root.get(criteria.getAttribute()));
			} else {
				return criteriaBuilder.isNull(root.get(criteria.getAttribute()));
			}
		}
	}

	protected <K> TypedQuery<K> values(TypedQuery<K> query) {
		for (Criteria criteria : this.criterias) {
			if (criteria.getValue() != null) {
				query.setParameter(criteria.getAttribute(), criteria.getValue());
			}
		}
		return query;
	}
}