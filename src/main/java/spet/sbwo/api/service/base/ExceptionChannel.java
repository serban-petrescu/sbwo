package spet.sbwo.api.service.base;

class ExceptionChannel {
    private String type = "UNKNOWN";
    private String details;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
