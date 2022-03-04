package br.com.monitoratec.farol.service.log;

import br.com.monitoratec.farol.graphql.model.dtos.log.ClientActionTypeDTO;
import br.com.monitoratec.farol.sql.model.log.LogClientHistory;
import br.com.monitoratec.farol.sql.repository.log.LogClientHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    private LogClientHistoryRepository logClientHistoryRepository;

    public LogService(LogClientHistoryRepository logClientHistoryRepository) {
        this.logClientHistoryRepository = logClientHistoryRepository;
    }

    public void logClientHistory(ClientActionTypeDTO type, String description, String cpf, String name) {
        LogClientHistory log = new LogClientHistory();
        log.setDate(LocalDateTime.now());
        log.setType(type.name());
        log.setDescription(description);
        log.setClientCpf(cpf);
        log.setClientName(name);
        logClientHistoryRepository.save(log);
    }
}
