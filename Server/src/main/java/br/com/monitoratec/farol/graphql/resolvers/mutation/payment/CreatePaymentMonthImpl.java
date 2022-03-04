package br.com.monitoratec.farol.graphql.resolvers.mutation.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class CreatePaymentMonthImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreatePaymentMonthImpl.class);

    private final FarolUserRepository farolUserRepository;
    private final PaymentService paymentService;

    public CreatePaymentMonthImpl(FarolUserRepository farolUserRepository, PaymentService paymentService) {
        this.farolUserRepository = farolUserRepository;
        this.paymentService = paymentService;
    }

    public CompletableFuture<CommonResponse> createPaymentMonth(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Optional<FarolUser> optionalFarolUser = farolUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalFarolUser.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    LOGGER.info("Calling method createPaymentMonth using a mutation");
                    paymentService.createPaymentMonth();
                    CommonResponse commonResponse = StatusCodes.Success.Payment.CREATED_PAYMENT_MONTH_SUCCESSFULLY;
                    responsePromise.complete(commonResponse);
                });

        return responsePromise;
    }
}
