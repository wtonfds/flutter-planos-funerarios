package br.com.monitoratec.farol.service.invoice.model;

import java.util.Optional;

public class Contact {
    private final String phone;
    private final String email;

    public Contact(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }
}
