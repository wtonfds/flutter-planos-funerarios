package br.com.monitoratec.farol.sql.repository.subscribedPlan;

import br.com.monitoratec.farol.sql.model.plan.SubscribedPlan;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscribedPlanRepository extends JpaRepository<SubscribedPlan, Long> {
    @Query("select u from SubscribedPlan u where u.beneficiary.id = :id")
    Optional<SubscribedPlan> findByBeneficiaryId(Long id);

    @Query("select u from SubscribedPlan  u where u.beneficiary.id = :id and u.isDefault = true")
    List<SubscribedPlan> findAllByBeneficiaryIdAndIsDefaultTrue(Long id);

    @Query("select u from SubscribedPlan u join fetch u.beneficiary join fetch u.plan where u.beneficiary.id = :beneficiaryId and u.active = true")
    Optional<SubscribedPlan> findByBeneficiaryIdAndActiveIsTrue(Long beneficiaryId);

    @NotNull
    @Query("select a from SubscribedPlan a join fetch a.beneficiary join fetch a.plan where a.id = :id")
    Optional<SubscribedPlan> findById(@NotNull Long id);

    @Query("select a from SubscribedPlan a where a.lastPayment <= current_date - :days and a.active = true")
    List<SubscribedPlan> findShouldCancelContracts(@NotNull Integer days);

    @Query("select s from SubscribedPlan s join fetch s.beneficiary join fetch s.plan where s.anticipationHaveDependent = true and s.anticipationLastPayment <= current_date - 90 and s.active = true")
    List<SubscribedPlan> findShouldRemoveDependentsContracts();

    @Query("select a from SubscribedPlan a join fetch a.beneficiary where a.active = true and a.paymentType = :paymentType")
    List<SubscribedPlan> findAllByPaymentTypeAndActiveIsTrue(String paymentType);

    @Query("select a from SubscribedPlan a join fetch a.beneficiary where a.active = true and a.waitingForLastPaymentDate = true and a.anticipationHaveDependent = false and a.lastPayment <= current_date")
    List<SubscribedPlan> findByWaitingForLastPaymentDateIsTrueAndActiveIsTrueAndAnticipationHaveDependentIsFalseAndPastLastPayment();

    @Query("select a from SubscribedPlan a join fetch a.beneficiary join fetch a.plan join fetch a.address where a.active = true and a.anticipationHaveDependent = true and a.lastPayment <= current_date")
    List<SubscribedPlan> findShouldUpdateContracts();

    @Query("select a from SubscribedPlan a join fetch a.beneficiary where a.anticipationHaveDependent = true and a.active = true and a.anticipationHaveDependent = true")
    List<SubscribedPlan> findByAnticipationHaveDependentIsTrueAndActiveIsTrueAndWaitingForLastPaymentDateIsTrue();

    List<SubscribedPlan> findAllByActiveIsTrue();

    @Query("select a from SubscribedPlan a where a.validUntil <= current_date and a.active = true")
    List<SubscribedPlan> findAllExpiredContracts();
}
