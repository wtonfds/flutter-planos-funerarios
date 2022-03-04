package br.com.monitoratec.farol.graphql.resolvers.query.generalParameterization;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.responses.generalParameterization.CommonResponseWithGeneralParameterization;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class GetGeneralParameterizationImpl  extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetGeneralParameterizationImpl.class);

    private final GeneralParameterizationRepository generalParameterizationRepository;

    public GetGeneralParameterizationImpl(GeneralParameterizationRepository generalParameterizationRepository) {
        this.generalParameterizationRepository = generalParameterizationRepository;
    }

    public CompletableFuture<CommonResponseWithGeneralParameterization> getGeneralParameterization(DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithGeneralParameterization> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                   GeneralParameterization generalParameterizationOptional = generalParameterizationRepository.findAll().get(0);

                   CommonResponseWithGeneralParameterization commonResponseWithGeneralParameterization = new CommonResponseWithGeneralParameterization(StatusCodes.Success.GeneralParameterization.GOT_GENERAL_PARAMETERIZATION, generalParameterizationOptional);
                   responsePromise.complete(commonResponseWithGeneralParameterization);
                });
        return responsePromise;
    }
}
