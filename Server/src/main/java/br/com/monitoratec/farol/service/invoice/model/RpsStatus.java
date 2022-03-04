package br.com.monitoratec.farol.service.invoice.model;

public enum RpsStatus {
    NORMAL((byte) 1),
    CANCELLED((byte) 2);

    private final byte value;

    RpsStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
