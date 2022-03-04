package br.com.monitoratec.farol.sql.model.generalParameterization;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "frl_general")
public class GeneralParameterization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_general_sequence")
    @SequenceGenerator(name = "frl_general_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Boolean loyaltyCard;

    @Column(nullable = false)
    private String loyaltyCardNumberRule;

    @Column(nullable = false)
    private Boolean tem;

    @Column(nullable = false, name = "accredited_login_with_cnpj")
    private Boolean accreditedLoginWithCNPJ;

    @Column(nullable = false)
    private LocalDate accreditedCouponDueDate;

    @Column(nullable = false)
    private Boolean lotteryAutoDisclosure;

    @Column(nullable = false, name = "lottery_url")
    private String lotteryURL;

    @Column(nullable = false)
    private String SLA;

    @Column(nullable = false)
    private String nfIss;

    @Column(nullable = false, name = "nf_giss_url")
    private String nfGissURL;

    @Column(nullable = false)
    private String nfGissUser;

    @Column(nullable = false)
    private String nfGissPassword;

    @Column(nullable = false)
    private String nfGissDueDate;

    @Column(nullable = false)
    private String farolTelephone;

    @Column(nullable = false)
    private Integer timeToBlockAccount;

    @Column(nullable = false)
    private Integer timeToUpdateFinancialData;

    @Column(nullable = false)
    private String farolEmail;

    @Column(nullable = false)
    private String funeralAssistancePhone;

    @Column(nullable = false)
    private String aliquot;

    @Column
    private String planBenefits;

    @Column
    private String planBenefitsDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(Boolean loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public String getLoyaltyCardNumberRule() {
        return loyaltyCardNumberRule;
    }

    public void setLoyaltyCardNumberRule(String loyaltyCardNumberRule) {
        this.loyaltyCardNumberRule = loyaltyCardNumberRule;
    }

    public Boolean getTem() {
        return tem;
    }

    public void setTem(Boolean tem) {
        this.tem = tem;
    }

    public Boolean getAccreditedLoginWithCNPJ() {
        return accreditedLoginWithCNPJ;
    }

    public void setAccreditedLoginWithCNPJ(Boolean accreditedLoginWithCNPJ) {
        this.accreditedLoginWithCNPJ = accreditedLoginWithCNPJ;
    }

    public LocalDate getAccreditedCouponDueDate() {
        return accreditedCouponDueDate;
    }

    public void setAccreditedCouponDueDate(LocalDate accreditedCouponDueDate) {
        this.accreditedCouponDueDate = accreditedCouponDueDate;
    }

    public Boolean getLotteryAutoDisclosure() {
        return lotteryAutoDisclosure;
    }

    public void setLotteryAutoDisclosure(Boolean loteryAutoDisclosure) {
        this.lotteryAutoDisclosure = loteryAutoDisclosure;
    }

    public String getLotteryURL() {
        return lotteryURL;
    }

    public void setLotteryURL(String loteryURL) {
        this.lotteryURL = loteryURL;
    }

    public String getSLA() {
        return SLA;
    }

    public void setSLA(String SLA) {
        this.SLA = SLA;
    }

    public String getNfIss() {
        return nfIss;
    }

    public void setNfIss(String nfIss) {
        this.nfIss = nfIss;
    }

    public String getNfGissURL() {
        return nfGissURL;
    }

    public void setNfGissURL(String nfGissURL) {
        this.nfGissURL = nfGissURL;
    }

    public String getNfGissUser() {
        return nfGissUser;
    }

    public void setNfGissUser(String nfGissUser) {
        this.nfGissUser = nfGissUser;
    }

    public String getNfGissPassword() {
        return nfGissPassword;
    }

    public void setNfGissPassword(String nfGissPassword) {
        this.nfGissPassword = nfGissPassword;
    }

    public String getNfGissDueDate() {
        return nfGissDueDate;
    }

    public void setNfGissDueDate(String nfGissDueDate) {
        this.nfGissDueDate = nfGissDueDate;
    }

    public String getFarolTelephone() {
        return farolTelephone;
    }

    public void setFarolTelephone(String farolTelephone) {
        this.farolTelephone = farolTelephone;
    }

    public Integer getTimeToBlockAccount() {
        return timeToBlockAccount;
    }

    public void setTimeToBlockAccount(Integer timeToBlockAccount) {
        this.timeToBlockAccount = timeToBlockAccount;
    }

    public Integer getTimeToUpdateFinancialData() {
        return timeToUpdateFinancialData;
    }

    public void setTimeToUpdateFinancialData(Integer timeToUpdateFinancialData) {
        this.timeToUpdateFinancialData = timeToUpdateFinancialData;
    }

    public String getFarolEmail() {
        return farolEmail;
    }

    public void setFarolEmail(String farolEmail) {
        this.farolEmail = farolEmail;
    }

    public String getFuneralAssistancePhone() {
        return funeralAssistancePhone;
    }

    public void setFuneralAssistancePhone(String funeralAssistancePhone) {
        this.funeralAssistancePhone = funeralAssistancePhone;
    }

    public String getPlanBenefits() {
        return planBenefits;
    }

    public void setPlanBenefits(String planBenefits) {
        this.planBenefits = planBenefits;
    }

    public String getAliquot() {
        return aliquot;
    }

    public void setAliquot(String aliquot) {
        this.aliquot = aliquot;
    }

    public String getPlanBenefitsDetails() {
        return planBenefitsDetails;
    }

    public void setPlanBenefitsDetails(String planBenefitsDetails) {
        this.planBenefitsDetails = planBenefitsDetails;
    }
}
