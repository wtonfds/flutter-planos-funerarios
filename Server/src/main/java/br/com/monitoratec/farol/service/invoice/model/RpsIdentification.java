package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

import java.math.BigInteger;

public class RpsIdentification {
    private final BigInteger number;
    private final String series;
    private final RpsType type;

    public RpsIdentification(BigInteger number, String series, RpsType type) {
        Assert.notNull(number, "RPS number must not be null");
        Assert.notNull(series, "RPS series must not be null");
        Assert.notNull(type, "RPS type must not be null");

        this.number = number;
        this.series = series;
        this.type = type;
    }

    public BigInteger getNumber() {
        return number;
    }

    public String getSeries() {
        return series;
    }

    public RpsType getType() {
        return type;
    }
}
