package br.com.monitoratec.farol.sql.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "frl_company_info_table")
public class Company {

    @Column
    private String name;

    @Id
    @Column
    private String cnpj;

    @Column
    private String municipalRegistry;

    @Column
    private String type;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private String webSite;

    @Column
    private String zipCode;

    @Column
    private String street;

    @Column
    private String number;

    @Column
    private String complement;

    @Column
    private String neighborhood;

    @Column
    private String city;

    @Column
    private String province;

    @Column
    private String country;

    @Column
    private String cityCode;

    @Column
    private String nfDiscrimination;

    @Column
    private String nfItemCode;

    @Column
    private String nfMunicipalTaxCode;

    @Column
    private Double nfShare;

    @Column
    private Long rpsIdentification;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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

    public Double getNfShare() {
        return nfShare;
    }

    public void setNfShare(Double nfShare) {
        this.nfShare = nfShare;
    }

    public Long getRpsIdentification() {
        return rpsIdentification;
    }

    public void setRpsIdentification(Long rpsIdentification) {
        this.rpsIdentification = rpsIdentification;
    }

    public String getAddress() {
        return this.street + ", N° " + this.number + ". " + this.neighborhood + ". CEP: " + this.zipCode + ". " + this.city + " / " + this.province;
    }
}
