package br.com.monitoratec.farol.sql.repository.user;

import br.com.monitoratec.farol.sql.model.permission.Permission;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FarolUserRepository extends JpaRepository<FarolUser, Long> {

    Optional<FarolUser> findByEmailIgnoreCaseAndActiveTrue(String email);

    @Query("select u from FarolUser u join fetch u.permissionList where u.CPF = :cpf")
    Optional<FarolUser> findByCPFAndActiveTrue(String cpf);

    Optional<FarolUser> findByCPFAndActiveTrueAndEmail(String cpf, String email);

    @Query("select max(agentNumber) from FarolUser")
    Long maxAgentNumber();

    Optional<FarolUser> findByIdAndActiveTrue(Long entityID);

    @Query("select u from FarolUser u left join fetch u.permissionList where u.id = :id and u.active = true")
    Optional<FarolUser> findByIdWithPermissionsAndActiveTrue(Long id);

    @Query("select u.permissionList from FarolUser u where u.id = :id")
    List<Permission> findUserPermissions(Long id);

    @Query(value = "select u from FarolUser u " +
            "where (:filterByID is null or u.id = :filterByID) " +
            "and (:filterByName is null or lower(u.name) like concat('%', lower(CAST(:filterByName as string)), '%'))")
    Page<FarolUser> findByFiltersAllNullable(
            Long filterByID,
            String filterByName,
            Pageable pageable
    );
}
