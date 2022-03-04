package br.com.monitoratec.farol.sql.model.accredited;

import br.com.monitoratec.farol.sql.model.product.Product;
import br.com.monitoratec.farol.sql.model.user.JuridicUser;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "frl_accredited")
public class Accredited extends JuridicUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frl_accredited_sequence")
    @SequenceGenerator(name = "frl_accredited_sequence", allocationSize = 1)
    private Long id;

    @Column(length = 255)
    private String logo;

    @Column(length = 255, nullable = false)
    private String couponOrigin;

    @Column(length = 255)
    private String code;

    @Column(nullable = false)
    private boolean active;

    @Column(length = 255)
    private String category;

    @Column(length = 255)
    private String subcategory;
    
    @Column
    private String site;

    @OneToMany(mappedBy = "accredited")
    private List<Product> productList;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accredited accredited = (Accredited) o;
        return id != null && id.equals(accredited.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
