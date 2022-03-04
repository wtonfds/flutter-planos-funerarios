package br.com.monitoratec.farol.graphql.model.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GetNetPaymentSlipBodyDTO {
    @JsonProperty("document_number")
    private String documentNumber;

    @JsonProperty("expiration_date")
    private String expirationDate;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public GetNetPaymentSlipBodyDTO(String documentNumber, LocalDate expirationDate) {
        this.documentNumber = documentNumber;
        this.expirationDate = expirationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public GetNetPaymentSlipBodyDTO(){}

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
