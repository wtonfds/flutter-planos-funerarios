package br.com.monitoratec.farol.sql.repository.payment;

import br.com.monitoratec.farol.sql.model.payment.UncheckedInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UncheckedInvoiceRepository extends JpaRepository<UncheckedInvoice, Long> {

    @Query("select a from UncheckedInvoice a " +
            "join fetch a.paymentHistory b join fetch b.paymentMonth c " +
            "join fetch c.subscribedPlan d join fetch d.beneficiary " +
            "where a.error is null")
    List<UncheckedInvoice> getAllWithClientInformation();
}
