package spet.sbwo.control;

import spet.sbwo.data.DatabaseException;

public class ControlException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final ControlError error;
    private final Class<?> channel;
    private final DatabaseException cause;

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
            case INVALID_VALUE:
            case CONSTRAINT_VIOLATED:
                this.error = ControlError.INVALID_PROPERTY_VALUE;
                break;
            case DIVISION_BY_ZERO:
            case CONNECTION_ERROR:
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

    @Override
    public DatabaseException getCause() {
        return cause;
    }

}
