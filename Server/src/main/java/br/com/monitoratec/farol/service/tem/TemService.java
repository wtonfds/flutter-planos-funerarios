/*
Deprecated class
Tem integration is removed from project.
 */


//package br.com.monitoratec.farol.service.tem;
//
//import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
//import br.com.monitoratec.farol.graphql.model.dtos.tem.*;
//import br.com.monitoratec.farol.graphql.model.dtos.user.GenderDTO;
//import br.com.monitoratec.farol.rest.tem.dto.SubscribeToTemParameters;
//import br.com.monitoratec.farol.rest.tem.dto.TemGetAllCardsParameters;
//import br.com.monitoratec.farol.rest.tem.dto.TemLoginParameters;
//import br.com.monitoratec.farol.rest.tem.dto.UpdateTemStatusParameters;
//import br.com.monitoratec.farol.sql.model.location.Address;
//import br.com.monitoratec.farol.sql.model.user.Client;
//import br.com.monitoratec.farol.utils.data.CpfUtils;
//import br.com.monitoratec.farol.utils.responses.StatusCodes;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.text.Normalizer;
//import java.util.List;
//
//@Service
//public class TemService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TemService.class);
//
//    private final RestTemplate restTemplate;
//    private final String companyId;
//    private final String apiKey;
//    private final String url;
//    private final String urlGetAllCards;
//    private final String tokenUrl;
//    private final String onixCode;
//    private final String cnpj;
//
//    public TemService(
//            RestTemplate restTemplate,
//            @Value("${tem.company_id}") String companyId,
//            @Value("${tem.api_key}") String apiKey,
//            @Value("${tem.url}") String url,
//            @Value("${tem.url_get_all_cards}") String urlGetAllCards,
//            @Value("${tem.token_url}") String tokenUrl,
//            @Value("${tem.onyx_code}") String onyxCode,
//            @Value("${tem.cnpj}") String cnpj
//    ) {
//        this.restTemplate = restTemplate;
//        this.companyId = companyId;
//        this.apiKey = apiKey;
//        this.url = url;
//        this.urlGetAllCards = urlGetAllCards;
//        this.tokenUrl = tokenUrl;
//        this.onixCode = onyxCode;
//        this.cnpj = cnpj;
//    }
//
//    private HttpHeaders getTemHttpHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
//
//    private String getAuthenticationToken() {
//        HttpHeaders headers = getTemHttpHeaders();
//
//        TemLoginParameters temLoginParameters = new TemLoginParameters();
//        temLoginParameters.setApiKey(apiKey);
//        temLoginParameters.setCompanyId(companyId);
//
//        HttpEntity<Object> request = new HttpEntity<>(temLoginParameters, headers);
//
//        final LoginResponseWrapperDTO loginResponseWrapperDTO = restTemplate.postForObject(tokenUrl, request, LoginResponseWrapperDTO.class);
//
//        if (loginResponseWrapperDTO != null) {
//            return loginResponseWrapperDTO.getData().getToken();
//        } else {
//            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Tem.TEM_COULD_NOT_GET_TOKEN);
//        }
//    }
//
//    private String convertGenderForTem(String gender) {
//        return gender.equals(GenderDTO.MALE.name()) ? "1" : "0";
//    }
//
//    public static String removeSpecialCharacters(String str) {
//        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
//    }
//
//    private SubscribeToTemParameters buildSubscribeToTemParameters(Client client, Address address, String tokenZeus) {
//        SubscribeToTemParameters parameters = new SubscribeToTemParameters();
//        final String gender = convertGenderForTem(client.getGender());
//        final String formattedName = removeSpecialCharacters(client.getName());
//
//        parameters.setCpfTitular("");
//        parameters.setCodOnix(onixCode);
//        parameters.setNome(formattedName);
//        parameters.setCpf(CpfUtils.getOnlyNumbers(client.getCPF()));
//        parameters.setDataNascimento(client.getBirthDay().toString()); // already in format YYYY-MM-DD
//        parameters.setSexo(gender);
//        parameters.setNumeroCartao("");
//        parameters.setCnpj(cnpj);
//        parameters.setLogradouro("");
//        parameters.setNumeroEndereco(address.getNumber());
//        parameters.setComplemento(address.getComplement());
//        parameters.setBairro(address.getNeighborhood());
//        parameters.setCidade(address.getCity());
//        parameters.setEstado(address.getProvince());
//        parameters.setCep(address.getZipCode());
//        parameters.setTelefone(client.getTelephone());
//        parameters.setNumerodasorte("");
//        parameters.setTokenzeus(tokenZeus);
//
//        return parameters;
//    }
//
//    public Client subscribeToTem(Client client, Address address) {
//        HttpHeaders headers = getTemHttpHeaders();
//        final String tokenZeus = getAuthenticationToken();
//        final String temUrl = url + "tem_adesao";
//        SubscribeToTemParameters parameters = buildSubscribeToTemParameters(client, address, tokenZeus);
//
//        HttpEntity<Object> request = new HttpEntity<>(parameters, headers);
//
//        final RegisterResponseDTO registerResponseDTO = restTemplate.postForObject(temUrl, request, RegisterResponseDTO.class);
//
//        if (registerResponseDTO == null) {
//            LOGGER.error("Fatal error on TEM subscription");
//            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Tem.TEM_COULD_NOT_SUBSCRIBE);
//        }
//
//        // The expected status is 200. Anything that is not HTTP STATUS: 200 should be logged as an warning.
//        if (registerResponseDTO.getStatus() != 200) {
//            LOGGER.warn("Unexpected http status code when saving user on TEM. Expected 200 but got: {}, with message: {}",
//                    registerResponseDTO.getStatus(), registerResponseDTO.getMessage());
//        } else {
//            LOGGER.info("Saving user on TEM successfully with message: {}", registerResponseDTO.getMessage());
//        }
//
//        final String userToken = registerResponseDTO.getUserToken();
//        final String cardNumber = registerResponseDTO.getNumeroCartao();
//
//        client.setTemCardNumber(cardNumber);
//        client.setTemUserToken(userToken);
//        return client;
//    }
//
//    public void changeTemStatus(String userToken, TemStatusTypeDTO status) {
//        HttpHeaders headers = getTemHttpHeaders();
//        final String tokenZeus = getAuthenticationToken();
//        final String temUrl = url + "tem_alteracao_status_cto";
//
//        UpdateTemStatusParameters parameters = new UpdateTemStatusParameters();
//        parameters.setCodOnyx(onixCode);
//        parameters.setUserToken(userToken);
//        parameters.setCpf("");
//        parameters.setNumeroCartao("");
//        parameters.setTokenzeus(tokenZeus);
//        parameters.setNovoStatus(status.value);
//
//        HttpEntity<Object> request = new HttpEntity<>(parameters, headers);
//
//        final UpdateTemStatusResponse response;
//        try {
//            response = restTemplate.postForObject(temUrl, request, UpdateTemStatusResponse.class);
//
//            if (response != null) {
//                LOGGER.info("Updated TEM status: {}", response.getMessage());
//            } else {
//                LOGGER.warn("Something wrong happened. Response from TEM was null");
//            }
//
//        } catch (Exception ex) {
//            LOGGER.error("Fatal error, could not update status, error: {}", ex.getMessage());
//        }
//    }
//
//    public CardDataDTO listTemCards(String cardNumber) {
//        HttpHeaders headers = getTemHttpHeaders();
//        final String tokenZeus = getAuthenticationToken();
//        final String temUrl = urlGetAllCards + "getallcards";
//
//        TemGetAllCardsParameters parameters = new TemGetAllCardsParameters();
//        parameters.setCnpj(cnpj);
//        parameters.setCodOnyx(onixCode);
//        parameters.setTokenzeus(tokenZeus);
//
//        HttpEntity<Object> request = new HttpEntity<>(parameters, headers);
//
//        final CardDataWrapperDTO response = restTemplate.postForObject(temUrl, request, CardDataWrapperDTO.class);
//
//        assert response != null;
//        List<CardDataDTO> cards = response.getData();
//
//        for (CardDataDTO c : cards) {
//            if (c.getNumeroCartao().equals(cardNumber)) {
//                return c;
//            }
//        }
//
//        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Tem.TEM_CARD_NOT_FOUND);
//    }
//}
