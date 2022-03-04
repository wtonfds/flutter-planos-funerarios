package br.com.monitoratec.farol.graphql.model.input.common;

import br.com.monitoratec.farol.sql.model.location.Address;

public class AddressInput {
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String province;
    private String country;

    public Address toModel(Address address) {
        if(address == null){
            address = new Address();
        }
        address.setZipCode(this.zipCode);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setComplement(this.complement);
        address.setNeighborhood(this.neighborhood);
        address.setCity(this.city);
        address.setProvince(this.province);
        address.setCountry(this.country);

        return address;
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

}
