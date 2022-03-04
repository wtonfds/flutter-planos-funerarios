package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

public class RpsInfo {
    private final RpsIdentification rpsIdentification;
    private final LocalDateTime emissionDate;
    private final RpsOperationNature operationNature;
    private final RpsSpecialTaxRegime specialTaxRegime;
    private final boolean nationalSimple;
    private final boolean culturalPromoter;
    private final RpsStatus status;
    private final RpsIdentification replacedRps;
    private final ServiceData serviceData;
    private final String providerCnpj;
    private final String providerMunicipalRegistry;
    private final TakerData takerData;
    private final ServiceIntermediaryInfo serviceIntermediaryInfo;
    private final ConstructionData constructionData;

    private RpsInfo(Builder builder) {
        Assert.notNull(builder.rpsIdentification, "RPS identification must not be null");
        Assert.notNull(builder.emissionDate, "Emission date must not be null");
        Assert.notNull(builder.operationNature, "Operation nature must not be null");
        Assert.notNull(builder.status, "Status must not be null");
        Assert.notNull(builder.serviceData, "Service data must not be null");
        Assert.notNull(builder.providerCnpj, "Provider CNPJ must not be null");
        Assert.notNull(builder.takerData, "Taker data must not be null");

        this.rpsIdentification = builder.rpsIdentification;
        this.emissionDate = builder.emissionDate;
        this.operationNature = builder.operationNature;
        this.specialTaxRegime = builder.specialTaxRegime;
        this.nationalSimple = builder.nationalSimple;
        this.culturalPromoter = builder.culturalPromoter;
        this.status = builder.status;
        this.replacedRps = builder.replacedRps;
        this.serviceData = builder.serviceData;
        this.providerCnpj = builder.providerCnpj;
        this.providerMunicipalRegistry = builder.providerMunicipalRegistry;
        this.takerData = builder.takerData;
        this.serviceIntermediaryInfo = builder.serviceIntermediaryInfo;
        this.constructionData = builder.constructionData;
    }

    public RpsIdentification getRpsIdentification() {
        return rpsIdentification;
    }

    public LocalDateTime getEmissionDate() {
        return emissionDate;
    }

    public RpsOperationNature getOperationNature() {
        return operationNature;
    }

    public Optional<RpsSpecialTaxRegime> getSpecialTaxRegime() {
        return Optional.ofNullable(specialTaxRegime);
    }

    public boolean isNationalSimple() {
        return nationalSimple;
    }

    public boolean isCulturalPromoter() {
        return culturalPromoter;
    }

    public RpsStatus getStatus() {
        return status;
    }

    public Optional<RpsIdentification> getReplacedRps() {
        return Optional.ofNullable(replacedRps);
    }

    public ServiceData getServiceData() {
        return serviceData;
    }

    public String getProviderCnpj() {
        return providerCnpj;
    }

    public Optional<String> getProviderMunicipalRegistry() {
        return Optional.ofNullable(providerMunicipalRegistry);
    }

    public TakerData getTakerData() {
        return takerData;
    }

    public Optional<ServiceIntermediaryInfo> getServiceIntermediaryInfo() {
        return Optional.ofNullable(serviceIntermediaryInfo);
    }

    public Optional<ConstructionData> getConstructionData() {
        return Optional.ofNullable(constructionData);
    }

    public static final class Builder {
        private RpsIdentification rpsIdentification;
        private LocalDateTime emissionDate;
        private RpsOperationNature operationNature;
        private RpsSpecialTaxRegime specialTaxRegime;
        private boolean nationalSimple;
        private boolean culturalPromoter;
        private RpsStatus status;
        private RpsIdentification replacedRps;
        private ServiceData serviceData;
        private String providerCnpj;
        private String providerMunicipalRegistry;
        private TakerData takerData;
        private ServiceIntermediaryInfo serviceIntermediaryInfo;
        private ConstructionData constructionData;

        public Builder setRpsIdentification(RpsIdentification rpsIdentification) {
            this.rpsIdentification = rpsIdentification;
            return this;
        }

        public Builder setEmissionDate(LocalDateTime emissionDate) {
            this.emissionDate = emissionDate;
            return this;
        }

        public Builder setOperationNature(RpsOperationNature operationNature) {
            this.operationNature = operationNature;
            return this;
        }

        public Builder setSpecialTaxRegime(RpsSpecialTaxRegime specialTaxRegime) {
            this.specialTaxRegime = specialTaxRegime;
            return this;
        }

        public Builder setNationalSimple(boolean nationalSimple) {
            this.nationalSimple = nationalSimple;
            return this;
        }

        public Builder setCulturalPromoter(boolean culturalPromoter) {
            this.culturalPromoter = culturalPromoter;
            return this;
        }

        public Builder setStatus(RpsStatus status) {
            this.status = status;
            return this;
        }

        public Builder setReplacedRps(RpsIdentification replacedRps) {
            this.replacedRps = replacedRps;
            return this;
        }

        public Builder setServiceData(ServiceData serviceData) {
            this.serviceData = serviceData;
            return this;
        }

        public Builder setProviderCnpj(String providerCnpj) {
            this.providerCnpj = providerCnpj;
            return this;
        }

        public Builder setProviderMunicipalRegistry(String providerMunicipalRegistry) {
            this.providerMunicipalRegistry = providerMunicipalRegistry;
            return this;
        }

        public Builder setTakerData(TakerData takerData) {
            this.takerData = takerData;
            return this;
        }

        public Builder setServiceIntermediaryInfo(ServiceIntermediaryInfo serviceIntermediaryInfo) {
            this.serviceIntermediaryInfo = serviceIntermediaryInfo;
            return this;
        }

        public Builder setConstructionData(ConstructionData constructionData) {
            this.constructionData = constructionData;
            return this;
        }

        public RpsInfo build() {
            return new RpsInfo(this);
        }
    }
}
