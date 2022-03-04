package br.com.monitoratec.farol.service.materializedViews;

import br.com.monitoratec.farol.sql.repository.accredited.AccreditedRepository.AccreditedRepository;
import br.com.monitoratec.farol.sql.repository.log.LogClientHistoryRepository;
import br.com.monitoratec.farol.sql.repository.lotteryNumber.LotteryNumberRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterializedViewsService.class);

    private final ClientRepository clientRepository;
    private final AccreditedRepository accreditedRepository;
    private final LotteryNumberRepository lotteryNumberRepository;
    private final LogClientHistoryRepository logClientHistoryRepository;

    public MaterializedViewsService(ClientRepository clientRepository, AccreditedRepository accreditedRepository, LotteryNumberRepository lotteryNumberRepository, LogClientHistoryRepository logClientHistoryRepository) {
        this.clientRepository = clientRepository;
        this.accreditedRepository = accreditedRepository;
        this.lotteryNumberRepository = lotteryNumberRepository;
        this.logClientHistoryRepository = logClientHistoryRepository;
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void updateMaterializedViews() {
        LOGGER.info("Started refreshing mview_user_client");
        clientRepository.refreshUserClientMaterializedView();
        LOGGER.info("Started refreshing mview_accredited");
        accreditedRepository.refreshAccreditedMaterializedView();
        LOGGER.info("Started refreshing mview_new_user_client");
        clientRepository.refreshNewUserClientMaterializedView();
        LOGGER.info("Started refreshing mview_incomplete_user_client");
        clientRepository.refreshIncompleteUserClientMaterializedView();
        LOGGER.info("Started refreshing mview_lottery_numbers");
        lotteryNumberRepository.refreshLotteryNumbersMaterializedView();
        LOGGER.info("Started refreshing mview_lottery_numbers_winners");
        lotteryNumberRepository.refreshLotteryWinnersMaterializedView();
        LOGGER.info("Started refreshing mview_user_client_is_default");
        clientRepository.refreshClientIsDefaultMaterializedView();
        LOGGER.info("Started refreshing mview_client_history");
        logClientHistoryRepository.refreshClientHistoryMaterializedView();
    }
}
