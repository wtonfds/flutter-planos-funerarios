package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.plan.PlanInput;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithPlanInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.price.DependentPriceTable;
import br.com.monitoratec.farol.sql.model.price.PlanPriceTable;
import br.com.monitoratec.farol.sql.model.price.UpgradePriceTable;
import br.com.monitoratec.farol.sql.repository.plan.PlanRepository;
import br.com.monitoratec.farol.sql.repository.price.DependentPriceTableRepository;
import br.com.monitoratec.farol.sql.repository.price.PlanPriceTableRepository;
import br.com.monitoratec.farol.sql.repository.price.UpgradePriceTableRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RegisterNewPlanImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewPlanImpl.class);

    private final PlanRepository planRepository;
    private final PlanPriceTableRepository planPriceTableRepository;
    private final UpgradePriceTableRepository upgradePriceTableRepository;
    private final DependentPriceTableRepository dependentPriceTableRepository;
    private final PlanService planService;

    public RegisterNewPlanImpl(
            PlanRepository planRepository,
            PlanPriceTableRepository planPriceTableRepository,
            UpgradePriceTableRepository upgradePriceTableRepository,
            DependentPriceTableRepository dependentPriceTableRepository,
            PlanService planService) {
        this.planRepository = planRepository;
        this.planPriceTableRepository = planPriceTableRepository;
        this.upgradePriceTableRepository = upgradePriceTableRepository;
        this.dependentPriceTableRepository = dependentPriceTableRepository;
        this.planService = planService;
    }

    public CompletableFuture<CommonResponseWithPlanInformation> registerNewPlan(
            PlanInput planInput,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPlanInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Plan plan = new Plan();
                    plan.setActive(planInput.getActive());
                    plan.setMaxExtraDependentsAmount(planInput.getMaxExtraDependentsAmount());
                    plan.setGracePeriod(planInput.getGracePeriod());
                    plan.setGracePeriodExtraDependents(planInput.getGracePeriodExtraDependents());
                    plan.setName(planInput.getName());
                    plan.setAdhesionContract(planInput.getAdhesionContract());

                    Optional<PlanPriceTable> planPriceTableOptional = planPriceTableRepository.findByIdWithAgeRanges(planInput.getPlanPriceTableId());
                    if (planPriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.PLAN_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setPlanPriceTable(planPriceTableOptional.get());

                    Optional<UpgradePriceTable> upgradePriceTableOptional = upgradePriceTableRepository.findByIdWithAgeRanges(planInput.getUpgradePriceTableId());
                    if (upgradePriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.UPGRADE_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setUpgradePriceTable(upgradePriceTableOptional.get());

                    Optional<DependentPriceTable> dependentPriceTableOptional = dependentPriceTableRepository.findByIdWithAgeRanges(planInput.getDependentPriceTableId());
                    if (dependentPriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.DEPENDENT_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setDependentPriceTable(dependentPriceTableOptional.get());
                    plan.setContractUrl(planInput.getContractUrl());

                    Plan savedPlan = planRepository.save(plan);
                    plan.setId(savedPlan.getId());

                    CommonResponseWithPlanInformation commonResponseWithPlanInformation = new CommonResponseWithPlanInformation(StatusCodes.Success.Plan.REGISTERED_NEW_PLAN, plan);
                    responsePromise.complete(commonResponseWithPlanInformation);
                });
        return responsePromise;
    }
}
