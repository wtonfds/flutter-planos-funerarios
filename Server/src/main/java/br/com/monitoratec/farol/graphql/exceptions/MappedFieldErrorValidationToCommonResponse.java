package br.com.monitoratec.farol.graphql.exceptions;


import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import br.com.monitoratec.farol.graphql.customTypes.Time;

public enum MappedFieldErrorValidationToCommonResponse {
    EMAIL, DATE, DATE_TIME, TIME, UNMAPPED, SEX_ENUM;

    public String fieldExpectedPattern() {
        switch (this) {
            case DATE:
                return Date.getExpectedFormat();
            case DATE_TIME:
                return DateTime.getExpectedFormat();
            case TIME:
                return Time.getExpectedFormat();
            // The values below should fall on the default case, the pattern will not be exposed to the frontend
            case EMAIL:
            case UNMAPPED:
            default:
                return "N/A";
        }
    }
}
