package spet.sbwo.data.query;

class Criteria {
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