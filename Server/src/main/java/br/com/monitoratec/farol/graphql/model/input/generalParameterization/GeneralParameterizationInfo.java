package br.com.monitoratec.farol.graphql.model.input.generalParameterization;


import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Email;

public class GeneralParameterizationInfo {
    private Boolean loyaltyCard;
    private String loyaltyCardNumberRule;
    private Boolean tem;
    private Boolean accreditedLoginWithCNPJ;
    private Date accreditedCouponDueDate;
    private Boolean lotteryAutoDisclosure;
    private String lotteryURL;
    private String SLA;
    private String nfIss;
    private String nfGissURL;
    private String nfGissUser;
    private String nfGissPassword;
    private String nfGissDueDate;
    private String farolTelephone;
    private Integer timeToBlockAccount;
    private Integer timeToUpdateFinancialData;
    private Email farolEmail;
    private String funeralAssistancePhone;
    private String aliquot;
    private String planBenefits;
    private String planBenefitsDetails;

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

    public Date getAccreditedCouponDueDate() {
        return accreditedCouponDueDate;
    }

    public void setAccreditedCouponDueDate(Date accreditedCouponDueDate) {
        this.accreditedCouponDueDate = accreditedCouponDueDate;
    }

    public Boolean getLotteryAutoDisclosure() {
        return lotteryAutoDisclosure;
    }

    public void setLotteryAutoDisclosure(Boolean lotteryAutoDisclosure) {
        this.lotteryAutoDisclosure = lotteryAutoDisclosure;
    }

    public String getLotteryURL() {
        return lotteryURL;
    }

    public void setLotteryURL(String lotteryURL) {
        this.lotteryURL = lotteryURL;
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

    public Email getFarolEmail() {
        return farolEmail;
    }

    public void setFarolEmail(Email farolEmail) {
        this.farolEmail = farolEmail;
    }

    public String getFuneralAssistancePhone() {
        return funeralAssistancePhone;
    }

    public void setFuneralAssistancePhone(String funeralAssistancePhone) {
        this.funeralAssistancePhone = funeralAssistancePhone;
    }

    public String getAliquot() {
        return aliquot;
    }

    public void setAliquot(String aliquot) {
        this.aliquot = aliquot;
    }

    public String getPlanBenefits() {
        return planBenefits;
    }

    public void setPlanBenefits(String planBenefits) {
        this.planBenefits = planBenefits;
    }

    public String getPlanBenefitsDetails() {
        return planBenefitsDetails;
    }

    public void setPlanBenefitsDetails(String planBenefitsDetails) {
        this.planBenefitsDetails = planBenefitsDetails;
    }
}
