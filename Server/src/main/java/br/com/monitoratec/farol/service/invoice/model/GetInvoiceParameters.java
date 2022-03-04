package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

public class GetInvoiceParameters {
    private final String providerCnpj;
    private final String providerMunicipalRegistry;
    private final String protocol;

    private GetInvoiceParameters(Builder builder) {
        Assert.notNull(builder.providerCnpj, "Provider CNPJ must not be null");

        providerCnpj = builder.providerCnpj;
        providerMunicipalRegistry = builder.providerMunicipalRegistry;
        protocol = builder.protocol;
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

        public GetInvoiceParameters build() {
            return new GetInvoiceParameters(this);
        }
    }

    public String getProviderCnpj() {
        return providerCnpj;
    }

    public String getProviderMunicipalRegistry() {
        return providerMunicipalRegistry;
    }

    public String getProtocol() {
        return protocol;
    }
}
