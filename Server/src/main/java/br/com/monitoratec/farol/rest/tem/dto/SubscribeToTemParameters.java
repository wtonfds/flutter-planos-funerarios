package br.com.monitoratec.farol.rest.tem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscribeToTemParameters {
    private String cpfTitular;

    @JsonProperty("CodOnix")
    private String codOnix;

    @JsonProperty("Nome")
    private String nome;

    private String cpf;

    @JsonProperty("data_nascimento")
    private String dataNascimento;

    @JsonProperty("Sexo")
    private String sexo;

    @JsonProperty("NumeroCartao")
    private String numeroCartao;

    private String cnpj;

    @JsonProperty("Logradouro")
    private String logradouro;

    @JsonProperty("NumeroEndereco")
    private String numeroEndereco;

    @JsonProperty("Complemento")
    private String complemento;

    @JsonProperty("Bairro")
    private String bairro;

    @JsonProperty("Cidade")
    private String cidade;

    @JsonProperty("Estado")
    private String estado;

    @JsonProperty("CEP")
    private String cep;

    @JsonProperty("Telefone")
    private String telefone;

    private String numerodasorte;

    private String tokenzeus;

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public String getCodOnix() {
        return codOnix;
    }

    public void setCodOnix(String codOnix) {
        this.codOnix = codOnix;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNumerodasorte() {
        return numerodasorte;
    }

    public void setNumerodasorte(String numerodasorte) {
        this.numerodasorte = numerodasorte;
    }

    public String getTokenzeus() {
        return tokenzeus;
    }

    public void setTokenzeus(String tokenzeus) {
        this.tokenzeus = tokenzeus;
    }
}
