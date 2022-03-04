package br.com.monitoratec.farol.graphql.model.dtos.tem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDTO {
    private String id;

    @JsonProperty("apiKey")
    private String apiKey;

    private String token;

    public TokenDTO(String id, String apiKey, String token) {
        this.id = id;
        this.apiKey = apiKey;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
