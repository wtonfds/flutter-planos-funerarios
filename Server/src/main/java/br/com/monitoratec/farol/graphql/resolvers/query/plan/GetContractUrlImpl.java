package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithContractUrl;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.repository.plan.PlanRepository;
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
public class GetContractUrlImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetContractUrlImpl.class);

    private final PlanRepository planRepository;

    public GetContractUrlImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public CompletableFuture<CommonResponseWithContractUrl> getContractUrl(Long planId, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithContractUrl> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<Plan> plan = planRepository.findById(planId);
                    if (plan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.PLAN_NOT_FOUND);
                    }

                    responsePromise.complete(new CommonResponseWithContractUrl(StatusCodes.Success.Plan.CONTRACT_URL_RETRIEVED, plan.get().getContractUrl()));
                });
        return responsePromise;
    }
}
