package br.com.monitoratec.farol.graphql.resolvers.mutation.user;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.responses.user.CommonResponseWithDischargeStatementURL;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.service.user.ClientService;
import br.com.monitoratec.farol.sql.model.company.Company;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.company.CompanyRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.lowagie.text.DocumentException;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class GenerateDischargeStatementForClientImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateDischargeStatementForClientImpl.class);

    private final MailService mailService;
    private final ClientService service;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final PaymentService paymentService;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final String mailFrom;
    private final String mailSubject;
    private final String mailBody;

    public GenerateDischargeStatementForClientImpl(
            MailService mailService,
            ClientService service,
            ClientRepository clientRepository,
            CompanyRepository companyRepository,
            PaymentService paymentService,
            SubscribedPlanRepository subscribedPlanRepository,
            @Value("${mail.discharge-statement-client-from}") String mailFrom,
            @Value("${mail.discharge-statement-subject}") String mailSubject,
            @Value("${mail.discharge-statement-client-body}") String mailBody) {
        this.mailService = mailService;
        this.service = service;
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.paymentService = paymentService;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.mailFrom = mailFrom;
        this.mailSubject = mailSubject;
        this.mailBody = mailBody;
    }

    public CompletableFuture<CommonResponseWithDischargeStatementURL> generateDischargeStatementForClient(
            Long clientId,
            String dischargeStatement,
            Date dateFrom,
            Date dateTo,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithDischargeStatementURL> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Client client = clientRepository.findByIdAndActiveTrue(clientId)
                            .orElseThrow(() -> new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND));

                    Company company = companyRepository.getCompany();

                    Optional<SubscribedPlan> optionalSubscribedPlan = subscribedPlanRepository.findByBeneficiaryIdAndActiveIsTrue(client.getId());
                    if (optionalSubscribedPlan.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.SUBSCRIBED_PLAN_NOT_FOUND);
                    }

                    if (!paymentService.checkUserPaymentInRange(optionalSubscribedPlan.get(), dateFrom.getDate(), dateTo.getDate())) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_HAVE_NOT_PAID);
                    }

                    String newDischargeStatement = dischargeStatement
                            .replace("$company_name", company.getName())
                            .replace("$company_address", company.getAddress())
                            .replace("$company_cnpj", company.getCnpj())
                            .replace("$client_name", client.getName())
                            .replace("$client_cpf", CpfUtils.formatCPF(client.getCPF()))
                            .replace("$local_date", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    try {
                        String dischargeStatementURL = service.generateDischargeStatement(newDischargeStatement);
                        if (client.getEmail() != null) {
                            final String newMailBody = mailBody.replace("<dischargeStatementURL>", dischargeStatementURL);
                            mailService.send(mailFrom, client.getEmail(), mailSubject, newMailBody);
                        }
                        CommonResponseWithDischargeStatementURL commonResponse = new CommonResponseWithDischargeStatementURL(StatusCodes.Success.User.DISCHARGE_STATEMENT_SUCCESSFULLY_SENT, dischargeStatementURL);
                        responsePromise.complete(commonResponse);
                    } catch (IOException | DocumentException e) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.ERROR_UPLOADING_DISCHARGE_STATEMENT);
                    }
                });
        return responsePromise;
    }

}
