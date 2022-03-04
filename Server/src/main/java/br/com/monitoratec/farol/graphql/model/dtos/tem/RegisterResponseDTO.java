package br.com.monitoratec.farol.graphql.model.dtos.tem;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterResponseDTO {

    @JsonProperty("enviaSMS")
    private String enviaSMS;

    @JsonProperty("UserToken")
    private String userToken;

    @JsonProperty("id_produto_painel")
    private Integer idProdutoPainel;

    @JsonProperty("tipo_produto")
    private Integer tipoProduto;

    @JsonProperty("NumeroCartao")
    private String numeroCartao;

    @JsonProperty("produto_id")
    private Integer produtoId;

    @JsonProperty("cliente_produto_id")
    private Integer clienteProdutoId;

    private String statusCartao;

    @JsonProperty("TipoCliente")
    private String tipoCliente;

    private Integer status;

    private String message;

    public RegisterResponseDTO(String enviaSMS, String userToken, Integer idProdutoPainel, Integer tipoProduto,
                               String numeroCartao, Integer produtoId, Integer clienteProdutoId, String statusCartao, String tipoCliente,
                               Integer status, String message) {
        this.enviaSMS = enviaSMS;
        this.userToken = userToken;
        this.idProdutoPainel = idProdutoPainel;
        this.tipoProduto = tipoProduto;
        this.numeroCartao = numeroCartao;
        this.produtoId = produtoId;
        this.clienteProdutoId = clienteProdutoId;
        this.statusCartao = statusCartao;
        this.tipoCliente = tipoCliente;
        this.status = status;
        this.message = message;
    }

    public String getEnviaSMS() {
        return enviaSMS;
    }

    public void setEnviaSMS(String enviaSMS) {
        this.enviaSMS = enviaSMS;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Integer getIdProdutoPainel() {
        return idProdutoPainel;
    }

    public void setIdProdutoPainel(Integer idProdutoPainel) {
        this.idProdutoPainel = idProdutoPainel;
    }

    public Integer getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(Integer tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public Integer getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Integer produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getClienteProdutoId() {
        return clienteProdutoId;
    }

    public void setClienteProdutoId(Integer clienteProdutoId) {
        this.clienteProdutoId = clienteProdutoId;
    }

    public String getStatusCartao() {
        return statusCartao;
    }

    public void setStatusCartao(String statusCartao) {
        this.statusCartao = statusCartao;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
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
}
