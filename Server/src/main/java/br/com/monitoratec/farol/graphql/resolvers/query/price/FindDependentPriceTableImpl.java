package br.com.monitoratec.farol.graphql.resolvers.query.price;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.price.DependentPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.responses.price.CommonResponseWithDependentPriceTable;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.price.DependentPriceTableService;
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

public class FindDependentPriceTableImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindDependentPriceTableImpl.class);

    private final DependentPriceTableService service;

    public FindDependentPriceTableImpl(DependentPriceTableService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithDependentPriceTable> findDependentPriceTable(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithDependentPriceTable> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Optional<DependentPriceTableDTO> optional = service.findById(id);
                    if (optional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.DEPENDENT_PRICE_TABLE_NOT_FOUND);
                    }

                    final CommonResponseWithDependentPriceTable response = new CommonResponseWithDependentPriceTable(StatusCodes.Success.PriceTable.GOT_PRICE_TABLE, optional.get());
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
