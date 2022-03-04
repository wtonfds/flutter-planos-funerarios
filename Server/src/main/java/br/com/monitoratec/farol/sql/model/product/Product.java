package br.com.monitoratec.farol.sql.model.product;

import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import br.com.monitoratec.farol.sql.model.user.FarolUser;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "frl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_accredited_sequence")
    @SequenceGenerator(name = "frl_accredited_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255)
    private String discount;

    @Column
    private LocalDate dueDate;

    @ManyToOne
    private Accredited accredited;

    @ManyToOne
    private FarolUser createdBy;

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Accredited getAccredited() {
        return accredited;
    }

    public void setAccredited(Accredited accredited) {
        this.accredited = accredited;
    }

    public FarolUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(FarolUser createdBy) {
        this.createdBy = createdBy;
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
