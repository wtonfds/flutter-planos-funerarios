package br.com.monitoratec.farol.service.plan;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.log.ClientActionTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.payment.PaymentTypeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.graphql.model.input.document.DocumentsInput;
import br.com.monitoratec.farol.graphql.model.input.payment.CardInput;
import br.com.monitoratec.farol.graphql.model.input.user.ExtraDependentInput;
import br.com.monitoratec.farol.service.document.DocumentService;
import br.com.monitoratec.farol.service.log.LogService;
import br.com.monitoratec.farol.service.mail.MailService;
import br.com.monitoratec.farol.service.payment.PaymentService;
import br.com.monitoratec.farol.service.push.PushService;
import br.com.monitoratec.farol.service.sms.SmsService;
import br.com.monitoratec.farol.sql.model.generalParameterization.GeneralParameterization;
import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import br.com.monitoratec.farol.sql.model.price.PriceTableAgeRange;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.generalParameterization.GeneralParameterizationRepository;
import br.com.monitoratec.farol.sql.repository.location.AddressRepository;
import br.com.monitoratec.farol.sql.repository.subscribedPlan.SubscribedPlanRepository;
import br.com.monitoratec.farol.sql.repository.user.ClientRepository;
import br.com.monitoratec.farol.utils.data.CpfUtils;
import br.com.monitoratec.farol.utils.data.DataUtils;
import br.com.monitoratec.farol.utils.log.LogMessageBuilder;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanSubscriptionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlanSubscriptionService.class);

    private final ClientRepository clientRepository;
    private final SubscribedPlanRepository subscribedPlanRepository;
    private final AddressRepository addressRepository;
    private final LogService logService;
    private final PushService pushService;
    private final MailService mailService;
    private final String vidaPlanoEmail;
    private final String vidaPlanoEmailSubject;
    private final String vidaPlanoBody;
    private final String removedChildSubject;
    private final String removedChildBody;
    private final String smsTokenBody;
    private final PaymentService paymentService;
    private final GeneralParameterizationRepository generalParameterizationRepository;
    private final PlanService planService;
    private final SmsService smsService;
    private final DocumentService documentService;

    public PlanSubscriptionService(ClientRepository clientRepository, SubscribedPlanRepository subscribedPlanRepository,
                                   AddressRepository addressRepository, LogService logService, PushService pushService,
                                   PaymentService paymentService,
                                   DocumentService documentService,
                                   MailService mailService, @Value("${mail.reset-password-from}") String vidaPlanoEmail,
                                   @Value("${mail.removed-from-plan-subject}") String vidaPlanoEmailSubject,
                                   @Value("${mail.removed-from-plan-body}") String vidaPlanoBody,
                                   @Value("${mail.removed-child-subject}") String removedChildSubject, @Value("${mail.removed-child-body}") String removedChildBody,
                                   @Value("${sms.auth_token_sms_body}") String smsTokenBody,
                                   GeneralParameterizationRepository generalParameterizationRepository, PlanService planService, SmsService smsService) {
        this.clientRepository = clientRepository;
        this.subscribedPlanRepository = subscribedPlanRepository;
        this.addressRepository = addressRepository;
        this.paymentService = paymentService;
        this.logService = logService;
        this.pushService = pushService;
        this.mailService = mailService;
        this.vidaPlanoEmail = vidaPlanoEmail;
        this.vidaPlanoEmailSubject = vidaPlanoEmailSubject;
        this.vidaPlanoBody = vidaPlanoBody;
        this.documentService = documentService;
        this.removedChildSubject = removedChildSubject;
        this.removedChildBody = removedChildBody;
        this.smsTokenBody = smsTokenBody;
        this.generalParameterizationRepository = generalParameterizationRepository;
        this.planService = planService;
        this.smsService = smsService;
    }

    @Transactional
    public void addDependents(SubscribedPlan subscribedPlan, List<Client> dependents, Client holder) {
        subscribedPlanRepository.save(subscribedPlan);
        clientRepository.saveAll(dependents);

        // Logging
        for (Client entry : dependents) {
            final String logMessage = LogMessageBuilder.buildIncludedDependentMessage(holder.getCPF(), holder.getName(), entry.getName(), entry.getClientType());
            logService.logClientHistory(ClientActionTypeDTO.ADDED_DEPENDENTS, logMessage, holder.getCPF(), holder.getName());
        }
    }

    // Does not have holder or dependent documents in this flow
    @Transactional
    public void subscribe(SubscribedPlan subscribedPlan, List<Client> dependents, Client currentClient, CardInput cardInput, Address address) {
        LOGGER.info("Started creating plan subscription for client with id: {}", currentClient.getId());
        subscribedPlan.setAddress(addressRepository.save(address));
        subscribedPlan.setValidUntil(subscribedPlan.getSubscribedIn().plusYears(1).toLocalDate());
        subscribedPlan = subscribedPlanRepository.save(subscribedPlan);

        LOGGER.info("Creating payment type: {} on getnet for client with id: {}", subscribedPlan.getPaymentType(), currentClient.getId());

        switch (subscribedPlan.getPaymentType()) {
            case "CREDIT_CARD":
                paymentService.subscribeToPlanWithCreditCard(cardInput, currentClient, subscribedPlan);
                break;
            case "PAYMENT_SLIP":
                paymentService.subscribeToPlanWithPaymentSlip(currentClient, subscribedPlan);
                break;
            default:
                LOGGER.error("Invalid payment method: {}", subscribedPlan.getPaymentType());
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.INVALID_PAYMENT_METHOD);
        }

        LOGGER.info("Finished getnet payment for client with id: {}", currentClient.getId());

//        LOGGER.info("Saving client with id: {} on TEM", currentClient.getId());
//        if (currentClient.getTemCardNumber() == null) {
//            try {
//                currentClient = temService.subscribeToTem(currentClient, address);
//            } catch (HttpServerErrorException ex) {
//                LOGGER.warn("Error creating user with id: {} on TEM: {}", currentClient.getId(), ex.getStatusText());
//            } catch (Exception ex) {
//                LOGGER.warn("Fatal error creating user with id: {} on TEM: {}", currentClient.getId(), ex.getMessage());
//            }
//        } else {
//            try {
//                temService.changeTemStatus(currentClient.getTemUserToken(), TemStatusTypeDTO.REACTIVATE);
//            } catch (HttpServerErrorException ex) {
//                LOGGER.warn("Error changing user with id: {} status on TEM: {}", currentClient.getId(), ex.getStatusText());
//            } catch (Exception ex) {
//                LOGGER.warn("Fatal error changing user with id: {} on TEM: {}", currentClient.getId(), ex.getMessage());
//            }
//        }

        clientRepository.saveAll(dependents);
        clientRepository.save(currentClient);

        try {
            smsService.send(
                    DataUtils.onlyNumber(currentClient.getTelephone()),
                    smsTokenBody.replace("<authToken>", currentClient.getAuthCode())
            );
        } catch (HttpClientErrorException ex) {
            LOGGER.warn("Error sending sms for client with id: {} with error: {}", currentClient.getId(), ex.toString());
        } catch (Exception ex) {
            LOGGER.warn("Fatal error sending sms for client with id: {} with error: {}", currentClient.getId(), ex.getMessage());
        }

        String logMessage = LogMessageBuilder.buildSubscribePlanMessage(currentClient.getCPF(), currentClient.getName(), subscribedPlan.getPlan().getId());
        logService.logClientHistory(ClientActionTypeDTO.SUBSCRIBED_CONTRACT, logMessage, currentClient.getCPF(), currentClient.getName());

        for (Client c : dependents) {
            logMessage = LogMessageBuilder.buildIncludedDependentMessage(currentClient.getCPF(), currentClient.getName(), c.getName(), c.getClientType());
            logService.logClientHistory(ClientActionTypeDTO.ADDED_DEPENDENTS, logMessage, currentClient.getCPF(), currentClient.getName());
        }

        LOGGER.info("Finished plan subscription successfully for client with id: {}", currentClient.getId());
    }

    @Transactional
    public void updateHolderAndExtraDependentInformation(List<DocumentsInput> holderDocuments, List<ExtraDependentInput> dependentInputs, Client holder) {
        List<Client> updatedDependents = new ArrayList<>();

        // Updates dependent information
        for (ExtraDependentInput input : dependentInputs) {
            Optional<Client> optionalDependent = clientRepository.findById(input.getId());
            if (optionalDependent.isEmpty()) {
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.USER_NOT_FOUND);
            }
            Client dependent = optionalDependent.get();
            if (!dependent.getHolder().getId().equals(holder.getId())) {
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.DEPENDENT_DONT_HAVE_CORRECT_HOLDER);
            }
            if (!dependent.getClientType().equals(ClientTypeDTO.EXTRA_DEPENDENT.name())) {
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.DEPENDENT_SHOULD_BE_GUEST);
            }
            if (!CpfUtils.isValidCpf(CpfUtils.getOnlyNumbers(input.getCpf()))) {
                throw new ErrorOnProcessingRequestException(StatusCodes.Error.User.INVALID_CPF);
            }
            dependent.setCPF(CpfUtils.getOnlyNumbers(input.getCpf()));
            dependent.setRG(input.getRg());
            dependent.setTelephone(input.getTelephone());
            dependent.setGender(input.getGender().name());
            documentService.saveCurrentClientDocuments(input.getDocuments(), dependent);
            updatedDependents.add(dependent);
        }
        // Save holder documents
        documentService.saveCurrentClientDocuments(holderDocuments, holder);
        // Save all dependents information
        clientRepository.saveAll(updatedDependents);
    }


    @Transactional
    public void removeDependents(List<Client> toDeleteDependents, String holderName, String holderCpf, SubscribedPlan currentSubscribedPlan) {

        clientRepository.saveAll(toDeleteDependents);
        subscribedPlanRepository.save(currentSubscribedPlan);

        final List<String> clientEmails = new ArrayList<>(toDeleteDependents.size());
        final List<String> oneSignalPlayersIds = new ArrayList<>(toDeleteDependents.size());

        for (Client client : toDeleteDependents) {
            final String email = client.getEmail();
            if (email != null) {
                clientEmails.add(email);
            }

            final String playerId = client.getOneSignalPlayerId();
            if (playerId != null) {
                oneSignalPlayersIds.add(playerId);
            }

            if (client.getHolder() != null) { // Only log this for dependents
                final String logMessage = LogMessageBuilder.buildRemovedDependentMessage(holderCpf, holderName, client.getName(), client.getClientType());
                logService.logClientHistory(ClientActionTypeDTO.REMOVED_DEPENDENTS, logMessage, holderCpf, holderName);
            }
        }

        final String body = vidaPlanoBody.replace("<holder_name>", holderName);
        if (clientEmails.size() > 0) {
            mailService.send(vidaPlanoEmail, clientEmails, vidaPlanoEmailSubject, body);
        }
        if (!oneSignalPlayersIds.isEmpty()) {
            pushService.sendNotificationOneSignal(oneSignalPlayersIds, body);
        }
    }

    @Transactional
    public void unsubscribe(SubscribedPlan subscribedPlan, List<Client> dependents, Client holder, String reason) {
        removeDependents(dependents, holder.getName(), holder.getCPF(), subscribedPlan);

        if (subscribedPlan.getPaymentType().equals(PaymentTypeDTO.CREDIT_CARD.name())) {
            paymentService.cancelCreditCardSubscription(reason, holder);
        }

        subscribedPlan.setActive(false);
        subscribedPlanRepository.save(subscribedPlan);
        // temService.changeTemStatus(holder.getTemUserToken(), TemStatusTypeDTO.SUSPEND);
        final String logMessage = LogMessageBuilder.buildUnsubscribedFromPlanMessage(holder.getCPF(), holder.getName(), subscribedPlan.getPlan().getId());
        logService.logClientHistory(ClientActionTypeDTO.UNSUBSCRIBED_CONTRACT, logMessage, holder.getCPF(), holder.getName());
    }

    @Transactional
    public void cancelContract(SubscribedPlan subscribedPlan) {
        final String reason = "User is default";

        // retrieve full subscribed plan information.
        Optional<SubscribedPlan> completeSubscribedPlan = subscribedPlanRepository.findById(subscribedPlan.getId());

        if (completeSubscribedPlan.isEmpty()) {
            throw new ErrorOnProcessingRequestException(StatusCodes.Error.Plan.PLAN_NOT_FOUND);
        }

        Client holder = completeSubscribedPlan.get().getBeneficiary();

        subscribedPlan.setCancelledIn(LocalDateTime.now());
        subscribedPlan.setActive(false);
        subscribedPlan.setDefault(true);

        List<Client> dependents = clientRepository.findAllByHolder(holder);

        for (Client dependent : dependents) {
            if (!dependent.isActive()) {
                dependent.setDeleted(true);
            }
        }

        holder.setClientType(ClientTypeDTO.GUEST.name());

        clientRepository.save(holder);

        unsubscribe(subscribedPlan, dependents, holder, reason);
    }

    @Transactional
    public void cancelContractByDeath(SubscribedPlan subscribedPlan) {
        final String reason = "User is dead";

        // Updates subscribed plan
        subscribedPlan.setCancelledIn(LocalDateTime.now());
        subscribedPlan.setDefault(true);

        // Holder is dead, so updates it
        Client holder = subscribedPlan.getBeneficiary();
        holder.setAlive(false);
        holder.setDeleted(true);
        holder.setActive(false);
        holder.setClientType(ClientTypeDTO.GUEST.name());

        // Remove dependents
        List<Client> dependents = clientRepository.findAllByHolder(holder);
        for (Client dependent : dependents) {
            if (!dependent.isActive()) {
                dependent.setDeleted(true);
            }
        }

        dependents.add(holder);

        unsubscribe(subscribedPlan, dependents, holder, reason);
    }

    // Cron used to check every midnight the contracts that have more than 90 days without payment.
    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void checkContractsToBeUnsubscribed() {
        LOGGER.info("Started scheduled method checkContractsToBeUnsubscribed");

        GeneralParameterization x = generalParameterizationRepository.findAll().get(0);
        List<SubscribedPlan> plansToCancel = subscribedPlanRepository.findShouldCancelContracts(x.getTimeToBlockAccount());
        for (SubscribedPlan subscribedPlan : plansToCancel) {
            cancelContract(subscribedPlan);
            LOGGER.info("Cancelled subscribed plan with id {}", subscribedPlan.getId());
        }
        if (plansToCancel.size() > 0) {
            LOGGER.info("{} subscribed plans cancelled", plansToCancel.size());
        }
    }

    // Recalculates de subscribed plan price and returns it with updated value
    private SubscribedPlan recalculatePlanPrice(SubscribedPlan subscribedPlan) {
        List<Client> clients = clientRepository.findAllByHolder(subscribedPlan.getBeneficiary());
        clients.add(subscribedPlan.getBeneficiary());

        Plan plan = planService.buildPlanWithAgeRanges(subscribedPlan.getPlan());
        double value = planService.calculatePrice(clients, plan.getPlanPriceTable().getAgeRanges(), plan.getDependentPriceTable().getAgeRanges());

        subscribedPlan.setValue(value);
        return subscribedPlan;
    }

    // Removes the dependents added after anticipation,
    // recalculates de subscribed plan price
    // if credit card -> cancel subscription
    @Transactional
    public void removeDependentAndRecalculate(SubscribedPlan subscribedPlan) {
        List<Client> clients = clientRepository.findAllByHolderAndAddedAfterAnticipationIsTrue(subscribedPlan.getBeneficiary());
        for (Client c : clients) {
            c.setAddedAfterAnticipation(null);
            c.setHolder(null);
            c.setClientType(ClientTypeDTO.GUEST.name());
        }
        recalculatePlanPrice(subscribedPlan);
        subscribedPlan.setAnticipationHaveDependent(false);
        subscribedPlan.setAnticipationLastPayment(null);

        if (subscribedPlan.getPaymentType().equals(PaymentTypeDTO.CREDIT_CARD.name())) {
            paymentService.cancelCreditCardSubscription("Usuario inadimplente", subscribedPlan.getBeneficiary());
        }

        if (clients.size() == 0) {
            LOGGER.warn("Subscribed plan is candidate to be cancelled, but no extra-dependent was found");
        } else {
            removeDependents(clients, subscribedPlan.getBeneficiary().getName(), subscribedPlan.getBeneficiary().getCPF(), subscribedPlan);
            subscribedPlanRepository.save(subscribedPlan);
        }
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void checkContractsToRemoveDependents() {
        List<SubscribedPlan> plansToCancel = subscribedPlanRepository.findShouldRemoveDependentsContracts();
        for (SubscribedPlan subscribedPlan : plansToCancel) {
            removeDependentAndRecalculate(subscribedPlan);
            LOGGER.info("Removed dependent while plan is anticipated in plan with id: {}", subscribedPlan.getId());
        }
        if (plansToCancel.size() > 0) {
            LOGGER.info("{} removed dependents while plan is anticipated", plansToCancel.size());
        }
    }

    @Transactional
    public void reactivateContract(SubscribedPlan subscribedPlan, List<Client> dependents, Client holder) {
        subscribedPlanRepository.save(subscribedPlan);
        clientRepository.saveAll(dependents);

        // Reactivate tem status
        // temService.changeTemStatus(holder.getTemUserToken(), TemStatusTypeDTO.REACTIVATE);

        LOGGER.info("Contract with id: {} was reactivated", subscribedPlan.getId());
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void checkChildBirthday() {
        LOGGER.info("Started scheduled method checkChildBirthday");
        LocalDate date = LocalDate.now().minusYears(24); // 24 years ago
        List<Client> clients = clientRepository.findBirthDayChildren(date);
        for (Client c : clients) {
            disableChildAndNotify(c);
        }
    }

    // Disable child that is doing 24 years.
    @Transactional
    public void disableChildAndNotify(Client dependent) {
        final String holderEmail = dependent.getHolder().getEmail();
        final String holderOneSignal = dependent.getHolder().getOneSignalPlayerId();
        dependent.setHolder(null);
        clientRepository.save(dependent);

        // Email
        final String body = removedChildBody.replace("<child_name>", dependent.getName());
        mailService.send(vidaPlanoEmail, holderEmail, removedChildSubject, body);

        // Push notification - must check if holder has onesignal id to not send notification to everyone
        if (holderOneSignal != null && !holderOneSignal.isBlank()) {
            pushService.sendNotificationOneSignal(Collections.singletonList(holderOneSignal), body);
        }

        LOGGER.info("Disabled dependent with cpf: {}", dependent.getCPF());
    }

    @Scheduled(cron = "${cron.cron-every-midnight}")
    public void startNewSubscribedPlanYear() {
        List<SubscribedPlan> subscribedPlanList = subscribedPlanRepository.findAllExpiredContracts();
        subscribedPlanList =
                subscribedPlanList
                        .stream()
                        .peek(subscribedPlan -> subscribedPlan.setValidUntil(subscribedPlan.getValidUntil().plusYears(1)))
                        .collect(Collectors.toList());
        subscribedPlanRepository.saveAll(subscribedPlanList);
    }

    // Recalculates the subscribed plan price and saves it.
    public void calculateSubscribedPlanPriceAndUpdate(Client holder, List<PriceTableAgeRange> ageRanges, List<PriceTableAgeRange> dependentAgeRange, SubscribedPlan subscribedPlan) {
        List<Client> clients = clientRepository.findAllByHolder(holder);
        clients.add(holder);

        subscribedPlan.setValue(planService.calculatePrice(clients, ageRanges, dependentAgeRange));
        if (subscribedPlan.getPaymentType().equals(PaymentTypeDTO.CREDIT_CARD.name())) {
            paymentService.cancelCreditCardSubscription("Alterado valor do plano", holder);
            subscribedPlan.setWaitingForLastPaymentDate(false);
        }
        subscribedPlanRepository.save(subscribedPlan);
    }

    public boolean checkSubscriptionValidUntil(SubscribedPlan subscribedPlan, int parcels) {
        LocalDate validUntil = subscribedPlan.getValidUntil();
        LocalDate lastPayment = subscribedPlan.getLastPayment().toLocalDate();

        return lastPayment.until(validUntil, ChronoUnit.MONTHS) - parcels < 0;
    }
}
