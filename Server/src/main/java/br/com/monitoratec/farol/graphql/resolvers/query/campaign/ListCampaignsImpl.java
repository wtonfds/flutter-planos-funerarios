package br.com.monitoratec.farol.graphql.resolvers.query.campaign;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.campaign.CommonResponseWithCampaigns;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.campaign.CampaignService;
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
public class ListCampaignsImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListCampaignsImpl.class);

    private final CampaignService service;

    public ListCampaignsImpl(CampaignService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithCampaigns> listCampaigns(
            Long filterByID,
            String filterByName,
            Paginate paginate,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithCampaigns> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Page<CampaignDTO> page = service.list(filterByID, filterByName, paginate);

                    final CommonResponseWithCampaigns response = new CommonResponseWithCampaigns(StatusCodes.Success.Campaign.FOUND_CAMPAIGNS, page);
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
