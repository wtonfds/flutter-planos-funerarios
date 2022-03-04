package br.com.monitoratec.farol.graphql.resolvers.query.price;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.price.CommonResponseWithPlanPriceTable;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.price.PlanPriceTableService;
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
public class FindPlanPriceTableImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindPlanPriceTableImpl.class);

    private final PlanPriceTableService service;

    public FindPlanPriceTableImpl(PlanPriceTableService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithPlanPriceTable> findPlanPriceTable(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPlanPriceTable> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Optional<PlanPriceTableDTO> optional = service.findById(id);
                    if (optional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.PLAN_PRICE_TABLE_NOT_FOUND);
                    }

                    final CommonResponseWithPlanPriceTable response = new CommonResponseWithPlanPriceTable(StatusCodes.Success.PriceTable.GOT_PRICE_TABLE, optional.get());
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
