package br.com.monitoratec.farol.graphql.resolvers.mutation.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.service.plan.PlanSubscriptionService;
import br.com.monitoratec.farol.sql.model.payment.PaymentDiscount;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.payment.PaymentDiscountRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class AnticipateParcelImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnticipateParcelImpl.class);

    private final ClientRepository clientRepository;
    private final PaymentService paymentService;
    private final PaymentDiscountRepository paymentDiscountRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PlanSubscriptionService planSubscriptionService;

    public AnticipateParcelImpl(ClientRepository clientRepository, PaymentService paymentService, PaymentDiscountRepository paymentDiscountRepository, SubscribedPlanRepository subscribedPlanRepository, PlanSubscriptionService planSubscriptionService) {
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
        this.paymentDiscountRepository = paymentDiscountRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.planSubscriptionService = planSubscriptionService;
    }

    public CompletableFuture<CommonResponse> anticipateParcel(Integer parcelQuantity, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    if (!isValidParcelValue(parcelQuantity)) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_PARCEL_QUANTITY);
                    }

                    Client client = optionalClient.get();

                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    SubscribedPlan subscribedPlan = optionalSubscribedPlan.get();

                    // Must check if user is not default before anticipate more parcels
                    if (subscribedPlan.getLastPayment().isBefore(LocalDateTime.now())) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.CURRENT_PLAN_NOT_PAID);
                    }

                    if (planSubscriptionService.checkSubscriptionValidUntil(subscribedPlan, parcelQuantity)) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_NUMBER_OF_PARCELS);
                    }

                    // Check if there is discount if client anticipates more than one parcel. If discount is not found
                    // the discount will be considered zero.
                    BigDecimal discount = new BigDecimal(0);
                    Optional<PaymentDiscount> paymentDiscount = paymentDiscountRepository.findFirstByMonth(parcelQuantity);
                    if (paymentDiscount.isPresent()) {
                        discount = paymentDiscount.get().getDiscount();
                    }

                    paymentService.anticipate(client, subscribedPlan, parcelQuantity, discount);
                    CommonResponse commonResponse = StatusCodes.Success.Payment.ANTICIPATE_PAYMENT_SUCCESSFULLY;

                    responsePromise.complete(commonResponse);

                });

        return responsePromise;
    }

    private boolean isValidParcelValue(int quantity) {
        return quantity >= 1 && quantity <= 12;
    }
}
