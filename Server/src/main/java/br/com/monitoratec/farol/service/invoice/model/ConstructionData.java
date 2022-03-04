package br.com.monitoratec.farol.service.invoice.model;

import org.springframework.util.Assert;

public class ConstructionData {
    private final String constructionCode;
    private final String artCode;

    public ConstructionData(String constructionCode, String artCode) {
        Assert.notNull(constructionCode, "Construction code must not be null");
        Assert.notNull(artCode, "ART code must not be null");

        this.constructionCode = constructionCode;
        this.artCode = artCode;
    }

    public String getConstructionCode() {
        return constructionCode;
    }

    public String getArtCode() {
        return artCode;
    }
}
