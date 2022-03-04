package br.com.monitoratec.farol.sql.repository.accredited.AccreditedRepository;

import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccreditedRepository extends JpaRepository<Accredited, Long> {
    Optional<Accredited> findByEmailIgnoreCaseAndActiveTrue(String email);

    @Query("select u from Accredited u " +
            "where (:filterByID is null or u.id = :filterByID) " +
            "and (:filterByName is null or lower(u.name) like concat('%', lower(CAST(:filterByName as string)), '%'))")
    Page<Accredited> findByFiltersAllNullable(
            Long filterByID,
            String filterByName,
            Pageable pageable
    );

    Optional<Accredited> findByCNPJAndActiveTrue(String CNPJ);

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_accredited", nativeQuery = true)
    void refreshAccreditedMaterializedView();
}
