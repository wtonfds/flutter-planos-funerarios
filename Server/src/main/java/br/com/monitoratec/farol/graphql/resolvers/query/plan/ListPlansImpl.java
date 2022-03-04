package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithPlans;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.repository.plan.PlanRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class ListPlansImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListPlansImpl.class);

    private final PlanRepository planRepository;
    private final PlanService planService;

    public ListPlansImpl(PlanRepository planRepository, PlanService planService) {
        this.planRepository = planRepository;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithPlans> listPlans(
            Long filterByID,
            String filterByName,
            Paginate paginate,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPlans> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, Plan.Fields.ID));

                    Page<Plan> planPage = planRepository.findByFiltersAllNullable(filterByID, filterByName, pageable);

                    planPage = planPage.map(planService::buildPlanWithAgeRanges);

                    responsePromise.complete(new CommonResponseWithPlans(StatusCodes.Success.Plan.FOUND_PLANS, planPage));
                });
        return responsePromise;
    }
}
