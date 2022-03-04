package br.com.monitoratec.farol.service.payment;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import br.com.monitoratec.farol.graphql.model.dtos.payment.*;
import br.com.monitoratec.farol.graphql.model.input.payment.CardInput;
import br.com.monitoratec.farol.rest.payment.dto.*;
import br.com.monitoratec.farol.service.invoice.InvoiceService;
import br.com.monitoratec.farol.service.invoice.model.*;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.service.plan.PlanService;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.soap.ginfes.ConsultarSituacaoLoteRpsResposta;
import br.com.monitoratec.farol.soap.ginfes.EnviarLoteRpsResposta;
import br.com.monitoratec.farol.sql.model.company.Company;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.model.payment.PaymentHistory;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import br.com.monitoratec.farol.sql.model.payment.UncheckedInvoice;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.company.CompanyRepository;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentHistoryRepository;
import br.com.monitoratec.farol.sql.repository.payment.PaymentMonthRepository;
import br.com.monitoratec.farol.sql.repository.payment.UncheckedInvoiceRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.DataUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.Payment.PAYMENT_SITUATION_NOT_FOUND;

@Service
public class PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private final RestTemplate restTemplate;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final PaymentMonthRepository paymentMonthRepository;
    private final GeneralParameterizationRepository generalParameterizationRepository;
    private final MailService mailService;
    private final String getNetUrl;
    private final String getNetClientId;
    private final String getNetClientSecret;
    private final String getNetSellerId;
    private final String vidaPlanoMail;
    private final CompanyRepository companyRepository;
    private final InvoiceService invoiceService;
    private final String paymentSlipMailSubject;
    private final String paymentSlipMailBody;
    private final String nfseSubject;
    private final String nfseBody;
    private final String nfseFrom;
    private final String paymentDays;
    private final ClientRepository clientRepository;
    private final PlanService planService;
    private final UncheckedInvoiceRepository uncheckedInvoiceRepository;
    private final S3Service s3Service;

    public PaymentService(
            RestTemplate restTemplate,
            @Value("${getnet.url}") String getNetUrl,
            @Value("${getnet.client_id}") String getNetClientId,
            @Value("${getnet.client_secret}") String getNetClientSecret,
            @Value("${getnet.seller_id}") String getNetSellerId,
            PaymentHistoryRepository paymentHistoryRepository,
            SubscribedPlanRepository subscribedPlanRepository,
            PaymentMonthRepository paymentMonthRepository, MailService mailService,
            CompanyRepository companyRepository, InvoiceService invoiceService,
            @Value("${mail.reset-password-from}") String vidaPlanoMail,
            @Value("${mail.charge-client-subject}") String paymentSlipMailSubject,
            @Value("${mail.payment-slip-body}") String paymentSlipMailBody,
            @Value("${mail.nfse-subject}") String nfseSubject,
            @Value("${mail.nfse-body}") String nfseBody,
            @Value("${mail.nfse-from}") String nfseFrom,
            @Value("${payment-days}") String paymentDays, ClientRepository clientRepository, PlanService planService,
            UncheckedInvoiceRepository uncheckedInvoiceRepository, S3Service s3Service,
            GeneralParameterizationRepository generalParameterizationRepository) {
        this.restTemplate = restTemplate;
        this.getNetUrl = getNetUrl;
        this.getNetClientId = getNetClientId;
        this.getNetClientSecret = getNetClientSecret;
        this.getNetSellerId = getNetSellerId;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.paymentMonthRepository = paymentMonthRepository;
        this.mailService = mailService;
        this.companyRepository = companyRepository;
        this.invoiceService = invoiceService;
        this.vidaPlanoMail = vidaPlanoMail;
        this.paymentSlipMailSubject = paymentSlipMailSubject;
        this.paymentSlipMailBody = paymentSlipMailBody;
        this.nfseBody = nfseBody;
        this.nfseFrom = nfseFrom;
        this.nfseSubject = nfseSubject;
        this.paymentDays = paymentDays;
        this.clientRepository = clientRepository;
        this.planService = planService;
        this.uncheckedInvoiceRepository = uncheckedInvoiceRepository;
        this.s3Service = s3Service;
        this.generalParameterizationRepository = generalParameterizationRepository;
    }

    public String authenticate() {
        final String token = getAuthenticationToken();
        return "Bearer " + token;
    }

    public List<Integer> getPaymentDays() {
        String[] days = paymentDays.split(",");
        List<Integer> convertedDays = new ArrayList<>();
        for (String s : days) {
            convertedDays.add(Integer.valueOf(s.trim()));
        }
        return convertedDays;
    }

    private String getAuthenticationToken() {
        String authUrl = getNetUrl + "/auth/oauth/v2/token";
        String auth = Base64.getEncoder().encodeToString((getNetClientId + ":" + getNetClientSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN, MediaType.ALL));
        headers.setBasicAuth(auth);

        HttpEntity<String> request =
                new HttpEntity<>("scope=oob&grant_type=client_credentials", headers);

        String getNetToken = restTemplate.postForObject(authUrl, request, String.class);

        assert getNetToken != null;
        return JsonParser.parseString(getNetToken).getAsJsonObject().get("access_token").getAsString();
    }

    @Transactional
    public void subscribeToPlanWithCreditCard(CardInput cardInput, Client holder, SubscribedPlan subscribedPlan) {
        String getNetToken = getAuthenticationToken();
        HttpHeaders headers = buildHeaders(getNetToken);

        paymentMonthRepository.save(generatePaymentMonth(subscribedPlan));

        String customerId = findClient(headers, holder);
        if (customerId == null) {
            customerId = createClient(headers, holder);
        }

        CardDTO card = getFromVault(headers, holder);
        if (cardInput == null && card == null) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NO_CARD_FOUND_OR_SAVED);
        }
        if (cardInput != null) {
            if (card != null) {
                removeFromVault(headers, card);
            }
            card = saveInVault(headers, cardInput, holder);
        }

        String planId = createPlan(headers, holder, subscribedPlan);

        createSubscription(headers, customerId, planId, card);
    }

    //APPLICATION_JSON_UTF8 is deprecated as of Spring Web 5.2, but we're using it following GetNet's documentation
    @SuppressWarnings("deprecation")
    private HttpHeaders buildHeaders(String getNetToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setBearerAuth(getNetToken);
        headers.set("seller_id", getNetSellerId);

        return headers;
    }

    @Transactional
    public PaymentMonth generatePaymentMonth(SubscribedPlan subscribedPlan) {
        LocalDateTime now = LocalDateTime.now();
        Optional<PaymentMonth> paymentMonthOptional = paymentMonthRepository.findBySubscribedPlanAndMonthAndYear(subscribedPlan, now.getMonthValue(), now.getYear());
        if (paymentMonthOptional.isPresent()) {
            return paymentMonthOptional.get();
        }
        PaymentMonth paymentMonth = new PaymentMonth();
        paymentMonth.setMonth(now.getMonthValue());
        paymentMonth.setYear(now.getYear());
        paymentMonth.setSubscribedPlan(subscribedPlan);
        paymentMonth.setPaid(false);

        return paymentMonth;
    }

    @Transactional
    public PaymentMonth generatePaymentMonth(SubscribedPlan subscribedPlan, LocalDateTime exactLocalDateTime) {
        Optional<PaymentMonth> paymentMonthOptional = paymentMonthRepository.findBySubscribedPlanAndMonthAndYear(subscribedPlan, exactLocalDateTime.getMonthValue(), exactLocalDateTime.getYear());
        if (paymentMonthOptional.isPresent()) {
            return paymentMonthOptional.get();
        }
        PaymentMonth paymentMonth = new PaymentMonth();
        paymentMonth.setMonth(exactLocalDateTime.getMonthValue());
        paymentMonth.setYear(exactLocalDateTime.getYear());
        paymentMonth.setSubscribedPlan(subscribedPlan);
        paymentMonth.setPaid(false);

        return paymentMonth;
    }

    private String findClient(HttpHeaders headers, Client holder) {
        try {
            String findClientUrl = getNetUrl + "/v1/customers/{holderId}";

            HttpEntity<String> request = new HttpEntity<>(null, headers);

            ResponseEntity<String> clientInfo = restTemplate.exchange(findClientUrl, HttpMethod.GET, request, String.class, holder.getId());
            String client = clientInfo.getBody();
            if (client == null) {
                return null;
            }
            return JsonParser.parseString(client).getAsJsonObject().get("customer_id").getAsString();
        } catch (final HttpClientErrorException.NotFound error) {
            return null;
        }
    }

    private String createClient(HttpHeaders headers, Client holder) {
        String clientUrl = getNetUrl + "/v1/customers";

        JsonObject clientBody = new JsonObject();

        clientBody.addProperty("seller_id", getNetSellerId);
        clientBody.addProperty("first_name", holder.getName());
        clientBody.addProperty("last_name", "teste");
        clientBody.addProperty("customer_id", holder.getId());
        clientBody.addProperty("document_type", "CPF");
        clientBody.addProperty("document_number", holder.getCPF());

        HttpEntity<String> request =
                new HttpEntity<>(clientBody.toString(), headers);

        String response = restTemplate.postForObject(clientUrl, request, String.class);

        assert response != null;
        return JsonParser.parseString(response).getAsJsonObject().get("customer_id").getAsString();
    }

    private CardDTO getFromVault(HttpHeaders headers, Client holder) {
        try {
            String vaultUrl = getNetUrl + "/v1/cards?customer_id={holderId}";

            HttpEntity<CardDTO> request =
                    new HttpEntity<>(null, headers);

            final CardDTOWrapper wrapper = restTemplate.exchange(vaultUrl, HttpMethod.GET, request, CardDTOWrapper.class, holder.getId().toString()).getBody();
            assert wrapper != null;

            List<CardDTO> cards = wrapper.getCards();
            if (cards == null) {
                return null;
            }
            return cards.get(0);
        } catch (HttpClientErrorException.NotFound error) {
            return null;
        }
    }

    private void removeFromVault(HttpHeaders headers, CardDTO card) {
        String vaultUrl = getNetUrl + "/v1/cards/{cardId}";

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        restTemplate.exchange(vaultUrl, HttpMethod.DELETE, request, Void.class, card.getCardId());
    }

    private CardDTO saveInVault(HttpHeaders headers, CardInput cardInput, Client holder) {
        String vaultUrl = getNetUrl + "/v1/cards";
        CardInputDTO cardInputDTO = new CardInputDTO(cardInput, holder.getId().toString());

        HttpEntity<CardInputDTO> request =
                new HttpEntity<>(cardInputDTO, headers);

        CardDTO response = restTemplate.postForObject(vaultUrl, request, CardDTO.class);
        assert response != null;

        response.setCardholderName(cardInput.getCardholderName());
        response.setExpirationMonth(cardInput.getExpirationMonth());
        response.setExpirationYear(cardInput.getExpirationYear());

        return response;
    }

    private String createPlan(HttpHeaders headers, Client holder, SubscribedPlan subscribedPlan) {
        String planUrl = getNetUrl + "/v1/plans";

        GetNetPeriodDTO getNetPeriodDTO = new GetNetPeriodDTO();
        GetNetPlanDTO getNetPlanDTO = new GetNetPlanDTO(getNetSellerId, holder.getCPF(), (int) (subscribedPlan.getValue() * 100), getNetPeriodDTO);

        HttpEntity<GetNetPlanDTO> request =
                new HttpEntity<>(getNetPlanDTO, headers);

        String response = restTemplate.postForObject(planUrl, request, String.class);
        assert response != null;

        return JsonParser.parseString(response).getAsJsonObject().get("plan_id").getAsString();
    }

    private void createSubscription(HttpHeaders headers, String customerId, String planId, CardDTO card) {
        String subscriptionUrl = getNetUrl + "/v1/subscriptions";

        GetNetSubscriptionCardDTO getNetSubscriptionCardDTO = new GetNetSubscriptionCardDTO(card);
        GetNetCreditDTO getNetCreditDTO = new GetNetCreditDTO("FULL", 1, getNetSubscriptionCardDTO);
        GetNetPaymentTypeDTO getNetPaymentTypeDTO = new GetNetPaymentTypeDTO(getNetCreditDTO);
        GetNetSubscriptionBodyDTO getNetSubscriptionBodyDTO = new GetNetSubscriptionBodyDTO(getNetPaymentTypeDTO);
        GetNetSubscriptionDTO getNetSubscriptionDTO = new GetNetSubscriptionDTO(getNetSellerId, customerId, planId, getNetSubscriptionBodyDTO);

        HttpEntity<GetNetSubscriptionDTO> request =
                new HttpEntity<>(getNetSubscriptionDTO, headers);

        final CreditCardSubscriptionReturnWrapper response = restTemplate.postForObject(subscriptionUrl, request, CreditCardSubscriptionReturnWrapper.class);
        if (response != null) {
            if (response.getStatus() == null) {
                LOGGER.error("Null status from getnet");
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NULL_RESPONSE_FROM_GETNET);
            }
            if (response.getStatus().equals("failed")) {
                final String errorDescription = response.getPayment().getError().getDetails().get(0).getDescription();
                LOGGER.error("Failed to subscribe on getnet with error: {}", errorDescription);
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.PAYMENT_FAILED_ON_GETNET(errorDescription));
            }
        } else {
            LOGGER.error("Null response from getnet");
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NULL_RESPONSE_FROM_GETNET);
        }
        LOGGER.info("Credit card subscription on getnet successfully with response: {}", response.getStatusDetails());
    }

    @Transactional
    public void subscribeToPlanWithPaymentSlip(Client holder, SubscribedPlan subscribedPlan) {
        String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);

        int subscribedPlanValue = (int) (subscribedPlan.getValue() * 100);

        LocalDate expirationDate = LocalDate.now().plusDays(1);

        GetNetPaymentSlipReturnDTO paymentSlip = requestPaymentSlip(holder, subscribedPlan, headers, subscribedPlanValue, expirationDate);

        PaymentMonth paymentMonth = generatePaymentMonth(subscribedPlan);

        if(paymentMonth.getId() == null) {
            paymentMonth = paymentMonthRepository.save(paymentMonth);
        }
        savePaymentSlipHistory(paymentSlip, subscribedPlan, paymentMonth);

        mailService.send(
                vidaPlanoMail,
                holder.getEmail(),
                paymentSlipMailSubject,
                paymentSlipMailBody.replace("<getNetUrl>", getNetUrl).replace("<paymentSlipId>", paymentSlip.getPaymentId())
        );
    }

    private double calculateValueWithDiscount(double value, double discount, int parcelQuantity) {
        return value * parcelQuantity - (value * parcelQuantity * discount);
    }

    public void generateCreditCardPaymentForAnticipation(Client holder, SubscribedPlan subscribedPlan, int parcelQuantity, double subscribedPlanValue) {
        String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);

        LocalDateTime lastActualDateTime = subscribedPlan.getLastPayment();
        List<PaymentMonth> paymentMonths = new ArrayList<>();
        for (int i = 0; i < parcelQuantity; i++) {
            paymentMonths.add(generatePaymentMonth(subscribedPlan, lastActualDateTime));
            lastActualDateTime = lastActualDateTime.plusMonths(1);
        }

        paymentMonths = paymentMonthRepository.saveAll(paymentMonths);
        // convert the value in cents, for getnet standards
        GetNetCreditCardResponseDTO getNetCreditCardResponseDTO = requestCreditCardPayment(holder, subscribedPlan, headers, (int) (subscribedPlanValue * 100));

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setStatus(getNetCreditCardResponseDTO.getStatus());
        paymentHistory.setValue(subscribedPlanValue);
        paymentHistory.setOrderId(getNetCreditCardResponseDTO.getOrderId());
        paymentHistory.setPaymentId(getNetCreditCardResponseDTO.getPaymentId());
        paymentHistory.setPaymentMonth(paymentMonths);
        paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
        paymentHistory.setCreatedAt(LocalDateTime.now());

        paymentHistoryRepository.save(paymentHistory);
    }

    public void generateAnticipationPayment(Client holder, SubscribedPlan subscribedPlan, int parcelQuantity, double subscribedPlanValue) {
        String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);

        LocalDate expirationDate = LocalDate.of(subscribedPlan.getLastPayment().getYear(), subscribedPlan.getLastPayment().getMonth().plus(1).getValue(), subscribedPlan.getPaymentDay());

        LocalDateTime lastActualDateTime = subscribedPlan.getLastPayment();
        List<PaymentMonth> paymentMonths = new ArrayList<>();
        for (int i = 0; i < parcelQuantity; i++) {
            //TODO adicionar data de vencimento do boleto
            paymentMonths.add(generatePaymentMonth(subscribedPlan, lastActualDateTime));
            lastActualDateTime = lastActualDateTime.plusMonths(1);
        }

        paymentMonths = paymentMonthRepository.saveAll(paymentMonths);
        // convert the value in cents, for getnet for getnet standards
        GetNetPaymentSlipReturnDTO paymentSlipReturn = requestPaymentSlip(holder, subscribedPlan, headers, (int) (subscribedPlanValue * 100), expirationDate);

        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setStatus(paymentSlipReturn.getStatus());
        paymentHistory.setValue(subscribedPlanValue);
        paymentHistory.setOrderId(paymentSlipReturn.getOrderId());
        paymentHistory.setPaymentId(paymentSlipReturn.getPaymentId());
        paymentHistory.setPaymentMonth(paymentMonths);
        paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
        paymentHistory.setCreatedAt(LocalDateTime.now());
        paymentHistory.setExpirationDate(expirationDate);

        paymentHistoryRepository.save(paymentHistory);

        mailService.send(
                vidaPlanoMail,
                holder.getEmail(),
                paymentSlipMailSubject,
                paymentSlipMailBody.replace("<getNetUrl>", getNetUrl).replace("<paymentSlipId>", paymentSlipReturn.getPaymentId())
        );
    }

    @Transactional
    public void anticipate(Client holder, SubscribedPlan subscribedPlan, Integer parcelQuantity, BigDecimal discount) {

        String getNetToken = getAuthenticationToken();
        HttpHeaders headers = buildHeaders(getNetToken);
        double subscribedPlanValue = calculateValueWithDiscount(subscribedPlan.getValue(), discount.doubleValue(), parcelQuantity);

        switch (PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType())) {
            case CREDIT_CARD:
                generateCreditCardPaymentForAnticipation(holder, subscribedPlan, parcelQuantity, subscribedPlanValue);
                break;
            case PAYMENT_SLIP:
                generateAnticipationPayment(holder, subscribedPlan, parcelQuantity, subscribedPlanValue);
                break;
        }
    }

    public GetNetCreditCardResponseDTO requestCreditCardPayment(Client holder, SubscribedPlan subscribedPlan, HttpHeaders headers, int subscribedPlanValue) {
        String paymentUrl = getNetUrl + "/v1/payments/credit";
        CardDTO cardDTO = getFromVault(headers, holder);
        GetNetAddressDTO getNetAddressDTO = new GetNetAddressDTO(new AddressDTO(subscribedPlan.getAddress()));
        GetNetOrderDTO getNetOrderDTO = new GetNetOrderDTO(UUID.randomUUID().toString());
        GetNetCustomerDTO getNetCustomerDTO = new GetNetCustomerDTO(holder.getName(), holder.getId().toString(), "CPF", holder.getCPF(), getNetAddressDTO);
        GetNetCreditPaymentDTO getNetCreditPaymentDTO = new GetNetCreditPaymentDTO(false, false, "FULL", 1, new CreditCardPaymentCardDTO(cardDTO));

        GetNetCreditCardSinglePaymentDTO getNetCreditCardSinglePaymentDTO = new GetNetCreditCardSinglePaymentDTO(
                getNetSellerId,
                subscribedPlanValue,
                "BRL",
                getNetOrderDTO,
                getNetCustomerDTO,
                getNetCreditPaymentDTO
        );

        HttpEntity<Object> request = new HttpEntity<>(getNetCreditCardSinglePaymentDTO, headers);
        return restTemplate.postForObject(paymentUrl, request, GetNetCreditCardResponseDTO.class);
    }


    public GetNetPaymentSlipReturnDTO requestPaymentSlip(Client holder, SubscribedPlan subscribedPlan, HttpHeaders headers, int subscribedPlanValue, LocalDate expirationDate) {
        String paymentSlipUrl = getNetUrl + "/v1/payments/boleto";
        GetNetAddressDTO getNetAddressDTO = new GetNetAddressDTO(new AddressDTO(subscribedPlan.getAddress()));
        GetNetPaymentSlipBodyDTO getNetPaymentSlipBodyDTO = new GetNetPaymentSlipBodyDTO("1", expirationDate);
        GetNetOrderDTO getNetOrderDTO = new GetNetOrderDTO(UUID.randomUUID().toString());
        GetNetCustomerDTO getNetCustomerDTO = new GetNetCustomerDTO(holder.getName(), holder.getId().toString(), "CPF", holder.getCPF(), getNetAddressDTO);
        GetNetPaymentSlipDTO getNetPaymentSlipDTO = new GetNetPaymentSlipDTO(getNetSellerId, subscribedPlanValue, "BRL", getNetOrderDTO, getNetPaymentSlipBodyDTO, getNetCustomerDTO);

        HttpEntity<GetNetPaymentSlipDTO> request =
                new HttpEntity<>(getNetPaymentSlipDTO, headers);

        return restTemplate.postForObject(paymentSlipUrl, request, GetNetPaymentSlipReturnDTO.class);
    }

    private void savePaymentSlipHistory(GetNetPaymentSlipReturnDTO paymentSlip, SubscribedPlan subscribedPlan, PaymentMonth paymentMonth) {
        List<PaymentMonth> paymentMonths = new ArrayList<>();
        paymentMonths.add(paymentMonth);
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setCreatedAt(LocalDateTime.now());
        paymentHistory.setOrderId(paymentSlip.getOrderId());
        paymentHistory.setValue(subscribedPlan.getValue());
        paymentHistory.setStatus(paymentSlip.getStatus());
        paymentHistory.setPaymentId(paymentSlip.getPaymentId());
        paymentHistory.setPaymentMonth(paymentMonths);
        paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
        String[] expirationDate = paymentSlip.getPaymentBoletoDetail().getExpirationDate().split("/");
        paymentHistory.setExpirationDate(LocalDate.of(Integer.parseInt(expirationDate[2]), Integer.parseInt(expirationDate[1]), Integer.parseInt(expirationDate[0])));

        paymentHistoryRepository.save(paymentHistory);
    }

// DEBUG METHOD
//    private void savePaymentSlipHistory(GetNetPaymentSlipReturnDTO paymentSlip, SubscribedPlan subscribedPlan, PaymentMonth paymentMonth)
//        List<PaymentMonth> paymentMonths = new ArrayList<>();
//        paymentMonths.add(paymentMonth);
//        PaymentHistory paymentHistory = new PaymentHistory();
//        paymentHistory.setCreatedAt(LocalDateTime.now());
//        paymentHistory.setOrderId(paymentSlip.getOrderId());
//        paymentHistory.setValue(subscribedPlan.getValue());
//        paymentHistory.setStatus(paymentSlip.getStatus());
//        paymentHistory.setPaymentId(paymentSlip.getPaymentId());
//        paymentHistory.setPaymentMonth(paymentMonths);
//        paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
//        EnviarLoteRpsResposta enviarLoteRpsResposta = sendPostInvoice(subscribedPlan.getBeneficiary(), subscribedPlan, paymentHistory);
//        paymentHistory.setInvoiceProtocolNumber(enviarLoteRpsResposta.getProtocolo());
//
//
//        PaymentHistory paymentHistory1 = paymentHistoryRepository.save(paymentHistory);
//
//        UncheckedInvoice uncheckedInvoice = new UncheckedInvoice();
//        uncheckedInvoice.setPaymentHistory(paymentHistory1);
//
//        uncheckedInvoiceRepository.save(uncheckedInvoice);
//    }

    public void cancelCreditCardSubscription(String reason, Client holder) {
        final String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);

        String subscriptionId = getSubscriptionId(headers, holder);

        String url = getNetUrl + "/v1/subscriptions/" + subscriptionId + "/cancel";

        GetNetCancelSubscriptionDTO getNetCancelSubscriptionDTO = new GetNetCancelSubscriptionDTO(getNetSellerId, reason);

        HttpEntity<GetNetCancelSubscriptionDTO> request =
                new HttpEntity<>(getNetCancelSubscriptionDTO, headers);

        CreditCardSubscriptionReturnWrapper response = restTemplate.postForObject(url, request, CreditCardSubscriptionReturnWrapper.class);
        if (response == null) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NULL_RESPONSE_FROM_GETNET);
        } else if (!response.getStatus().equals("canceled")) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.FAILED_TO_CANCEL_CLIENT_GETNET);
        } else if (response.getStatus().equals("canceled")) {
            LOGGER.info("User subscription was cancelled on getnet successfully with status: {}", response.getStatus());
        }
    }

    private String getSubscriptionId(HttpHeaders headers, Client holder) {
        final String url = getNetUrl + "/v1/subscriptions?page=1&limit=1&sort=create_date&sort_type=desc&customer_id=" + holder.getId();

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        ResponseEntity<SubscriptionDTOWrapper> clientInfo = restTemplate.exchange(url, HttpMethod.GET, request, SubscriptionDTOWrapper.class);

        if (clientInfo.getBody() == null) {
            throw new Error("getSubscriptionId");
        }

        return clientInfo.getBody().getSubscriptions().get(0).getSubscription().getSubscriptionId();
    }

    public void paymentSlipGenerated(PaymentSlipGenerationParameters parameters) {
        PaymentHistory paymentHistory =
                paymentHistoryRepository.findByPaymentId(parameters.getPayment_id()).orElseThrow();
        paymentHistory.setPaymentSlipId(parameters.getId());
        paymentHistory.setStatus(parameters.getStatus());
        paymentHistoryRepository.save(paymentHistory);
    }

    public void paymentSlipFinished(PaymentSlipFinishedParameters parameters) {
        PaymentHistory paymentHistory =
                paymentHistoryRepository.findByPaymentSlipId(parameters.getId()).orElseThrow();
        if (parameters.getStatus().equals("PAID")) {
            paymentHistory.setLiquidationDate(LocalDateTime.now());
            List<PaymentMonth> paymentMonthsToChange =
                    paymentHistory
                            .getPaymentMonth()
                            .stream()
                            .peek(paymentMonth -> paymentMonth.setPaid(true))
                            .collect(Collectors.toList());
            paymentMonthRepository.saveAll(paymentMonthsToChange);
            SubscribedPlan subscribedPlan = paymentMonthsToChange.get(0).getSubscribedPlan();

            EnviarLoteRpsResposta enviarLoteRpsResposta = sendPostInvoice(subscribedPlan.getBeneficiary(), subscribedPlan, paymentHistory);
            paymentHistory.setInvoiceProtocolNumber(enviarLoteRpsResposta.getProtocolo());

            UncheckedInvoice uncheckedInvoice = new UncheckedInvoice();
            uncheckedInvoice.setPaymentHistory(paymentHistory);

            uncheckedInvoiceRepository.save(uncheckedInvoice);

            if (subscribedPlan.getAnticipationHaveDependent() != null && subscribedPlan.getAnticipationHaveDependent()) {
                subscribedPlan.setAnticipationLastPayment(subscribedPlan.getAnticipationLastPayment().plusMonths(paymentMonthsToChange.size()));
            } else {
                subscribedPlan.setLastPayment(subscribedPlan.getLastPayment().plusMonths(paymentMonthsToChange.size()));
            }
            uncheckedInvoiceRepository.save(uncheckedInvoice);

            subscribedPlanRepository.save(subscribedPlan);
        }
        paymentHistory.setStatus(parameters.getStatus());
        paymentHistoryRepository.save(paymentHistory);
    }

    public void recurrenceUpdated(RecurrenceUpdatedParameters parameters) {
        SubscribedPlan subscribedPlan =
                subscribedPlanRepository
                        .findByBeneficiaryIdAndActiveIsTrue(Long.parseLong(parameters.getCustomer_id()))
                        .orElseThrow();

        Optional<PaymentHistory> optionalPaymentHistory = paymentHistoryRepository.findByPaymentId(parameters.getPayment_id());
        PaymentHistory paymentHistory;

        if (optionalPaymentHistory.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            List<PaymentMonth> paymentMonths = new ArrayList<>();
            PaymentMonth paymentMonth =
                    paymentMonthRepository
                            .findBySubscribedPlanAndMonthAndYear(subscribedPlan, now.getMonthValue(), now.getYear())
                            .orElseThrow();
            paymentMonths.add(paymentMonth);
            paymentHistory = new PaymentHistory();
            paymentHistory.setStatus(parameters.getStatus());
            paymentHistory.setPaymentId(parameters.getPayment_id());
            paymentHistory.setOrderId(parameters.getOrder_id());
            paymentHistory.setValue(parameters.getAmount() / 100.0);
            paymentHistory.setCreatedAt(LocalDateTime.now());
            paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
            paymentHistory.setPaymentMonth(paymentMonths);
        } else {
            paymentHistory = optionalPaymentHistory.get();
            paymentHistory.setStatus(parameters.getStatus());
        }

        if (parameters.getStatus().equals("CONFIRMED")) {
            paymentHistory.setLiquidationDate(LocalDateTime.now());
            List<PaymentMonth> paymentMonths =
                    paymentHistory
                            .getPaymentMonth()
                            .stream()
                            .peek(paymentMonth -> paymentMonth.setPaid(true))
                            .collect(Collectors.toList());

            EnviarLoteRpsResposta enviarLoteRpsResposta = sendPostInvoice(subscribedPlan.getBeneficiary(), subscribedPlan, paymentHistory);
            paymentHistory.setInvoiceProtocolNumber(enviarLoteRpsResposta.getProtocolo());
            paymentHistoryRepository.save(paymentHistory);

            UncheckedInvoice uncheckedInvoice = new UncheckedInvoice();
            uncheckedInvoice.setPaymentHistory(paymentHistory);

            uncheckedInvoiceRepository.save(uncheckedInvoice);

            if (subscribedPlan.getAnticipationHaveDependent() != null && subscribedPlan.getAnticipationHaveDependent()) {
                subscribedPlan.setAnticipationLastPayment(subscribedPlan.getAnticipationLastPayment().plusMonths(paymentMonths.size()));
            } else {
                subscribedPlan.setLastPayment(subscribedPlan.getLastPayment().plusMonths(paymentMonths.size()));
            }

            paymentMonthRepository.saveAll(paymentMonths);
            subscribedPlanRepository.save(subscribedPlan);
            return;
        }
        paymentHistoryRepository.save(paymentHistory);
    }

    public void creditCardUpdated(CreditCardUpdatedParameters parameters) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findByPaymentId(parameters.getPayment_id()).orElseThrow();

        paymentHistory.setStatus(parameters.getStatus());

        if (parameters.getStatus().equals("CONFIRMED")) {
            SubscribedPlan subscribedPlan =
                    subscribedPlanRepository
                            .findByBeneficiaryIdAndActiveIsTrue(Long.parseLong(parameters.getCustomer_id()))
                            .orElseThrow();
            paymentHistory.setLiquidationDate(LocalDateTime.now());

            EnviarLoteRpsResposta enviarLoteRpsResposta = sendPostInvoice(subscribedPlan.getBeneficiary(), subscribedPlan, paymentHistory);
            paymentHistory.setInvoiceProtocolNumber(enviarLoteRpsResposta.getProtocolo());

            UncheckedInvoice uncheckedInvoice = new UncheckedInvoice();
            uncheckedInvoice.setPaymentHistory(paymentHistory);

            uncheckedInvoiceRepository.save(uncheckedInvoice);
            List<PaymentMonth> paymentMonths =
                    paymentHistory
                            .getPaymentMonth()
                            .stream()
                            .peek(paymentMonth -> paymentMonth.setPaid(true))
                            .collect(Collectors.toList());
            subscribedPlan.setLastPayment(subscribedPlan.getLastPayment().plusMonths(paymentMonths.size()));
            paymentMonthRepository.saveAll(paymentMonths);
            subscribedPlanRepository.save(subscribedPlan);
        }
        paymentHistoryRepository.save(paymentHistory);
    }

    public void debitCardUpdated(DebitCardUpdatedParameters parameters) {
        //TODO
    }

    public void updateCreditCard(CardInput cardInput, Client client) {
        String getNetToken = getAuthenticationToken();
        HttpHeaders headers = buildHeaders(getNetToken);

        final String subscriptionId = getSubscriptionId(headers, client);
        final String url = getNetUrl + "/v1/subscriptions/" + subscriptionId + "/paymentType/credit/card";

        // removes from vault
        CardDTO cardDTO = getFromVault(headers, client);
        if (cardDTO != null) {
            removeFromVault(headers, cardDTO);
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NO_CARD_FOUND_OR_SAVED);
        }
        saveInVault(headers, cardInput, client);

        HttpEntity<Object> request = new HttpEntity<>(null, headers);

        restTemplate.exchange(url, HttpMethod.PATCH, request, GetNetUpdatePaymentDTO.class);
    }

    @Transactional
    public void changePaymentSlipToCreditCard(CardInput cardInput, Client client, SubscribedPlan subscribedPlan) {
        subscribeToPlanWithCreditCard(cardInput, client, subscribedPlan);

        subscribedPlan.setPaymentType(PaymentTypeDTO.CREDIT_CARD.name());
        subscribedPlanRepository.save(subscribedPlan);
    }

    public void changeCreditCardToPaymentSlip(Client client, SubscribedPlan subscribedPlan, int paymentDay) {
        String getNetToken = getAuthenticationToken();
        HttpHeaders headers = buildHeaders(getNetToken);

        CardDTO cardDTO = getFromVault(headers, client);

        if (cardDTO != null) {
            removeFromVault(headers, cardDTO);
        } else {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Payment.NO_CARD_FOUND_OR_SAVED);
        }

        cancelCreditCardSubscription("Usuario trocou para pagamento em boleto", client);

        subscribedPlan.setPaymentDay(paymentDay);
        subscribedPlan.setPaymentType(PaymentTypeDTO.PAYMENT_SLIP.name());
        subscribedPlanRepository.save(subscribedPlan);
    }

    @Transactional
    public void registerNewPaymentSlip(Client holder, SubscribedPlan subscribedPlan, Integer month, Integer year) {
        String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);

        PaymentMonth paymentMonth = paymentMonthRepository
                .findBySubscribedPlanAndMonthAndYear(subscribedPlan, month, year)
                .orElseThrow();

        int subscribedPlanValue = (int) (subscribedPlan.getValue() * 100);

        LocalDate expirationDate = LocalDate.of(subscribedPlan.getLastPayment().getYear(), subscribedPlan.getLastPayment().getMonth().plus(1).getValue(), subscribedPlan.getPaymentDay());

        GetNetPaymentSlipReturnDTO paymentSlipReturn = requestPaymentSlip(holder, subscribedPlan, headers, subscribedPlanValue, expirationDate);
        List<PaymentMonth> paymentMonths = new ArrayList<>();
        paymentMonths.add(paymentMonth);
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setStatus(paymentSlipReturn.getStatus());
        paymentHistory.setValue(subscribedPlanValue / 100.0);
        paymentHistory.setOrderId(paymentSlipReturn.getOrderId());
        paymentHistory.setPaymentId(paymentSlipReturn.getPaymentId());
        paymentHistory.setPaymentMonth(paymentMonths);
        paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
        paymentHistory.setCreatedAt(LocalDateTime.now());
        String[] expirationDateReturn = paymentSlipReturn.getPaymentBoletoDetail().getExpirationDate().split("/");
        paymentHistory.setExpirationDate(LocalDate.of(Integer.parseInt(expirationDateReturn[2]), Integer.parseInt(expirationDateReturn[1]), Integer.parseInt(expirationDateReturn[0])));
        paymentHistoryRepository.save(paymentHistory);

        mailService.send(
                vidaPlanoMail,
                holder.getEmail(),
                paymentSlipMailSubject,
                paymentSlipMailBody.replace("<getNetUrl>", getNetUrl).replace("<paymentSlipId>", paymentSlipReturn.getPaymentId())
        );
    }

    public List<PaymentSituationDTO> getClientPaymentSituation(Long clientId) {
        final SubscribedPlan plan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(clientId)
                .orElseThrow(() -> new ErrorOnProcessingRequestException(PAYMENT_SITUATION_NOT_FOUND));

        List<PaymentSituationDTO> dtos = new ArrayList<>();
        final List<PaymentMonth> paymentMonthList = paymentMonthRepository.findAllBySubscribedPlan(plan);

        for (PaymentMonth month : paymentMonthList) {
            final Optional<PaymentHistory> paymentHistory = paymentHistoryRepository.findTopByPaymentMonthOrderByCreatedAtDesc(month);
            PaymentSituationDTO dto;
            if (paymentHistory.isEmpty()) {
                dto = new PaymentSituationDTO();
                dto.setMonth(month.getMonth());
                dto.setYear(month.getYear());
                dto.setLiquidationDate(null);
                dto.setStatus("PENDING");
                dto.setPaymentType(plan.getPaymentType());
                dto.setValue(plan.getValue());
            } else {
                dto = new PaymentSituationDTO(month, paymentHistory.get());
            }

            dtos.add(dto);

        }

        return dtos;
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void sendPaymentSlips() {
        List<SubscribedPlan> subscribedPlanList =
                subscribedPlanRepository.findAllByPaymentTypeAndActiveIsTrue(PaymentTypeDTO.PAYMENT_SLIP.name());
        LocalDateTime now = LocalDateTime.now();
        subscribedPlanList = subscribedPlanList
                .stream()
                .filter(subscribedPlan -> {
                    if (subscribedPlan.getValue() == 0) {
                        return false;
                    }
                    LocalDateTime lastPayment =
                            subscribedPlan.getAnticipationHaveDependent() != null && subscribedPlan.getAnticipationHaveDependent() ?
                                    subscribedPlan.getAnticipationLastPayment() :
                                    subscribedPlan.getLastPayment();
                    Integer paymentSlipDate = subscribedPlan.getPaymentDay();
                    LocalDateTime paymentDate = now.withDayOfMonth(paymentSlipDate);
                    if (now.getDayOfMonth() > paymentSlipDate) {
                        paymentDate = paymentDate.plusMonths(1);
                    }
                    return paymentDate.isAfter(lastPayment) && now.plusDays(7).getDayOfYear() == paymentDate.getDayOfYear();
                }).collect(Collectors.toList());

        String getNetToken = getAuthenticationToken();

        HttpHeaders headers = buildHeaders(getNetToken);
        for (SubscribedPlan subscribedPlan : subscribedPlanList) {
            LocalDate expirationDate = LocalDate.of(subscribedPlan.getLastPayment().getYear(), subscribedPlan.getLastPayment().getMonth().plus(1).getValue(), subscribedPlan.getPaymentDay());
            PaymentMonth paymentMonth =
                    paymentMonthRepository
                            .findBySubscribedPlanAndMonthAndYear(subscribedPlan, now.getMonthValue(), now.getYear())
                            .orElseThrow();
            int subscribedPlanValue = (int) (subscribedPlan.getValue() * 100);
            Client holder = subscribedPlan.getBeneficiary();
            GetNetPaymentSlipReturnDTO paymentSlip = requestPaymentSlip(
                    holder,
                    subscribedPlan,
                    headers,
                    subscribedPlanValue,
                    expirationDate
            );

            List<PaymentMonth> paymentMonths = new ArrayList<>();
            paymentMonths.add(paymentMonth);

            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setCreatedAt(now);
            paymentHistory.setPaymentId(paymentSlip.getPaymentId());
            paymentHistory.setValue(subscribedPlan.getValue());
            paymentHistory.setOrderId(paymentSlip.getOrderId());
            paymentHistory.setPaymentType(PaymentTypeDTO.valueOf(subscribedPlan.getPaymentType()));
            paymentHistory.setStatus(paymentSlip.getStatus());
            paymentHistory.setPaymentMonth(paymentMonths);
            String[] expirationDateReturn = paymentSlip.getPaymentBoletoDetail().getExpirationDate().split("/");
            paymentHistory.setExpirationDate(LocalDate.of(Integer.parseInt(expirationDateReturn[2]), Integer.parseInt(expirationDateReturn[1]), Integer.parseInt(expirationDateReturn[0])));

            paymentHistoryRepository.save(paymentHistory);
            mailService.send(
                    vidaPlanoMail,
                    holder.getEmail(),
                    paymentSlipMailSubject,
                    paymentSlipMailBody
                            .replace("<getNetUrl>", getNetUrl)
                            .replace("<paymentSlipId", paymentSlip.getPaymentId())
            );
        }
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void restartPlans() {
        List<SubscribedPlan> subscribedPlans = subscribedPlanRepository
                .findByWaitingForLastPaymentDateIsTrueAndActiveIsTrueAndAnticipationHaveDependentIsFalseAndPastLastPayment();
        for (SubscribedPlan subscribedPlan : subscribedPlans) {
            subscribeToPlanWithCreditCard(null, subscribedPlan.getBeneficiary(), subscribedPlan);
            subscribedPlan.setWaitingForLastPaymentDate(false);
            subscribedPlanRepository.save(subscribedPlan);
        }
    }

    public String getPaymentSlipUrl(String paymentId) {
        return getNetUrl + "v1/payments/boleto/" + paymentId + "/pdf";
    }

    @Scheduled(cron = "${cron.cron-every-month}")
    public void createPaymentMonth() {
        List<SubscribedPlan> subscribedPlanList = subscribedPlanRepository.findAllByActiveIsTrue();
        List<PaymentMonth> paymentMonthList =
                subscribedPlanList.stream().map(this::generatePaymentMonth).collect(Collectors.toList());
        paymentMonthRepository.saveAll(paymentMonthList);
    }

    public boolean checkUserPaymentInRange(SubscribedPlan subscribedPlan, LocalDate startDate, LocalDate endDate) {
        List<PaymentMonth> paymentMonths = paymentMonthRepository.findAllBySubscribedPlan(subscribedPlan);
        startDate = startDate.withDayOfMonth(1);
        endDate = endDate.withDayOfMonth(2);
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
            boolean found = false;
            for (PaymentMonth p : paymentMonths) {
                if (p.getMonth() == date.getMonthValue() && p.getYear() == date.getYear()) {
                    found = true; // entry found.
                    if (!p.isPaid()) {
                        return false; // a entry is not paid, returns false.
                    }
                }
                if (found) break;
            }
            if (!found) return false; // no entry for the requested month/year
        }
        return true; // all entries were found and all are paid
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void returnAnticipatedContractsWithExtraDependentsToNormalcy() {
        List<SubscribedPlan> subscribedPlanList = subscribedPlanRepository.findShouldUpdateContracts();
        for (SubscribedPlan subscribedPlan : subscribedPlanList) {
            subscribedPlan.setAnticipationLastPayment(null);
            subscribedPlan.setAnticipationHaveDependent(false);
            subscribedPlan.setWaitingForLastPaymentDate(false);
            Plan plan = planService.buildPlanWithAgeRanges(subscribedPlan.getPlan());
            Client holder = subscribedPlan.getBeneficiary();
            List<Client> dependents = clientRepository.findAllByHolder(holder);
            dependents.add(holder);
            subscribedPlan.setValue(planService.calculatePrice(dependents, plan.getPlanPriceTable().getAgeRanges(), plan.getDependentPriceTable().getAgeRanges()));
            dependents = dependents.stream().peek(dependent -> dependent.setAddedAfterAnticipation(false)).collect(Collectors.toList());
            resubscribe(subscribedPlan, holder, dependents);
        }
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void resubscribeIfWaitingForLastPaymentAndAnticipationHaveDependent() {
        List<SubscribedPlan> subscribedPlanList = subscribedPlanRepository.findByAnticipationHaveDependentIsTrueAndActiveIsTrueAndWaitingForLastPaymentDateIsTrue();
        LocalDateTime now = LocalDateTime.now();
        for (SubscribedPlan subscribedPlan : subscribedPlanList) {
            if (subscribedPlan.getAnticipationLastPayment().isBefore(now)) {
                subscribedPlan.setWaitingForLastPaymentDate(false);
                if (subscribedPlan.getValue() != 0) {
                    subscribeToPlanWithCreditCard(null, subscribedPlan.getBeneficiary(), subscribedPlan);
                }
                subscribedPlanRepository.save(subscribedPlan);
            }
        }
    }

    public void resubscribe(SubscribedPlan subscribedPlan, Client holder, List<Client> dependents) {
        subscribedPlan = subscribedPlanRepository.save(subscribedPlan);
        switch (subscribedPlan.getPaymentType()) {
            case "CREDIT_CARD":
                if ((subscribedPlan.getWaitingForLastPaymentDate() != null && !subscribedPlan.getWaitingForLastPaymentDate()) || (subscribedPlan.getAnticipationHaveDependent() != null && subscribedPlan.getAnticipationHaveDependent())) {
                    cancelCreditCardSubscription("Alterado valor do plano", holder);
                    subscribedPlan.setWaitingForLastPaymentDate(false);
                    subscribedPlanRepository.save(subscribedPlan);
                }
                subscribeToPlanWithCreditCard(null, holder, subscribedPlan);
                break;
            case "PAYMENT_SLIP":
                subscribeToPlanWithPaymentSlip(holder, subscribedPlan);
                break;
            default:
        }

        clientRepository.saveAll(dependents);
    }

    private EnviarLoteRpsResposta sendPostInvoice(Client client, SubscribedPlan subscribedPlan, PaymentHistory paymentHistory) {
        Company company = companyRepository.getCompany();
        GeneralParameterization generalParameterization = generalParameterizationRepository.findAll().get(0);

        Long rpsId = company.getRpsIdentification();
        DecimalFormat df = new DecimalFormat("000000000000000.00");
        BigDecimal servicesValue = new BigDecimal(df.format(paymentHistory.getValue()));
        final Values values = new Values.Builder()
                .setServicesValue(servicesValue)
                .setAliquot(new BigDecimal(generalParameterization.getAliquot())) // the correct aliquot value is on frl_general table
                .build();

        final ServiceData serviceData = new ServiceData.Builder()
                .setValues(values)
                .setServiceListItemCode(company.getNfItemCode()) // todo check correct code here http://sped.rfb.gov.br/pagina/show/1601 | 25.03 – Planos ou convênio funerários.
                .setDiscrimination(company.getNfDiscrimination()) // Discriminação do conteúdo da NFS-e
                .setCityCode(Integer.parseInt(company.getCityCode())) // Código de identificação do município conforme tabela do IBGE
                .setMunicipalTaxCode(company.getNfMunicipalTaxCode())
                .build();

        // Taker data
        final CpfOrCnpj cpfOrCnpj = new CpfOrCnpj(client.getCPF(), true);
        final TakerInfo takerInfo = new TakerInfo(cpfOrCnpj, company.getMunicipalRegistry()); // todo check this info
        final Contact contact = new Contact(DataUtils.onlyNumber(client.getTelephone()), client.getEmail());
        Address clientAddress = subscribedPlan.getAddress();

        final FullAddress fullAddress = new FullAddress.Builder()
                .setNumber(clientAddress.getNumber())
                .setZipCode(Integer.parseInt(clientAddress.getZipCode().replace("-", ""))) // Must be an integer value for ginfes
                .setProvince(clientAddress.getProvince())
                .setStreet(clientAddress.getStreet())
                .setComplement(clientAddress.getComplement())
                .setNeighborhood(clientAddress.getNeighborhood())
                .setCityCode(Integer.parseInt(company.getCityCode())) // todo default value here is for Ribeirao Preto but must check this value
                .build();
        // End taker data

        LocalDateTime now = ZonedDateTime.now(ZoneId.of("Brazil/East")).toLocalDateTime().withNano(0);
        final RpsInfo info = new RpsInfo.Builder()
                .setRpsIdentification(new RpsIdentification(BigInteger.valueOf(rpsId), "00001", RpsType.RPS)) // todo check this values
                .setEmissionDate(now)
                .setOperationNature(RpsOperationNature.TAXATION_INSIDE_MUNICIPALITY) // todo check this value
                .setStatus(RpsStatus.NORMAL) // todo check this status
                .setServiceData(serviceData)
                .setProviderCnpj(company.getCnpj())
                .setProviderMunicipalRegistry(company.getMunicipalRegistry())
                .setTakerData(new TakerData(takerInfo, company.getName(), fullAddress, contact)) // todo check if company name is correct
                .build();

        rpsId += 1;
        company.setRpsIdentification(rpsId);
        companyRepository.save(company);
        final List<RpsInfo> rpsInfoList = List.of(info);

        PostInvoiceParameters postInvoiceParameters = new PostInvoiceParameters(new BigInteger("123456789"), company.getCnpj(), company.getMunicipalRegistry(), rpsInfoList); // todo check if UUID is the correct way to generate this id
        return invoiceService.postInvoice(postInvoiceParameters);
    }

    // Check all unsent invoices, extracts from ginfes using protocol number, send to client email and delete the invoice from database
    @Scheduled(fixedRateString = "${cron.cron-delay-minutes}")
    public void checkUncheckedInvoices() {
        LOGGER.info("Checking unchecked invoices...");
        List<UncheckedInvoice> uncheckedInvoices = uncheckedInvoiceRepository.getAllWithClientInformation();
        if (uncheckedInvoices.size() == 0) {
            return;
        }

        UncheckedInvoice uncheckedInvoice = uncheckedInvoices.get(0);

        final String companyCnpj = companyRepository.getCompany().getCnpj();
        final String companyMunicipalRegistry = companyRepository.getCompany().getMunicipalRegistry();

        GetInvoiceStatusParameters getInvoiceStatusParameters = new GetInvoiceStatusParameters.Builder()
                .setProviderCnpj(companyCnpj)
                .setProviderMunicipalRegistry(companyMunicipalRegistry)
                .setProtocol(uncheckedInvoice.getPaymentHistory().getInvoiceProtocolNumber())
                .build();

        ConsultarSituacaoLoteRpsResposta consultarSituacaoLoteRpsResposta = invoiceService.getInvoiceStatus(getInvoiceStatusParameters);
        Byte situation = consultarSituacaoLoteRpsResposta.getSituacao();
        //getSituacao 1-Waiting 2-Processing 3-Completed with error 4-Completed successfully
        switch (situation) {
            case 3:
                uncheckedInvoice.setError(true);
                uncheckedInvoiceRepository.save(uncheckedInvoice);
                break;
            case 4:
                GetInvoiceParameters getInvoiceParameters = new GetInvoiceParameters.Builder()
                        .setProviderCnpj(companyCnpj)
                        .setProviderMunicipalRegistry(companyMunicipalRegistry)
                        .setProtocol(uncheckedInvoice.getPaymentHistory().getInvoiceProtocolNumber())
                        .build();
                final String xmlContent = invoiceService.getInvoice(getInvoiceParameters);
                LOGGER.info("Successfully sent invoice with protocol: {}", getInvoiceParameters.getProtocol());
                File file = generateXMLFile(uncheckedInvoice.getPaymentHistory().getInvoiceProtocolNumber(), xmlContent);
                s3Service.uploadXml(file);
                final String clientEmail = uncheckedInvoice.getPaymentHistory().getPaymentMonth().get(0).getSubscribedPlan().getBeneficiary().getEmail();
                mailService.send(
                        nfseFrom,
                        clientEmail,
                        nfseSubject,
                        nfseBody,
                        file.getPath()
                );
                if (!file.delete()) {
                    LOGGER.warn("Something wrong happened when deleting file from system");
                }
                uncheckedInvoiceRepository.delete(uncheckedInvoice);
                LOGGER.info("Invoice sent to email and uploaded to S3 successfully");
                break;
            default:
                LOGGER.info("Current NFS-e with protocol: {} is waiting or processing", uncheckedInvoice.getPaymentHistory().getInvoiceProtocolNumber());
        }
    }

    // this method creates a file in the system that contains the xml content from the user NFS-e and returns the created file
    // with <protocolnumber>.xml pattern
    private File generateXMLFile(String protocol, String xmlContent) {
        try {
            FileWriter fileWriter = new FileWriter(protocol + ".xml");
            fileWriter.write(xmlContent);
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            LOGGER.error("Error generating NFS-e xml file with protocol number: {}", protocol);
        }
        File file = new File(protocol + ".xml");
        return file;
    }
}
