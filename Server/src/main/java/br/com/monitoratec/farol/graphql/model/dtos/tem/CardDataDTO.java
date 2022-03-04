package br.com.monitoratec.farol.graphql.model.dtos.tem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardDataDTO {

    @JsonProperty("data_ult_status")
    private String dataUltStatus;

    @JsonProperty("codigo_d")
    private String codigoD;

    @JsonProperty("descricao_d")
    private String descricaoD;

    @JsonProperty("data_aquisicao")
    private String dataAquisicao;

    private String sexo;

    @JsonProperty("cnpj_cpf")
    private String cnpjCpf;

    @JsonProperty("numero_cartao")
    private String numeroCartao;

    private Integer status;

    public CardDataDTO(String dataUltStatus, String codigoD, String descricaoD, String dataAquisicao, String sexo,
                       String cnpjCpf, String numeroCartao, Integer status) {
        this.dataUltStatus = dataUltStatus;
        this.codigoD = codigoD;
        this.descricaoD = descricaoD;
        this.dataAquisicao = dataAquisicao;
        this.sexo = sexo;
        this.cnpjCpf = cnpjCpf;
        this.numeroCartao = numeroCartao;
        this.status = status;
    }

    // Empty constructor to be used because there is no TEM integration anymore.
    public CardDataDTO() {

    }

    public String getDataUltStatus() {
        return dataUltStatus;
    }

    public void setDataUltStatus(String dataUltStatus) {
        this.dataUltStatus = dataUltStatus;
    }

    public String getCodigoD() {
        return codigoD;
    }

    public void setCodigoD(String codigoD) {
        this.codigoD = codigoD;
    }

    public String getDescricaoD() {
        return descricaoD;
    }

    public void setDescricaoD(String descricaoD) {
        this.descricaoD = descricaoD;
    }

    public String getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(String dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
