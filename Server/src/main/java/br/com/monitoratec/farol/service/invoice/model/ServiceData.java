package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.util.Optional;

public class ServiceData {
    private final Values values;
    private final String serviceListItemCode;
    private final Integer cnaeCode;
    private final String municipalTaxCode;
    private final String discrimination;
    private final int cityCode;

    private ServiceData(Builder builder) {
        Assert.notNull(builder.values, "Values must not be null");
        Assert.notNull(builder.serviceListItemCode, "Service list item code must not be null");
        Assert.notNull(builder.discrimination, "Discrimination must not be null");

        this.values = builder.values;
        this.serviceListItemCode = builder.serviceListItemCode;
        this.cnaeCode = builder.cnaeCode;
        this.municipalTaxCode = builder.municipalTaxCode;
        this.discrimination = builder.discrimination;
        this.cityCode = builder.cityCode;
    }

    public Values getValues() {
        return values;
    }

    public String getServiceListItemCode() {
        return serviceListItemCode;
    }

    public Optional<Integer> getCnaeCode() {
        return Optional.ofNullable(cnaeCode);
    }

    public Optional<String> getMunicipalTaxCode() {
        return Optional.ofNullable(municipalTaxCode);
    }

    public String getDiscrimination() {
        return discrimination;
    }

    public int getCityCode() {
        return cityCode;
    }

    public static final class Builder {
        private Values values;
        private String serviceListItemCode;
        private Integer cnaeCode;
        private String municipalTaxCode;
        private String discrimination;
        private int cityCode;

        public Builder setValues(Values values) {
            this.values = values;
            return this;
        }

        public Builder setServiceListItemCode(String serviceListItemCode) {
            this.serviceListItemCode = serviceListItemCode;
            return this;
        }

        public Builder setCnaeCode(Integer cnaeCode) {
            this.cnaeCode = cnaeCode;
            return this;
        }

        public Builder setMunicipalTaxCode(String municipalTaxCode) {
            this.municipalTaxCode = municipalTaxCode;
            return this;
        }

        public Builder setDiscrimination(String discrimination) {
            this.discrimination = discrimination;
            return this;
        }

        public Builder setCityCode(int cityCode) {
            this.cityCode = cityCode;
            return this;
        }

        public ServiceData build() {
            return new ServiceData(this);
        }
    }
}
