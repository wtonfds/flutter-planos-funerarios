package br.com.monitoratec.farol.sql.repository.generalParameterization;

import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralParameterizationRepository extends JpaRepository<GeneralParameterization, Long> {
}
