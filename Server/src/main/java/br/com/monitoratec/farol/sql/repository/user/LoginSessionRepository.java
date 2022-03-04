package br.com.monitoratec.farol.sql.repository.user;

import br.com.monitoratec.farol.sql.model.user.LoginSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoginSessionRepository extends JpaRepository<LoginSession, Long> {
    @Query("SELECT s FROM LoginSession s JOIN FETCH s.systemUser WHERE s.authToken = :authToken")
    Optional<LoginSession> findByAuthToken(String authToken);

    @Query( "SELECT DISTINCT loginSession " +
            "FROM LoginSession loginSession " +
            "WHERE loginSession.validUntilMillis < :currentMillis")
    List<LoginSession> findExpiredSessions(@Param("currentMillis") Long currentMillis);
}
