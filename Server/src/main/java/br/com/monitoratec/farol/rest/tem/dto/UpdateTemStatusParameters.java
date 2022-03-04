package br.com.monitoratec.farol.rest.tem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTemStatusParameters {
    private String cpf;

    @JsonProperty("CodOnix")
    private String codOnyx;

    @JsonProperty("NumeroCartao")
    private String numeroCartao;

    @JsonProperty("NovoStatus")
    private String novoStatus;

    private String tokenzeus;

    @JsonProperty("UserToken")
    private String userToken;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodOnyx() {
        return codOnyx;
    }

    public void setCodOnyx(String codOnyx) {
        this.codOnyx = codOnyx;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }

    public String getTokenzeus() {
        return tokenzeus;
    }

    public void setTokenzeus(String tokenzeus) {
        this.tokenzeus = tokenzeus;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
