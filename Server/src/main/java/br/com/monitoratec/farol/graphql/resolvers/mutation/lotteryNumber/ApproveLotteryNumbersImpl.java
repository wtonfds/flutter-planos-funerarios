package br.com.monitoratec.farol.graphql.resolvers.mutation.lotteryNumber;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.lotteryNumber.LotteryNumberRepository;
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
public class ApproveLotteryNumbersImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApproveLotteryNumbersImpl.class);
    private final LotteryNumberRepository lotteryNumberRepository;
    private final FarolUserRepository farolUserRepository;

    public ApproveLotteryNumbersImpl(LotteryNumberRepository lotteryNumberRepository, FarolUserRepository farolUserRepository) {
        this.lotteryNumberRepository = lotteryNumberRepository;
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponse> approveLotteryNumbers(
            Long id,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponse> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<LotteryNumber> optionalLotteryNumber = lotteryNumberRepository.findById(id);
                    if (optionalLotteryNumber.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Lottery.LOTTERY_NUMBERS_NOT_FOUND);
                    }

                    Optional<FarolUser> optionalFarolUser = farolUserRepository.findById(cachedTrustedToken.userCachedInfo.entityID);
                    if (optionalFarolUser.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
                    }

                    LotteryNumber approvedLotteryNumbers = optionalLotteryNumber.get();
                    if (approvedLotteryNumbers.getApprovedBy() != null) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Lottery.LOTTERY_NUMBERS_ALREADY_APPROVED);
                    }
                    FarolUser farolUser = optionalFarolUser.get();

                    approvedLotteryNumbers.setApprovedBy(farolUser);

                    lotteryNumberRepository.save(approvedLotteryNumbers);

                    CommonResponse commonResponse = new CommonResponse(StatusCodes.Success.Lottery.APPROVED_LOTTERY_NUMBERS_SUCCESSFULLY.getStatusCode(), StatusCodes.Success.Lottery.APPROVED_LOTTERY_NUMBERS_SUCCESSFULLY.getDescription(), false);
                    responsePromise.complete(commonResponse);
                });
        return responsePromise;
    }
}
