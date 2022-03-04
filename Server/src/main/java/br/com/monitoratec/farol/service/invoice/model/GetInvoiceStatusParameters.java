package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.util.Optional;

public class GetInvoiceStatusParameters {
    private final String providerCnpj;
    private final String providerMunicipalRegistry;
    private final String protocol;

    private GetInvoiceStatusParameters(Builder builder) {
        Assert.notNull(builder.providerCnpj, "Provider CNPJ must not be null");

        providerCnpj = builder.providerCnpj;
        providerMunicipalRegistry = builder.providerMunicipalRegistry;
        protocol = builder.protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getProviderCnpj() {
        return providerCnpj;
    }

    public Optional<String> getProviderMunicipalRegistry() {
        return Optional.ofNullable(providerMunicipalRegistry);
    }


    public static final class Builder {
        private String providerCnpj;
        private String providerMunicipalRegistry;
        private String protocol;

        public Builder setProtocol(String protocol) {
            this.protocol = protocol;
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

        public GetInvoiceStatusParameters build() {
            return new GetInvoiceStatusParameters(this);
        }
    }
}
