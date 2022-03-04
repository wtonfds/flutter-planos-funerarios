package br.com.monitoratec.farol.service.invoice.model;

import java.util.Optional;

public class TakerInfo {
    private final CpfOrCnpj cpfOrCnpj;
    private final String municipalRegistry;

    public TakerInfo(CpfOrCnpj cpfOrCnpj, String municipalRegistry) {
        this.cpfOrCnpj = cpfOrCnpj;
        this.municipalRegistry = municipalRegistry;
    }

    public Optional<CpfOrCnpj> getCpfOrCnpj() {
        return Optional.ofNullable(cpfOrCnpj);
    }

    public Optional<String> getMunicipalRegistry() {
        return Optional.ofNullable(municipalRegistry);
    }
}
