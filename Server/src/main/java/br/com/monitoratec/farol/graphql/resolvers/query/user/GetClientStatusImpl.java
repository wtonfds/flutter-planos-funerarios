package br.com.monitoratec.farol.graphql.resolvers.query.user;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithClientInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetClientStatusImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetClientStatusImpl.class);

    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;

    public GetClientStatusImpl(ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository) {
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
    }

    public CommonResponseWithClientInformation getClientByCPF(String cpf) {
        super.logRequest(LOGGER, this);

        Optional<Client> clientOptional;

        if (CpfUtils.isValidCpf(cpf)) {
            clientOptional = clientRepository.findByCPF(CpfUtils.getOnlyNumbers(cpf));
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
        }

        if (clientOptional.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
        }

        Client client = clientOptional.get();

        // if the user have an automatic cancelled plan, the dependents should be able to create an account and subscribe
        // to a plan. The original client status keeps on the database for contract reactivation but the response should be
        // changed to Guest allowing the dependent to create an account if needed.
        if (client.getHolder() != null) {
            Optional<SubscribedPlan> holderSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getHolder().getId());
            if (holderSubscribedPlan.isPresent()) {
                if (!holderSubscribedPlan.get().isActive()) {// holder have a plan
                    client.setClientType(ClientTypeDTO.GUEST.name());
                    client.setHolder(null);
                }
            }
        }

        return new CommonResponseWithClientInformation(StatusCodes.Success.User.GOT_CLIENT_SUCCESS, client);
    }
}
