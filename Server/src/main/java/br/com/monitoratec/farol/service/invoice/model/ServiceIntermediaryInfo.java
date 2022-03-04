package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.util.Optional;

public class ServiceIntermediaryInfo {
    private final String companyName;
    private final CpfOrCnpj cpfOrCnpj;
    private final String municipalRegistry;

    public ServiceIntermediaryInfo(String companyName, CpfOrCnpj cpfOrCnpj, String municipalRegistry) {
        Assert.notNull(companyName, "Company name must not be null");
        Assert.notNull(companyName, "CPF or CNPJ must not be null");

        this.companyName = companyName;
        this.cpfOrCnpj = cpfOrCnpj;
        this.municipalRegistry = municipalRegistry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CpfOrCnpj getCpfOrCnpj() {
        return cpfOrCnpj;
    }

    public Optional<String> getMunicipalRegistry() {
        return Optional.ofNullable(municipalRegistry);
    }
}
