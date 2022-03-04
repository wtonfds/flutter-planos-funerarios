package br.com.monitoratec.farol.sql.repository.company;

import br.com.monitoratec.farol.sql.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c")
    Company getCompany();
}
