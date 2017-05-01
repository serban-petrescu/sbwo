package spet.sbwo.control;

import spet.sbwo.data.DatabaseException;

public class ControlException extends Exception {
	private static final long serialVersionUID = 1L;
	private ControlError error;
	private Class<?> channel;
	private DatabaseException cause;

	public ControlException(DatabaseException e) {
		this.error = ControlError.PERSISTENCE_ERROR;
		this.cause = e;
		this.channel = null;
	}

	public ControlException(DatabaseException e, Class<?> channel) {
		this.channel = channel;
		this.cause = e;
		switch (e.getError()) {
		case AUTHORIZATION_ERROR:
			this.error = ControlError.NOT_AUTHORIZED;
			break;
		case CONCURRENT_ACCESS:
			this.error = ControlError.OBJECT_LOCKED;
			break;
		case CONNECTION_ERROR:
			this.error = ControlError.PERSISTENCE_ERROR;
			break;
		case CONSTRAINT_VIOLATED:
			this.error = ControlError.INVALID_PROPERTY_VALUE;
			break;
		case DIVISION_BY_ZERO:
			this.error = ControlError.PERSISTENCE_ERROR;
			break;
		case INVALID_VALUE:
			this.error = ControlError.INVALID_PROPERTY_VALUE;
			break;
		case OTHER:
			this.error = ControlError.PERSISTENCE_ERROR;
			break;
		case STRUCTURE_ERROR:
			this.error = ControlError.PERSISTENCE_ERROR;
			break;
		default:
			this.error = ControlError.PERSISTENCE_ERROR;
			break;

		}
	}

	public ControlException(ControlError error, Class<?> channel) {
		super();
		this.error = error;
		this.channel = channel;
		this.cause = null;
	}

	public ControlError getError() {
		return error;
	}

	public Class<?> getChannel() {
		return channel;
	}

	public DatabaseException getCause() {
		return cause;
	}

}
