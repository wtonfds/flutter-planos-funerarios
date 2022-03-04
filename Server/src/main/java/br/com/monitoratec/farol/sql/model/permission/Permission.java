package br.com.monitoratec.farol.sql.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "frl_farol_user_permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_farol_user_permission_sequence")
    @SequenceGenerator(name = "frl_farol_user_permission_sequence", allocationSize = 1)
    private Long id;

    private String name;

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
}
