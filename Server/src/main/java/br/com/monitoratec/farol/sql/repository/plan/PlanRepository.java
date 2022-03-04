package br.com.monitoratec.farol.sql.repository.plan;

import br.com.monitoratec.farol.sql.model.plan.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("select u from Plan u " +
            "where (:filterByID is null or u.id = :filterByID) " +
            "and (:filterByName is null or lower(u.name) like concat('%', lower(CAST(:filterByName as string)), '%'))")
    Page<Plan> findByFiltersAllNullable(Long filterByID, String filterByName, Pageable pageable);

    Plan findByIdAndActiveTrue(Long id);
}
