package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetNetCustomerDTO {
    private String name;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("document_type")
    private String documentType;
    @JsonProperty("document_number")
    private String documentNumber;
    @JsonProperty("billing_address")
    private  GetNetAddressDTO billingAddress;

    public GetNetCustomerDTO(String name, String customerId, String documentType, String documentNumber, GetNetAddressDTO billingAddress) {
        this.name = name;
        this.customerId = customerId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.billingAddress = billingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public GetNetAddressDTO getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(GetNetAddressDTO billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
