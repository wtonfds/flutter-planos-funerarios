package br.com.monitoratec.farol.graphql.resolvers.query.lotteryNumber;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.lotteryNumbers.CommonResponseWithLotteryNumbers;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import br.com.monitoratec.farol.sql.repository.lotteryNumber.LotteryNumberRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class ListLotteryNumbersImpl extends BaseResolver implements GraphQLQueryResolver {

    private final LotteryNumberRepository lotteryNumberRepository;

    public ListLotteryNumbersImpl(LotteryNumberRepository lotteryNumberRepository){
        this.lotteryNumberRepository = lotteryNumberRepository;
    }

    public CompletableFuture<CommonResponseWithLotteryNumbers> listLotteryNumbers(
            Paginate paginate,
            DataFetchingEnvironment dataFetchingEnvironment
    ){

        CompletableFuture<CommonResponseWithLotteryNumbers> responsePromise = TimedOutHandledPromiser.genPromise();
        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, LotteryNumber.Fields.ID));

                    Page<LotteryNumber> lotteryNumberPage = lotteryNumberRepository.findAll(pageable);
                    responsePromise.complete(new CommonResponseWithLotteryNumbers(StatusCodes.Success.LotteryNumbers.GOT_LOTTERYNUMBERS, lotteryNumberPage));
                });
        return responsePromise;
    }
}
