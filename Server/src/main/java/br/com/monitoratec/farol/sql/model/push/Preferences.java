package br.com.monitoratec.farol.sql.model.push;

import javax.persistence.*;

@Entity
@Table(name = "frl_user_client_one_signal_preference")
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_user_client_one_signal_preference_sequence")
    @SequenceGenerator(name = "frl_user_client_one_signal_preference_sequence", allocationSize = 1)
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