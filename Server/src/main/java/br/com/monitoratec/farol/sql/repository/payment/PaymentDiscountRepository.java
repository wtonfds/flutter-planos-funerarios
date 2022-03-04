package br.com.monitoratec.farol.sql.repository.payment;

import br.com.monitoratec.farol.sql.model.payment.PaymentDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentDiscountRepository extends JpaRepository<PaymentDiscount, Long> {
    Optional<PaymentDiscount> findFirstByMonth(int month);
}
