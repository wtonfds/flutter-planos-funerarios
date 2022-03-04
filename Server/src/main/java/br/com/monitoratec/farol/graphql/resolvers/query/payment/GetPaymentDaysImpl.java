package br.com.monitoratec.farol.graphql.resolvers.query.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.payment.CommonResponseWithPaymentDays;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
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
public class GetPaymentDaysImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPaymentDaysImpl.class);

    private final PaymentService paymentService;

    public GetPaymentDaysImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public CompletableFuture<CommonResponseWithPaymentDays> getPaymentDays(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithPaymentDays> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    List<Integer> paymentDays = paymentService.getPaymentDays();

                    responsePromise.complete(new CommonResponseWithPaymentDays(StatusCodes.Success.Plan.PAYMENT_DAYS_RETRIEVED, paymentDays));

                });
        return responsePromise;
    }
}