package br.com.monitoratec.farol.graphql.resolvers.query.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.payment.CommonResponseWithGetNetToken;
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
import java.util.concurrent.CompletableFuture;

@Component
public class GetGetNetTokenImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetGetNetTokenImpl.class);

    private final PaymentService paymentService;

    public GetGetNetTokenImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public CompletableFuture<CommonResponseWithGetNetToken> getGetNetToken(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithGetNetToken> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    String getNetToken = paymentService.authenticate();

                    CommonResponseWithGetNetToken commonResponse = new CommonResponseWithGetNetToken(StatusCodes.Success.Payment.AUTHORIZATION_SUCCESSFUL, getNetToken);

                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
