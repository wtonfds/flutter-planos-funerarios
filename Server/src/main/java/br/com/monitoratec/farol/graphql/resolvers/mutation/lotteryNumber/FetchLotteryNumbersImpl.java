package br.com.monitoratec.farol.graphql.resolvers.mutation.lotteryNumber;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.dtos.lotteryNumbers.LotteryNumbersDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.utils.lottery.LotteryNumberUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class FetchLotteryNumbersImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchLotteryNumbersImpl.class);

    private final LotteryNumberUtils lotteryNumberUtils;

    public FetchLotteryNumbersImpl(LotteryNumberUtils lotteryNumberUtils) {
        this.lotteryNumberUtils = lotteryNumberUtils;
    }

    public CompletableFuture<CommonResponse> fetchLotteryNumbers(
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access
                    LotteryNumbersDTO lotteryNumbersDTO = lotteryNumberUtils.generateCustomNumber();

                    CommonResponse commonResponse = new CommonResponse(StatusCodes.Success.LotteryNumbers.GENERATED_LOTTERYNUMBERS.getStatusCode(), lotteryNumbersDTO.getGeneratedNumbers(), false);
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
