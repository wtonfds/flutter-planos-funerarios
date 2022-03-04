package br.com.monitoratec.farol.graphql.model.dtos.company;

import br.com.monitoratec.farol.sql.model.company.Company;

public class CompanyDTO {
    private String name;
    private String cnpj;
    private String municipalRegistry;
    private String type;
    private String telephone;
    private String webSite;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String province;
    private String country;
    private String nfDiscrimination;
    private String nfItemCode;
    private String nfMunicipalTaxCode;

    public CompanyDTO(Company company) {
        this.name = company.getName();
        this.cnpj = company.getCnpj();
        this.municipalRegistry = company.getMunicipalRegistry();
        this.type = company.getType();
        this.telephone = company.getTelephone();
        this.webSite = company.getWebSite();
        this.zipCode = company.getZipCode();
        this.street = company.getStreet();
        this.number = company.getNumber();
        this.complement = company.getComplement();
        this.neighborhood = company.getNeighborhood();
        this.city = company.getCity();
        this.province = company.getProvince();
        this.country = company.getCountry();
        this.nfDiscrimination = company.getNfDiscrimination();
        this.nfItemCode = company.getNfItemCode();
        this.nfMunicipalTaxCode = company.getNfMunicipalTaxCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getMunicipalRegistry() {
        return municipalRegistry;
    }

    public void setMunicipalRegistry(String municipalRegistry) {
        this.municipalRegistry = municipalRegistry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNfDiscrimination() {
        return nfDiscrimination;
    }

    public void setNfDiscrimination(String nfDiscrimination) {
        this.nfDiscrimination = nfDiscrimination;
    }

    public String getNfItemCode() {
        return nfItemCode;
    }

    public void setNfItemCode(String nfItemCode) {
        this.nfItemCode = nfItemCode;
    }

    public String getNfMunicipalTaxCode() {
        return nfMunicipalTaxCode;
    }

    public void setNfMunicipalTaxCode(String nfMunicipalTaxCode) {
        this.nfMunicipalTaxCode = nfMunicipalTaxCode;
    }
}
