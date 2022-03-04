package br.com.monitoratec.farol.graphql.resolvers.query.common;

import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.utils.data.DataUtils;
import br.com.monitoratec.farol.utils.location.CepAutoCompleter;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AutoCompleteCepImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCompleteCepImpl.class);

    private final CepAutoCompleter cepAutoCompleter;

    public AutoCompleteCepImpl(CepAutoCompleter cepAutoCompleter) {
        this.cepAutoCompleter = cepAutoCompleter;
    }

    public AddressDTO autoCompleteCep(String cep, DataFetchingEnvironment fetchingEnvironment) {
        super.logRequest(LOGGER, this);

        cep = DataUtils.onlyNumber(cep);
        return cepAutoCompleter.completeCep(cep);
    }
}
