package br.com.monitoratec.farol.graphql.resolvers.mutation.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.payment.PaymentHistoryRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
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
public class RegisterNewPaymentSlipImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewPaymentSlipImpl.class);

    private final SubscribedPlanRepository subscribedPlanRepository;
    private final ClientRepository clientRepository;
    private final PaymentService paymentService;

    public RegisterNewPaymentSlipImpl(SubscribedPlanRepository subscribedPlanRepository, PaymentHistoryRepository paymentHistoryRepository, ClientRepository clientRepository, PaymentService paymentService) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
    }

    public CompletableFuture<CommonResponse> registerNewPaymentSlip(
            Integer month,
            Integer year,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long userId = cachedTrustedToken.userCachedInfo.entityID;

                    Client holder = clientRepository.findByIdAndActiveTrue(userId)
                            .orElseThrow();

                    SubscribedPlan subscribedPlan =
                            subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(userId)
                                    .orElseThrow();

                    paymentService.registerNewPaymentSlip(holder, subscribedPlan, month, year);

                    responsePromise.complete(StatusCodes.Success.Payment.REGISTERED_NEW_PAYMENT_SLIP);
                });

        return responsePromise;
    }
}
