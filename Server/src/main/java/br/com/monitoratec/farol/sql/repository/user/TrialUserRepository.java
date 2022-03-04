package br.com.monitoratec.farol.sql.repository.user;

import br.com.monitoratec.farol.sql.model.user.TrialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrialUserRepository extends JpaRepository<TrialUser, Long> {
}
