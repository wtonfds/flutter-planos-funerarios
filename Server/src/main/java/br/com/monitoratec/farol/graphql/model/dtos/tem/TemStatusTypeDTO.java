package br.com.monitoratec.farol.graphql.model.dtos.tem;

public enum TemStatusTypeDTO {
    SUSPEND("8"),
    REACTIVATE("1"),
    CANCEL("3");

    public String value;

    TemStatusTypeDTO(String s) {
        value = s;
    }
}
