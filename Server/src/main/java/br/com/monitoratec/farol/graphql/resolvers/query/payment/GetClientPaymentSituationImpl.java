package br.com.monitoratec.farol.graphql.resolvers.query.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentSituationDTO;
import br.com.monitoratec.farol.graphql.model.responses.payment.CommonResponseWithClientPaymentSituation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Success.Payment.FOUND_PAYMENT_SITUATION;

@Component
public class GetClientPaymentSituationImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetClientPaymentSituationImpl.class);

    private final PaymentService service;

    public GetClientPaymentSituationImpl(PaymentService service) {
        this.service = service;
    }

    public CompletableFuture<CommonResponseWithClientPaymentSituation> getClientPaymentSituation(Long clientId, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithClientPaymentSituation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    final List<PaymentSituationDTO> paymentSituation = service.getClientPaymentSituation(clientId);

                    final CommonResponseWithClientPaymentSituation commonResponse = new CommonResponseWithClientPaymentSituation(FOUND_PAYMENT_SITUATION, paymentSituation);
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
