package br.com.monitoratec.farol.graphql.model.input.user;

public class ResetFarolUserPasswordInput {
    private String cpf;
    private String email;

    public ResetFarolUserPasswordInput() {
    }

    public ResetFarolUserPasswordInput(String cpf, String email) {
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
