package br.com.monitoratec.farol.graphql.model.input.accredited;

import java.util.List;

public class AccreditedInfo {
    private String name;
    private String CNPJ;
    private List<Byte> logo;
    private String couponOrigin;
    private String code;
    private String category;
    private String subcategory;
    private String telephone;
    private Boolean active;
    private String site;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public List<Byte> getLogo() {
        return logo;
    }

    public void setLogo(List<Byte> logo) {
        this.logo = logo;
    }

    public String getCouponOrigin() {
        return couponOrigin;
    }

    public void setCouponOrigin(String couponOrigin) {
        this.couponOrigin = couponOrigin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
