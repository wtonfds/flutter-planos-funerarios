package br.com.monitoratec.farol.graphql.model.dtos.accredited;

import br.com.monitoratec.farol.graphql.model.dtos.location.AddressDTO;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;

public class AccreditedDTO {
    private Long id;
    private String name;
    private String CNPJ;
    private AddressDTO address;
    private String logo;
    private String couponOrigin;
    private String code;
    private Boolean active;
    private String category;
    private String subcategory;
    private String telephone;
    private String site;

    public AccreditedDTO(Accredited accredited) {
        this.id = accredited.getId();
        this.name = accredited.getName();
        this.CNPJ = accredited.getCNPJ();
        if (accredited.getAddress() != null) {
            this.address = new AddressDTO(accredited.getAddress());
        }
        this.logo = accredited.getLogo();
        this.couponOrigin = accredited.getCouponOrigin();
        this.code = accredited.getCode();
        this.active = accredited.isActive();
        this.category = accredited.getCategory();
        this.subcategory = accredited.getSubcategory();
        this.telephone = accredited.getTelephone();
        this.site = accredited.getSite();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return CNPJ;
    }

    public void setCnpj(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
