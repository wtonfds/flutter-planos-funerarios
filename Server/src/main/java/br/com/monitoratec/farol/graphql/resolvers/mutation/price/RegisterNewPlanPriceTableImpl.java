package br.com.monitoratec.farol.graphql.resolvers.mutation.price;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.input.price.PlanPriceTableInput;
import br.com.monitoratec.farol.graphql.model.responses.price.CommonResponseWithPlanPriceTable;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.price.PlanPriceTableService;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class RegisterNewPlanPriceTableImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewPlanPriceTableImpl.class);

    private final PlanPriceTableService service;

    public RegisterNewPlanPriceTableImpl(PlanPriceTableService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithPlanPriceTable> registerNewPlanPriceTable(PlanPriceTableInput input, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPlanPriceTable> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final PlanPriceTableDTO dto = service.create(input);

                    final CommonResponseWithPlanPriceTable response = new CommonResponseWithPlanPriceTable(StatusCodes.Success.PriceTable.REGISTERED_NEW_PRICE_TABLE, dto);
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
