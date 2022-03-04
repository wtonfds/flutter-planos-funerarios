package br.com.monitoratec.farol.sql.model.user;

import br.com.monitoratec.farol.sql.interfaces.user.LoggableViaCPF;
import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.model.lotteryNumber.LotteryNumber;
import br.com.monitoratec.farol.sql.model.permission.Permission;
import br.com.monitoratec.farol.sql.model.product.Product;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "frl_user_farol")
public class FarolUser extends PhysicalUser implements LoggableViaCPF {

    private Long agentNumber;

    @OneToMany(mappedBy = "approvedBy")
    private List<LotteryNumber> lotteryNumberList;

    public Long getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(Long agentNumber) {
        this.agentNumber = agentNumber;
    }

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "createdBy")
    private List<Product> productList;

    @ManyToMany
    private List<Permission> permissionList;

    // Login via CPF
    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    public List<LotteryNumber> getLotteryNumberList() {
        return lotteryNumberList;
    }

    public void setLotteryNumberList(List<LotteryNumber> lotteryNumberList) {
        this.lotteryNumberList = lotteryNumberList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public static class Fields {
        public static final String ID = "id";
    }
}
