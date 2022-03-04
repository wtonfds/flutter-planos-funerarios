package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithFarolUsers;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.permission.Permission;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ListFarolUsersImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListFarolUsersImpl.class);

    private final FarolUserRepository farolUserRepository;

    public ListFarolUsersImpl(FarolUserRepository farolUserRepository) {
        this.farolUserRepository = farolUserRepository;
    }

    public CompletableFuture<CommonResponseWithFarolUsers> listFarolUsers(
            Long filterByID,
            String filterByName,
            Paginate paginate,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithFarolUsers> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    // Validated access

                    Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, FarolUser.Fields.ID));

                    Page<FarolUser> farolUsersPage = farolUserRepository.findByFiltersAllNullable(filterByID, filterByName, pageable);

                    for (FarolUser f : farolUsersPage) {
                        List<Permission> permissions = farolUserRepository.findUserPermissions(f.getId());
                        f.setPermissionList(permissions);
                    }

                    responsePromise.complete(new CommonResponseWithFarolUsers(StatusCodes.Success.User.FOUND_FAROL_USERS, farolUsersPage));
                });
        return responsePromise;
    }
}
