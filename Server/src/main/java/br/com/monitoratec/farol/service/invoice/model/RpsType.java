package br.com.monitoratec.farol.service.invoice.model;

public enum RpsType {
    RPS((byte) 1),
    MIXED((byte) 2),
    COUPON((byte) 3);

    private final byte value;

    RpsType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
