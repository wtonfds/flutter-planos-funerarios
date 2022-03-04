package br.com.monitoratec.farol.graphql.resolvers.query.generalParameterization;

import br.com.monitoratec.farol.graphql.model.responses.generalParameterization.CommonResponseWithPlanBenefits;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetPlanBenefitsImpl extends BaseResolver implements GraphQLQueryResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetPlanBenefitsImpl.class);

    private final GeneralParameterizationRepository generalParameterizationRepository;

    public GetPlanBenefitsImpl(GeneralParameterizationRepository generalParameterizationRepository) {
        this.generalParameterizationRepository = generalParameterizationRepository;
    }

    public CommonResponseWithPlanBenefits getPlanBenefits() {
        super.logRequest(LOGGER, this);

        GeneralParameterization generalParameterizationOptional = generalParameterizationRepository.findAll().get(0);
        String planBenefits = generalParameterizationOptional.getPlanBenefits();
        if (planBenefits == null) {
            planBenefits = "";
        }

        return new CommonResponseWithPlanBenefits(StatusCodes.Success.GeneralParameterization.GOT_PLAN_BENEFITS_SUCCESSFULLY, planBenefits);
    }
}

