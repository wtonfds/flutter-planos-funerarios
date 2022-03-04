package br.com.monitoratec.farol.sql.repository.log;

import br.com.monitoratec.farol.sql.model.log.LogClientHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LogClientHistoryRepository extends JpaRepository<LogClientHistory, Long> {

    @Transactional
    @Modifying
    @Query(value = "refresh materialized view mview_client_history", nativeQuery = true)
    void refreshClientHistoryMaterializedView();

}
