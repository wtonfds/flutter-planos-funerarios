package br.com.monitoratec.farol.rest.tem.dto;

public class TemLoginParameters {
    private String companyId;
    private String apiKey;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
