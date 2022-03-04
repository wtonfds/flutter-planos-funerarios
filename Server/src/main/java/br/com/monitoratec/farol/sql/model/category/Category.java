package br.com.monitoratec.farol.sql.model.category;

import javax.persistence.*;

@Entity
@Table(name = "frl_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_campaign_sequence")
    @SequenceGenerator(name = "frl_campaign_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
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
