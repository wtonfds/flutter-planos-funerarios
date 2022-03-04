package br.com.monitoratec.farol.sql.repository.campaign;

import br.com.monitoratec.farol.sql.model.campaign.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Query("select c from Campaign c " +
            "where c.active = true " +
            "and (:filterByID is null or c.id = :filterByID) " +
            "and (:filterByName is null or lower(c.name) like concat('%', lower(CAST(:filterByName as string)), '%'))")
    Page<Campaign> findByFiltersAllNullable(Long filterByID,
                                            String filterByName,
                                            Pageable pageable);

    Optional<Campaign> findByIdAndActiveTrue(Long id);
}
