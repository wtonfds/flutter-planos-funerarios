package br.com.monitoratec.farol.utils.location;

import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepAutoCompleter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CepAutoCompleter.class);

    private final RestTemplate restTemplate;
    private final String cepAutocompleteUrl;

    public CepAutoCompleter(RestTemplate restTemplate, @Value("${cep-autocomplete-url}") String cepAutocompleteUrl) {
        this.restTemplate = restTemplate;
        this.cepAutocompleteUrl = cepAutocompleteUrl;
    }

    public AddressDTO completeCep(String cep) {
        AddressDTO address = new AddressDTO();
        address.setZipCode("");
        address.setStreet("");
        address.setNumber("");
        address.setComplement("");
        address.setNeighborhood("");
        address.setCity("");
        address.setProvince("");
        address.setCountry("");

        try {
            final CepDTO dto = restTemplate.getForObject(cepAutocompleteUrl, CepDTO.class, cep);

            address.setZipCode(cep);

            if (dto != null && !dto.erro) {
                address.setStreet(dto.logradouro);
                address.setComplement(dto.complemento);
                address.setNeighborhood(dto.bairro);
                address.setCity(dto.localidade);
                address.setProvince(dto.uf);
            }
        } catch (Exception e) {
            LOGGER.info("Error retrieving information for CEP {}: {}", cep, e.getMessage());
        }

        return address;
    }

    private static final class CepDTO {
        private String logradouro;
        private String complemento;
        private String bairro;
        private String localidade;
        private String uf;
        private boolean erro;
    }
}
