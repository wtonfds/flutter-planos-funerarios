package br.com.monitoratec.farol.sql.repository.user;

import br.com.monitoratec.farol.sql.model.user.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByIdAndActiveTrue(Long id);
}
