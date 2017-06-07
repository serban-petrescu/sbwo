package spet.sbwo.api.service.base;

class ControlExceptionChannel {
    private String type = "CONTROL";
    private String error;
    private String entity;
    private DatabaseExceptionChannel cause;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public DatabaseExceptionChannel getCause() {
        return cause;
    }

    public void setCause(DatabaseExceptionChannel cause) {
        this.cause = cause;
    }

}
