package br.com.monitoratec.farol.service.invoice.model;

public enum RpsOperationNature {
    TAXATION_INSIDE_MUNICIPALITY((byte) 1),
    TAXATION_OUTSIDE_MUNICIPALITY((byte) 2),
    EXEMPTION((byte) 3),
    IMMUNE((byte) 4),
    ENFORCEABILITY_SUSPENDED_BY_JUDICIAL_DECISION((byte) 5),
    ENFORCEABILITY_SUSPENDED_BY_ADMINISTRATIVE_PROCEDURE((byte) 6);

    private final byte value;

    RpsOperationNature(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
