package br.com.monitoratec.farol.sql.repository.payment;

import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMonthRepository extends JpaRepository<PaymentMonth, Long> {
    Optional<PaymentMonth> findBySubscribedPlanAndMonthAndYear(SubscribedPlan subscribedPlan, int month, int year);

    List<PaymentMonth> findAllBySubscribedPlan(SubscribedPlan subscribedPlan);
}
