package br.com.monitoratec.farol.rest.payment;

import br.com.monitoratec.farol.rest.payment.dto.*;
import br.com.monitoratec.farol.service.payment.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/payment-notifications")
public class PaymentNotificationsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentNotificationsRestController.class);

    private final PaymentService service;

    public PaymentNotificationsRestController(PaymentService service) {
        this.service = service;
    }

    @GetMapping("/slip")
    public void paymentSlipGenerated(PaymentSlipGenerationParameters parameters) {
        LOGGER.info("Generated payment slip: {}", parameters);
        service.paymentSlipGenerated(parameters);
    }

    //The URL is the same as the previous one, but this method will only be called if parameter "payment_date" is present
    @GetMapping(value = "/slip", params = "payment_date")
    public void paymentSlipFinished(PaymentSlipFinishedParameters parameters) {
        LOGGER.info("Finished payment slip: {}", parameters);
        service.paymentSlipFinished(parameters);
    }

    @GetMapping("/recurrence")
    public void recurrenceUpdated(RecurrenceUpdatedParameters parameters) {
        LOGGER.info("Received update for recurrence: {}", parameters);
        service.recurrenceUpdated(parameters);
    }

    @GetMapping("/credit")
    public void creditCardUpdated(CreditCardUpdatedParameters parameters){
        LOGGER.info("Received update for credit card: {}", parameters);
        service.creditCardUpdated(parameters);
    }

    @GetMapping("/debit")
    public void debitCardUpdated(DebitCardUpdatedParameters parameters){
        LOGGER.info("Received update for debit card: {}", parameters);
        service.debitCardUpdated(parameters);
    }

    @PostMapping("/debit")
    public void debitCardBankReturn(Object parameters) {
        LOGGER.info("Received bank update for debit card: {}", parameters);
    }
}
