package br.com.monitoratec.farol.graphql.resolvers.mutation.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.plan.PlanInput;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithPlanInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
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
public class UpdatePlanImpl extends BaseResolver implements GraphQLMutationResolver {

    private static Logger LOGGER = LoggerFactory.getLogger(UpdatePlanImpl.class);

    private final PlanRepository planRepository;
    private final PlanPriceTableRepository planPriceTableRepository;
    private final UpgradePriceTableRepository upgradePriceTableRepository;
    private final DependentPriceTableRepository dependentPriceTableRepository;

    public UpdatePlanImpl(PlanRepository planRepository, PlanPriceTableRepository planPriceTableRepository, UpgradePriceTableRepository upgradePriceTableRepository, DependentPriceTableRepository dependentPriceTableRepository) {
        this.planRepository = planRepository;
        this.planPriceTableRepository = planPriceTableRepository;
        this.upgradePriceTableRepository = upgradePriceTableRepository;
        this.dependentPriceTableRepository = dependentPriceTableRepository;
    }

    public CompletableFuture<CommonResponseWithPlanInformation> updatePlan(
            Long id,
            PlanInput planInput,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPlanInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Plan> planOptional = planRepository.findById(id);
                    if (planOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.PLAN_NOT_FOUND);
                    }

                    Plan plan = planOptional.get();

                    if (planInput.getActive() != null) {
                        plan.setActive(planInput.getActive());
                    }

                    if (planInput.getGracePeriod() != null) {
                        plan.setGracePeriod(planInput.getGracePeriod());
                    }

                    if (planInput.getGracePeriodExtraDependents() != null) {
                        plan.setGracePeriodExtraDependents(planInput.getGracePeriodExtraDependents());
                    }

                    if (planInput.getMaxExtraDependentsAmount() != null) {
                        plan.setMaxExtraDependentsAmount(planInput.getMaxExtraDependentsAmount());
                    }

                    if (planInput.getName() != null) {
                        plan.setName(planInput.getName());
                    }

                    if (planInput.getAdhesionContract() != null) {
                        plan.setAdhesionContract(planInput.getAdhesionContract());
                    }

                    if (planInput.getContractUrl() != null) {
                        plan.setContractUrl(planInput.getContractUrl());
                    }

                    // default price table
                    Optional<PlanPriceTable> planPriceTableOptional;
                    if (planInput.getPlanPriceTableId() != null) {
                        planPriceTableOptional = planPriceTableRepository.findByIdWithAgeRanges(planInput.getPlanPriceTableId());
                    } else {
                        planPriceTableOptional = planPriceTableRepository.findByIdWithAgeRanges(plan.getPlanPriceTable().getId());
                    }

                    if (planPriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.PLAN_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setPlanPriceTable(planPriceTableOptional.get());

                    // upgrade price table
                    Optional<UpgradePriceTable> upgradePriceTableOptional;
                    if (planInput.getUpgradePriceTableId() != null) {
                        upgradePriceTableOptional = upgradePriceTableRepository.findByIdWithAgeRanges(planInput.getUpgradePriceTableId());
                    } else {
                        upgradePriceTableOptional = upgradePriceTableRepository.findByIdWithAgeRanges(plan.getUpgradePriceTable().getId());
                    }

                    if (upgradePriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.UPGRADE_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setUpgradePriceTable(upgradePriceTableOptional.get());

                    // dependent price table
                    Optional<DependentPriceTable> dependentPriceTableOptional;
                    if (planInput.getDependentPriceTableId() != null) {
                        dependentPriceTableOptional = dependentPriceTableRepository.findByIdWithAgeRanges(planInput.getDependentPriceTableId());
                    } else {
                        dependentPriceTableOptional = dependentPriceTableRepository.findByIdWithAgeRanges(plan.getDependentPriceTable().getId());
                    }
                    if (dependentPriceTableOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.PriceTable.DEPENDENT_PRICE_TABLE_NOT_FOUND);
                    }
                    plan.setDependentPriceTable(dependentPriceTableOptional.get());

                    planRepository.save(plan);

                    CommonResponseWithPlanInformation commonResponseWithPlanInformation = new CommonResponseWithPlanInformation(StatusCodes.Success.Plan.GOT_PLAN, plan);
                    responsePromise.complete(commonResponseWithPlanInformation);
                });
        return responsePromise;
    }
}
