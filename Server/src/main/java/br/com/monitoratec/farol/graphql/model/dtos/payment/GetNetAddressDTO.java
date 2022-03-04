package br.com.monitoratec.farol.graphql.model.dtos.payment;

import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import br.com.monitoratec.farol.graphql.model.input.common.AddressInput;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetAddressDTO {
    private String street;
    private String number;
    private String district;
    private String city;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;

    public GetNetAddressDTO(AddressDTO addressDTO) {
        this.street = addressDTO.getStreet();
        this.number = addressDTO.getNumber();
        this.district = addressDTO.getNeighborhood();
        this.city = addressDTO.getCity();
        this.state = addressDTO.getProvince();
        this.postalCode = addressDTO.getZipCode();
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
