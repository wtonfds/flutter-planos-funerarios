package br.com.monitoratec.farol.sql.model.user;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "frl_system_user", indexes = {
        @Index(name = "UserSystemUserEmail", columnList = "email"),
})
public abstract class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_user_system_user_sequence")
    @SequenceGenerator(name = "frl_user_system_user_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    protected String passwordHash;

    @Column
    private boolean temporaryPassword;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = false;

    @Column
    private String telephone;

    @OneToMany(mappedBy = "systemUser", cascade = CascadeType.REMOVE)
    private List<LoginSession> loginSessions;

    public SystemUser() {
    }

    public Long getId() {
        return id;
    }

    public List<LoginSession> getLoginSessions() {
        return loginSessions;
    }

    public void setLoginSessions(List<LoginSession> loginSessions) {
        this.loginSessions = loginSessions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(boolean temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUser that = (SystemUser) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
