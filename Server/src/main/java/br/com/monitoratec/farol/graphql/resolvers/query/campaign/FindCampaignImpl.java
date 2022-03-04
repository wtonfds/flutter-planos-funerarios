package br.com.monitoratec.farol.graphql.resolvers.query.campaign;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.responses.campaign.CommonResponseWithCampaign;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.campaign.CampaignService;
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
public class FindCampaignImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindCampaignImpl.class);

    private final CampaignService service;

    public FindCampaignImpl(CampaignService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithCampaign> findCampaign(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithCampaign> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final Optional<CampaignDTO> optional = service.findById(id);
                    if (optional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Campaign.CAMPAIGN_NOT_FOUND);
                    }

                    final CommonResponseWithCampaign response = new CommonResponseWithCampaign(StatusCodes.Success.Campaign.GOT_CAMPAIGN, optional.get());
                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
