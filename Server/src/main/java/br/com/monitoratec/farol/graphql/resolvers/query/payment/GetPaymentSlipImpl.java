package br.com.monitoratec.farol.graphql.resolvers.query.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.payment.CommonResponseWithPaymentSlipId;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.payment.PaymentHistory;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.repository.payment.PaymentHistoryRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentMonthRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class GetPaymentSlipImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPaymentSlipImpl.class);
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PaymentMonthRepository paymentMonthRepository;

    public GetPaymentSlipImpl(PaymentHistoryRepository paymentHistoryRepository, SubscribedPlanRepository subscribedPlanRepository, PaymentMonthRepository paymentMonthRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.paymentMonthRepository = paymentMonthRepository;
    }

    public CompletableFuture<CommonResponseWithPaymentSlipId> getPaymentSlip(
            Integer month,
            Integer year,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPaymentSlipId> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long holderId = cachedTrustedToken.userCachedInfo.entityID;
                    SubscribedPlan subscribedPlan =
                            subscribedPlanRepository
                                    .findByBeneficiaryIdAndActiveIsTrue(holderId)
                                    .orElseThrow();
                    PaymentMonth paymentMonth =
                            paymentMonthRepository
                                    .findBySubscribedPlanAndMonthAndYear(subscribedPlan, month, year)
                                    .orElseThrow();
                    PaymentHistory paymentHistory =
                            paymentHistoryRepository
                                    .findTopByPaymentMonthAndStatusOrderByCreatedAtDesc(paymentMonth, "PENDING")
                                    .orElseThrow();

                    CommonResponseWithPaymentSlipId commonResponse = new CommonResponseWithPaymentSlipId(StatusCodes.Success.Payment.FOUND_PAYMENT_SLIP, paymentHistory.getPaymentId());
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
