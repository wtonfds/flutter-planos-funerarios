package br.com.monitoratec.farol.graphql.model.dtos.user;

import br.com.monitoratec.farol.graphql.customTypes.Email;
import br.com.monitoratec.farol.graphql.model.dtos.permission.PermissionTypeDTO;
import br.com.monitoratec.farol.sql.model.user.FarolUser;

import java.util.List;
import java.util.stream.Collectors;

public class FarolUserDTO {

    // SystemUser
    private Long id;
    private Email email;

    // Myself
    private String name;

    private String cpf;
    private String telephone;
    private Long agentNumber;
    private boolean active;
    private boolean isTemporaryPassword;
    private List<PermissionTypeDTO> permissionTypeDTOList;

    public FarolUserDTO(FarolUser farolUser) {
        this.id = farolUser.getId();
        this.email = Email.fromString(farolUser.getEmail());
        this.name = farolUser.getName();
        this.cpf = farolUser.getCPF();
        this.telephone = farolUser.getTelephone();
        this.agentNumber = farolUser.getAgentNumber();
        this.active = farolUser.isActive();
        this.isTemporaryPassword = farolUser.isTemporaryPassword();
        this.permissionTypeDTOList = farolUser.getPermissionList().stream()
                .map(x -> PermissionTypeDTO.valueOf(x.getName()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(Long agentNumber) {
        this.agentNumber = agentNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTemporaryPassword() {
        return isTemporaryPassword;
    }

    public void setTemporaryPassword(boolean temporaryPassword) {
        isTemporaryPassword = temporaryPassword;
    }

    public List<PermissionTypeDTO> getPermissionTypeDTOList() {
        return permissionTypeDTOList;
    }

    public void setPermissionTypeDTOList(List<PermissionTypeDTO> permissionTypeDTOList) {
        this.permissionTypeDTOList = permissionTypeDTOList;
    }
}
