package br.com.monitoratec.farol.graphql.resolvers.query.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.payment.CommonResponseWithDiscountList;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.payment.PaymentDiscount;
import br.com.monitoratec.farol.sql.repository.payment.PaymentDiscountRepository;
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
public class ListPaymentDiscountsImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListPaymentDiscountsImpl.class);

    private final PaymentDiscountRepository paymentDiscountRepository;

    public ListPaymentDiscountsImpl(PaymentDiscountRepository paymentDiscountRepository) {
        this.paymentDiscountRepository = paymentDiscountRepository;
    }

    public CompletableFuture<CommonResponseWithDiscountList> listPaymentDiscounts(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithDiscountList> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    List<PaymentDiscount> paymentDiscountList = paymentDiscountRepository.findAll();
                    CommonResponseWithDiscountList commonResponseWithDiscountList = new CommonResponseWithDiscountList(StatusCodes.Success.Payment.DISCOUNTS_RETRIEVED_SUCCESSFULLY, paymentDiscountList);
                    responsePromise.complete(commonResponseWithDiscountList);
                });

        return responsePromise;
    }
}
