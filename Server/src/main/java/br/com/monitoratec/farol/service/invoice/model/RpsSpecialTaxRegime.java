package br.com.monitoratec.farol.service.invoice.model;

public enum RpsSpecialTaxRegime {
    MUNICIPAL_MICRO_ENTERPRISE((byte) 1),
    ESTIMATE((byte) 2),
    PROFESSIONAL_SOCIETY((byte) 3),
    COOPERATIVE((byte) 4),
    INDIVIDUAL_MICRO_ENTREPRENEUR((byte) 5), //MEI
    MICRO_ENTREPRENEUR_AND_SMALL_BUSINESS((byte) 6); //ME EPP

    private final byte value;

    RpsSpecialTaxRegime(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
