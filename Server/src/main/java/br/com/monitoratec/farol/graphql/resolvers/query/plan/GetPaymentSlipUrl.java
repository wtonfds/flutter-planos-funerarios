package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithPaymentSlipUrl;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.sql.model.payment.PaymentHistory;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.payment.PaymentHistoryRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentMonthRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
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
public class GetPaymentSlipUrl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPaymentSlipUrl.class);

    private final ClientRepository clientRepository;
    private final PaymentService paymentService;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PaymentMonthRepository paymentMonthRepository;

    public GetPaymentSlipUrl(ClientRepository clientRepository, PaymentService paymentService, PaymentHistoryRepository paymentHistoryRepository, SubscribedPlanRepository subscribedPlanRepository, PaymentMonthRepository paymentMonthRepository) {
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.paymentMonthRepository = paymentMonthRepository;
    }

    // Returns only the last pending payment slip for an active subscribed plan
    public CompletableFuture<CommonResponseWithPaymentSlipUrl> getPaymentSlipUrl(
            Integer month,
            Integer year,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPaymentSlipUrl> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    final String status = "PENDING";

                    Optional<Client> optionalClient = clientRepository.findById(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }
                    Client client = optionalClient.get();

                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }
                    SubscribedPlan subscribedPlan = optionalSubscribedPlan.get();

                    PaymentMonth paymentMonth =
                            paymentMonthRepository
                                    .findBySubscribedPlanAndMonthAndYear(subscribedPlan, month, year)
                                    .orElseThrow();

                    PaymentHistory paymentHistory =
                            paymentHistoryRepository
                                    .findTopByPaymentMonthAndStatusOrderByCreatedAtDesc(paymentMonth, "PENDING")
                                    .orElseThrow();

                    final String url = paymentService.getPaymentSlipUrl(paymentHistory.getPaymentId());

                    CommonResponseWithPaymentSlipUrl commonResponseWithPaymentSlipUrl = new CommonResponseWithPaymentSlipUrl(StatusCodes.Success.Plan.PAYMENT_SLIP_URL_RETRIEVED, url);
                    responsePromise.complete(commonResponseWithPaymentSlipUrl);
                });
        return responsePromise;
    }
}
