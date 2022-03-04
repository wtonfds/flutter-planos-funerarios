package br.com.monitoratec.farol.sql.repository.payment;

import br.com.monitoratec.farol.sql.model.payment.PaymentHistory;
import br.com.monitoratec.farol.sql.model.payment.PaymentMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    @Query("select h from PaymentHistory h join fetch h.paymentMonth where h.paymentId = :paymentId")
    Optional<PaymentHistory> findByPaymentId(String paymentId);

    @Query("select h from PaymentHistory h join fetch h.paymentMonth where h.paymentSlipId = :paymentSlipId")
    Optional<PaymentHistory> findByPaymentSlipId(String paymentSlipId);

    Optional<PaymentHistory> findTopByPaymentMonthAndStatusOrderByCreatedAtDesc(PaymentMonth paymentMonth, String status);

    Optional<PaymentHistory> findTopByPaymentMonthOrderByCreatedAtDesc(PaymentMonth paymentMonth);
}
