package br.com.monitoratec.farol.graphql.model.input.product;

import br.com.monitoratec.farol.graphql.customTypes.Date;

public class ProductInput {
    private Long accreditedId;
    private String discount;
    private Date dueDate;
    private String name;

    public ProductInput(Long accreditedId, Long createdById, String discount, Date dueDate, String name) {
        this.accreditedId = accreditedId;
        this.discount = discount;
        this.dueDate = dueDate;
        this.name = name;
    }

    public Long getAccreditedId() {
        return accreditedId;
    }

    public void setAccreditedId(Long accreditedId) {
        this.accreditedId = accreditedId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
