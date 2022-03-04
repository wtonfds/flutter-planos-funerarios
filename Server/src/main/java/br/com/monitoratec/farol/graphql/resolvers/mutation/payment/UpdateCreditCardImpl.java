package br.com.monitoratec.farol.graphql.resolvers.mutation.payment;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.payment.CardInput;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
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
public class UpdateCreditCardImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCreditCardImpl.class);

    private final ClientRepository clientRepository;
    private final PaymentService paymentService;
    private final SubscribedPlanRepository subscribedPlanRepository;

    public UpdateCreditCardImpl(ClientRepository clientRepository, PaymentService paymentService, SubscribedPlanRepository subscribedPlanRepository) {
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
        this.subscribedPlanRepository = subscribedPlanRepository;
    }

    public CompletableFuture<CommonResponse> updateCreditCard(CardInput cardInput, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Client> optionalClient = clientRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalClient.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(optionalClient.get().getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    if (!optionalSubscribedPlan.get().getPaymentType().equals(PaymentTypeDTO.CREDIT_CARD.name())) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.PAYMENT_METHOD_IS_NOT_CREDIT_CARD);
                    }

                    paymentService.updateCreditCard(cardInput, optionalClient.get());

                    responsePromise.complete(StatusCodes.Success.Payment.CREDIT_CARD_UPDATED);
                });

        return responsePromise;
    }
}
