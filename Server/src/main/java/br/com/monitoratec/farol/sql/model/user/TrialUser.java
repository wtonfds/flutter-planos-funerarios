package br.com.monitoratec.farol.sql.model.user;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "frl_trial_user")
public class TrialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_trial_user_sequence")
    @SequenceGenerator(name = "frl_trial_user_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
