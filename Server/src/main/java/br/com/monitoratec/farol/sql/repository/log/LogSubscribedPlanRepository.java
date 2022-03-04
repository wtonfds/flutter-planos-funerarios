package br.com.monitoratec.farol.sql.repository.log;

import br.com.monitoratec.farol.sql.model.log.LogSubscribedPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogSubscribedPlanRepository extends JpaRepository<LogSubscribedPlan, Long> {

}
