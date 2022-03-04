package br.com.monitoratec.farol.graphql.resolvers.query.price;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.dtos.price.UpgradePriceTableDTO;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.price.CommonResponseWithUpgradePriceTables;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.price.UpgradePriceTableService;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class ListUpgradePriceTablesImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListUpgradePriceTablesImpl.class);

    private final UpgradePriceTableService service;

    public ListUpgradePriceTablesImpl(UpgradePriceTableService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithUpgradePriceTables> listUpgradePriceTables(
            Long filterByID,
            String filterByName,
            Paginate paginate,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithUpgradePriceTables> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Page<UpgradePriceTableDTO> page = service.list(filterByID, filterByName, paginate);

                    final CommonResponseWithUpgradePriceTables response = new CommonResponseWithUpgradePriceTables(StatusCodes.Success.PriceTable.FOUND_PRICE_TABLES, page);
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
