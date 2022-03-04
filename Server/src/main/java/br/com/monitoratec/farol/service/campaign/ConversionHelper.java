package br.com.monitoratec.farol.service.campaign;

import br.com.monitoratec.farol.graphql.model.dtos.campaign.DeliveryModeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.RecurrenceTypeDTO;
import br.com.monitoratec.farol.sql.model.campaign.DeliveryMode;
import br.com.monitoratec.farol.sql.model.campaign.RecurrenceType;

public final class ConversionHelper {
    private ConversionHelper() {
        throw new AssertionError();
    }

    public static RecurrenceType toModel(RecurrenceTypeDTO dto) {
        switch (dto) {
            case DAILY:
                return RecurrenceType.DAILY;
            case WEEKLY:
                return RecurrenceType.WEEKLY;
            default: //MONTHLY
                return RecurrenceType.MONTHLY;
        }
    }

    public static DeliveryMode toModel(DeliveryModeDTO dto) {
        switch (dto) {
            case EMAIL:
                return DeliveryMode.EMAIL;
            case SMS:
                return DeliveryMode.SMS;
            default: //NOTIFICATION
                return DeliveryMode.NOTIFICATION;
        }
    }

    public static RecurrenceTypeDTO toDto(RecurrenceType model) {
        switch (model) {
            case DAILY:
                return RecurrenceTypeDTO.DAILY;
            case WEEKLY:
                return RecurrenceTypeDTO.WEEKLY;
            default: //MONTHLY
                return RecurrenceTypeDTO.MONTHLY;
        }
    }

    public static DeliveryModeDTO toDto(DeliveryMode model) {
        switch (model) {
            case EMAIL:
                return DeliveryModeDTO.EMAIL;
            case SMS:
                return DeliveryModeDTO.SMS;
            default: //NOTIFICATION
                return DeliveryModeDTO.NOTIFICATION;
        }
    }
}
