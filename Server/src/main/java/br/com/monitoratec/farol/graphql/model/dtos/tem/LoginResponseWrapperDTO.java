package br.com.monitoratec.farol.graphql.model.dtos.tem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseWrapperDTO {
    @JsonProperty("UUID")
    private String uuid;

    private String message;

    @JsonProperty("data")
    private TokenDTO data;

    public LoginResponseWrapperDTO(String uuid, String message, TokenDTO data) {
        this.uuid = uuid;
        this.message = message;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenDTO getData() {
        return data;
    }

    public void setData(TokenDTO data) {
        this.data = data;
    }
}
