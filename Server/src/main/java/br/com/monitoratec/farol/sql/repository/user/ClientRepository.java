package br.com.monitoratec.farol.sql.repository.user;

import br.com.monitoratec.farol.sql.model.push.Preferences;
import br.com.monitoratec.farol.sql.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmailIgnoreCaseAndActiveTrue(String email);

    Optional<Client> findByCPFAndActiveTrue(String cpf);

    Optional<Client> findByCPFAndActiveFalse(String cpf);

    Optional<Client> findByCPF(String cpf);

    Optional<Client> findByIdAndActiveTrue(Long id);

    @Query("select c from Client c where c.id = :id and c.oneSignalPlayerId is not null and c.active = true")
    Optional<Client> findByIdAndActiveTrueAndOneSignalPlayerIdNotNull(Long id);

    @Query("select c from Client c where c.active = true and c.holder is null")
    List<Client> findAllHolders();

    List<Client> findAllByHolder(Client holder);

    List<Client> findAllByHolderAndAddedAfterAnticipationIsTrue(Client holder);

    @Query("select c from Client c where c.birthDay = :date and c.clientType = 'CHILD' and c.holder is not null and c.deleted = false")
    List<Client> findBirthDayChildren(LocalDate date);

    @Query("select c.oneSignalPreferenceList from Client c where c.id = :id")
    List<Preferences> findOneSignalPreferences(Long id);

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_user_client", nativeQuery = true)
    void refreshUserClientMaterializedView();

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_new_user_client", nativeQuery = true)
    void refreshNewUserClientMaterializedView();

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_incomplete_user_client", nativeQuery = true)
    void refreshIncompleteUserClientMaterializedView();

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_user_client_is_default", nativeQuery = true)
    void refreshClientIsDefaultMaterializedView();
}
