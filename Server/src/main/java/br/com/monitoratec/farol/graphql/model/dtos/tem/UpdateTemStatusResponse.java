package br.com.monitoratec.farol.graphql.model.dtos.tem;

public class UpdateTemStatusResponse {

    private Integer status;

    private String message;

    private String error;

    public UpdateTemStatusResponse(Integer status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
