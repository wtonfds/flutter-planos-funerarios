package br.com.monitoratec.farol.graphql.resolvers.query.price;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.price.UpgradePriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.price.CommonResponseWithUpgradePriceTable;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.price.UpgradePriceTableService;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class FindUpgradePriceTableImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindPlanPriceTableImpl.class);

    private final UpgradePriceTableService service;

    public FindUpgradePriceTableImpl(UpgradePriceTableService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithUpgradePriceTable> findUpgradePriceTable(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithUpgradePriceTable> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Optional<UpgradePriceTableDTO> optional = service.findById(id);
                    if (optional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.UPGRADE_PRICE_TABLE_NOT_FOUND);
                    }

                    final CommonResponseWithUpgradePriceTable response = new CommonResponseWithUpgradePriceTable(StatusCodes.Success.PriceTable.GOT_PRICE_TABLE, optional.get());
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
