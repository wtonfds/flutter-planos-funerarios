package br.com.monitoratec.farol.rest.tem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemGetAllCardsParameters {
    private String cnpj;

    @JsonProperty("CodOnix")
    private String codOnyx;

    private String tokenzeus;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCodOnyx() {
        return codOnyx;
    }

    public void setCodOnyx(String codOnyx) {
        this.codOnyx = codOnyx;
    }

    public String getTokenzeus() {
        return tokenzeus;
    }

    public void setTokenzeus(String tokenzeus) {
        this.tokenzeus = tokenzeus;
    }
}
