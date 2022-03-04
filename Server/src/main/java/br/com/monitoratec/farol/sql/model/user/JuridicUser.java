package br.com.monitoratec.farol.sql.model.user;

import br.com.monitoratec.farol.sql.model.location.Address;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "frl_juridic_user")
public abstract class JuridicUser extends SystemUser {

    @Column(nullable = false, unique = true)
    private String CNPJ;

    @OneToOne
    private Address address;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
