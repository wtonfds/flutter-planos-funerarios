package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.List;

public class PostInvoiceParameters {
    private final BigInteger lotNumber;
    private final String cnpj;
    private final String municipalRegistry;
    private final List<RpsInfo> rpsInfoList;

    public PostInvoiceParameters(BigInteger lotNumber, String cnpj, String municipalRegistry, List<RpsInfo> rpsInfoList) {
        Assert.notNull(lotNumber, "Lot number must not be null");
        Assert.notNull(cnpj, "CNPJ must not be null");
        Assert.notNull(municipalRegistry, "Municipal registry must not be null");
        Assert.notNull(rpsInfoList, "RPS Info list must not be null");

        this.lotNumber = lotNumber;
        this.cnpj = cnpj;
        this.municipalRegistry = municipalRegistry;
        this.rpsInfoList = rpsInfoList;
    }

    public BigInteger getLotNumber() {
        return lotNumber;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getMunicipalRegistry() {
        return municipalRegistry;
    }

    public List<RpsInfo> getRpsInfoList() {
        return rpsInfoList;
    }
}
