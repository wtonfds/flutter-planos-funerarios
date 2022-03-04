package br.com.monitoratec.farol.sql.model.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "frl_user_login_session", indexes = {
        @Index(name = "authTokenIndex", columnList = "authToken"),
        @Index(name = "validUntilMillisIndex", columnList = "validUntilMillis"),
})
public class LoginSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_user_login_session_sequence")
    @SequenceGenerator(name = "frl_user_login_session_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String authToken;

    @Column(nullable = false, unique = false)
    private Long validUntilMillis;

    @Column(length = 511, nullable = true, unique = false)
    private String notificationToken;

    @JoinColumn(name = "system_user_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SystemUser systemUser;

    public LoginSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Long getValidUntilMillis() {
        return validUntilMillis;
    }

    public void setValidUntilMillis(Long validUntilMillis) {
        this.validUntilMillis = validUntilMillis;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginSession that = (LoginSession) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LoginSession{" +
                "id=" + id +
                ", authToken='" + authToken + '\'' +
                ", validUntilMillis=" + validUntilMillis +
                ", notificationToken='" + notificationToken + '\'' +
                ", systemUser=" + systemUser +
                '}';
    }
}
