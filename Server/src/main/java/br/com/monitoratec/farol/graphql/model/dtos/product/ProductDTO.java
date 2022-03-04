package br.com.monitoratec.farol.graphql.model.dtos.product;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.model.dtos.accredited.AccreditedDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.FarolUserBasicDTO;
import br.com.monitoratec.farol.sql.model.product.Product;

public class ProductDTO {
    private Long id;
    private String name;
    private String discount;
    private Date dueDate;
    private AccreditedDTO accreditedDTO;
    private FarolUserBasicDTO farolUserBasicDTO;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.discount = product.getDiscount();
        if (product.getDueDate() != null) {
            this.dueDate = new Date(product.getDueDate());
        }
        this.accreditedDTO = new AccreditedDTO(product.getAccredited());
        this.farolUserBasicDTO = new FarolUserBasicDTO(product.getCreatedBy());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AccreditedDTO getAccreditedDTO() {
        return accreditedDTO;
    }

    public void setAccreditedDTO(AccreditedDTO accreditedDTO) {
        this.accreditedDTO = accreditedDTO;
    }

    public FarolUserBasicDTO getFarolUserBasicDTO() {
        return farolUserBasicDTO;
    }

    public void setFarolUserBasicDTO(FarolUserBasicDTO farolUserBasicDTO) {
        this.farolUserBasicDTO = farolUserBasicDTO;
    }
}
