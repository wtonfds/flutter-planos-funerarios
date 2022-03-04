package br.com.monitoratec.farol.graphql.resolvers.query.plan;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.plan.CommonResponseWithPaymentMonths;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.repository.payment.PaymentMonthRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
public class ListPaymentMonthsImpl extends BaseResolver implements GraphQLQueryResolver {
    Logger LOGGER = LoggerFactory.getLogger(ListPaymentMonthsImpl.class);
    private SubscribedPlanRepository subscribedPlanRepository;
    private PaymentMonthRepository paymentMonthRepository;

    public ListPaymentMonthsImpl(SubscribedPlanRepository subscribedPlanRepository, PaymentMonthRepository paymentMonthRepository) {
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.paymentMonthRepository = paymentMonthRepository;
    }

    public CompletableFuture<CommonResponseWithPaymentMonths> listPaymentMonths(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPaymentMonths> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Long holderId = cachedTrustedToken.userCachedInfo.entityID;
                    SubscribedPlan subscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(holderId).orElseThrow();

                    List<PaymentMonth> paymentMonthList = paymentMonthRepository.findAllBySubscribedPlan(subscribedPlan);

                    CommonResponseWithPaymentMonths commonResponseWithPaymentMonths = new CommonResponseWithPaymentMonths(StatusCodes.Success.Plan.PAYMENT_MONTHS_RETRIEVED, paymentMonthList);

                    responsePromise.complete(commonResponseWithPaymentMonths);
                });
        return responsePromise;
    }
}
