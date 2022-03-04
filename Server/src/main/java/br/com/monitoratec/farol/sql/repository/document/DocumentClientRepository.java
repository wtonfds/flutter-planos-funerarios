package br.com.monitoratec.farol.sql.repository.document;

import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.model.user.DocumentsClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentClientRepository extends JpaRepository<DocumentsClient, Client> {

}
