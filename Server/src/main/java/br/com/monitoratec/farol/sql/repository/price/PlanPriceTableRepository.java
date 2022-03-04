package br.com.monitoratec.farol.sql.repository.price;

import br.com.monitoratec.farol.sql.model.price.PlanPriceTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlanPriceTableRepository extends JpaRepository<PlanPriceTable, Long> {
    @Query("select t from PlanPriceTable t " +
            "where (:filterByID is null or t.id = :filterByID) " +
            "and (:filterByName is null or lower(t.name) like concat('%', lower(CAST(:filterByName as string)), '%'))")
    Page<PlanPriceTable> findByFiltersAllNullable(Long filterByID,
                                              String filterByName,
                                              Pageable pageable);

    @Query("select t from PlanPriceTable t left join fetch t.ageRanges where t.id = :id")
    Optional<PlanPriceTable> findByIdWithAgeRanges(Long id);

    Optional<PlanPriceTable> findAllById(Long id);
}
