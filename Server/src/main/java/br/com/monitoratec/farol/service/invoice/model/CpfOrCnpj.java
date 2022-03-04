package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

public class CpfOrCnpj {
    private final String value;
    private final boolean cpf;

    public CpfOrCnpj(String value, boolean cpf) {
        Assert.notNull(value, "CPF or CNPJ value must not be null");

        this.value = value;
        this.cpf = cpf;
    }

    public String getValue() {
        return value;
    }

    public boolean isCpf() {
        return cpf;
    }
}
