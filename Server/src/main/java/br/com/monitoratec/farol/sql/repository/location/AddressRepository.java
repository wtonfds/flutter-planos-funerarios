package br.com.monitoratec.farol.sql.repository.location;

import br.com.monitoratec.farol.sql.model.location.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
