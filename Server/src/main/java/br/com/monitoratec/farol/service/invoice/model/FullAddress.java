package br.com.monitoratec.farol.service.invoice.model;

import java.util.Optional;

public class FullAddress {
    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final Integer cityCode;
    private final String province;
    private final Integer zipCode;

    private FullAddress(Builder builder) {
        this.street = builder.street;
        this.number = builder.number;
        this.complement = builder.complement;
        this.neighborhood = builder.neighborhood;
        this.cityCode = builder.cityCode;
        this.province = builder.province;
        this.zipCode = builder.zipCode;
    }

    public Optional<String> getStreet() {
        return Optional.ofNullable(street);
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }

    public Optional<String> getComplement() {
        return Optional.ofNullable(complement);
    }

    public Optional<String> getNeighborhood() {
        return Optional.ofNullable(neighborhood);
    }

    public Optional<Integer> getCityCode() {
        return Optional.ofNullable(cityCode);
    }

    public Optional<String> getProvince() {
        return Optional.ofNullable(province);
    }

    public Optional<Integer> getZipCode() {
        return Optional.ofNullable(zipCode);
    }

    public static final class Builder {
        private String street;
        private String number;
        private String complement;
        private String neighborhood;
        private Integer cityCode;
        private String province;
        private Integer zipCode;

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setComplement(String complement) {
            this.complement = complement;
            return this;
        }

        public Builder setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder setCityCode(Integer cityCode) {
            this.cityCode = cityCode;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setZipCode(Integer zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public FullAddress build() {
            return new FullAddress(this);
        }
    }
}
