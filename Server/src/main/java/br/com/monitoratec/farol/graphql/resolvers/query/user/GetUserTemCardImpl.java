package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.tem.CommonResponseWithTemCard;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.resolvers.query.product.ListProductsImpl;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class GetUserTemCardImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListProductsImpl.class);

    private final ClientRepository clientRepository;

    public GetUserTemCardImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Deprecated(since = "10/08/2020")
    public CompletableFuture<CommonResponseWithTemCard> getUserTemCard(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithTemCard> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    throw new ErrorOnProcessingRequestException(StatusCodes.Error.Tem.TEM_INTEGRATION_NOT_BEING_USED);
//
//                    Optional<Client> optionalClient = clientRepository.findById(cachedTrustedToken.userCachedInfo.entityID);
//                    if (optionalClient.isEmpty()) {
//                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
//                    }
//
//                    CardDataDTO card = temService.listTemCards(optionalClient.get().getTemCardNumber());
//                    CommonResponseWithTemCard response = new CommonResponseWithTemCard(StatusCodes.Success.Tem.GOT_CARD_SUCCESSFULLY, card);
//
//                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
