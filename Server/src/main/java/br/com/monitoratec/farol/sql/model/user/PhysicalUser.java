package br.com.monitoratec.farol.sql.model.user;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "frl_physical_user")
public abstract class PhysicalUser extends SystemUser {

    @Column(nullable = false)
    private String CPF;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
