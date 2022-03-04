package br.com.monitoratec.farol.graphql.resolvers.query.accredited;

import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.accredited.CommonResponseWithAccreditedUsers;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import br.com.monitoratec.farol.sql.repository.accredited.AccreditedRepository.AccreditedRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ListAccreditedUsersImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListAccreditedUsersImpl.class);

    private final AccreditedRepository accreditedRepository;

    public ListAccreditedUsersImpl(AccreditedRepository accreditedRepository) {
        this.accreditedRepository = accreditedRepository;
    }

    public CommonResponseWithAccreditedUsers listAccreditedUsers(
            Long filterByID,
            String filterByName,
            Paginate paginate
    ) {
        super.logRequest(LOGGER, this);


        Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, Accredited.Fields.ID));

        Page<Accredited> accreditedUsersPage = accreditedRepository.findByFiltersAllNullable(filterByID, filterByName, pageable);
        return new CommonResponseWithAccreditedUsers(StatusCodes.Success.Accredited.FOUND_ACCREDITED_USERS, accreditedUsersPage);
    }
}
