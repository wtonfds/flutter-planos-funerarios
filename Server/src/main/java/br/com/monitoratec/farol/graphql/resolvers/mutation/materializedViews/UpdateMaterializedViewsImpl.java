package br.com.monitoratec.farol.graphql.resolvers.mutation.materializedViews;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.materializedViews.MaterializedViewsService;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateMaterializedViewsImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateMaterializedViewsImpl.class);

    MaterializedViewsService materializedViewsService;

    public UpdateMaterializedViewsImpl(MaterializedViewsService materializedViewsService) {
        this.materializedViewsService = materializedViewsService;
    }

    public CompletableFuture<CommonResponse> updateMaterializedViews(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    materializedViewsService.updateMaterializedViews();

                    CommonResponse commonResponse = new CommonResponse(StatusCodes.Success.MaterializedViews.UPDATED_MATERIALIZED_VIEWS.getStatusCode(), StatusCodes.Success.MaterializedViews.UPDATED_MATERIALIZED_VIEWS.getDescription(), false);
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
