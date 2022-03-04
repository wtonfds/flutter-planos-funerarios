package br.com.monitoratec.farol.service.invoice.model;

import java.util.Optional;

public class TakerData {
    private final TakerInfo takerInfo;
    private final String companyName;
    private final FullAddress address;
    private final Contact contact;

    public TakerData(TakerInfo takerInfo, String companyName, FullAddress address, Contact contact) {
        this.takerInfo = takerInfo;
        this.companyName = companyName;
        this.address = address;
        this.contact = contact;
    }

    public Optional<TakerInfo> getTakerInfo() {
        return Optional.ofNullable(takerInfo);
    }

    public Optional<String> getCompanyName() {
        return Optional.ofNullable(companyName);
    }

    public Optional<FullAddress> getAddress() {
        return Optional.ofNullable(address);
    }

    public Optional<Contact> getContact() {
        return Optional.ofNullable(contact);
    }
}
