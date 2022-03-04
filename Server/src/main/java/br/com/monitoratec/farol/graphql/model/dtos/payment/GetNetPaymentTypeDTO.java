package br.com.monitoratec.farol.graphql.model.dtos.payment;

public class GetNetPaymentTypeDTO {
    private GetNetCreditDTO credit;

    public GetNetPaymentTypeDTO(GetNetCreditDTO credit) {
        this.credit = credit;
    }

    public GetNetCreditDTO getCredit() {
        return credit;
    }

    public void setCredit(GetNetCreditDTO credit) {
        this.credit = credit;
    }
}
