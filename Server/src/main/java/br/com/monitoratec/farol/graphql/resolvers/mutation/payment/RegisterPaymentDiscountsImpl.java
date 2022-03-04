package br.com.monitoratec.farol.graphql.resolvers.mutation.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.payment.DiscountInput;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.payment.PaymentDiscount;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.payment.PaymentDiscountRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RegisterPaymentDiscountsImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPaymentDiscountsImpl.class);

    private final PaymentDiscountRepository paymentDiscountRepository;
    private final FarolUserRepository farolUserRepository;

    public RegisterPaymentDiscountsImpl(PaymentDiscountRepository paymentDiscountRepository, FarolUserRepository farolUserRepository) {
        this.paymentDiscountRepository = paymentDiscountRepository;
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponse> registerPaymentDiscounts(
            List<DiscountInput> discounts,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<FarolUser> farolUserOptional = farolUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (farolUserOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    List<PaymentDiscount> paymentDiscountList = paymentDiscountRepository.findAll();
                    List<PaymentDiscount> newPaymentDiscountList = new ArrayList<>();

                    for (DiscountInput input : discounts) {
                        PaymentDiscount existingDiscount = paymentDiscountList.stream().filter(x -> x.getMonth() == input.getMonth()).findAny().orElse(null);
                        if (existingDiscount == null) {
                            PaymentDiscount p = new PaymentDiscount();
                            p.setDiscount(input.getDiscount());
                            p.setMonth(input.getMonth());
                            newPaymentDiscountList.add(p);
                        } else {
                            existingDiscount.setDiscount(input.getDiscount());
                            newPaymentDiscountList.add(existingDiscount);
                        }
                    }

                    paymentDiscountRepository.saveAll(newPaymentDiscountList);
                    responsePromise.complete(StatusCodes.Success.Payment.DISCOUNTS_ADDED_SUCCESSFULLY);
                });

        return responsePromise;
    }
}
