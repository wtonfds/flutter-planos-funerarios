package br.com.monitoratec.farol.service.campaign;

import br.com.monitoratec.farol.graphql.model.dtos.campaign.DeliveryModeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.RecurrenceTypeDTO;
import br.com.monitoratec.farol.sql.model.campaign.DeliveryMode;
import br.com.monitoratec.farol.sql.model.campaign.RecurrenceType;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static br.com.monitoratec.farol.service.campaign.ConversionHelper.toDto;
import static br.com.monitoratec.farol.service.campaign.ConversionHelper.toModel;
import static org.junit.jupiter.api.Assertions.*;

class ConversionHelperTest {
    @Test
    void testConstructor() throws NoSuchMethodException {
        final Constructor<ConversionHelper> constructor = ReflectionUtils.accessibleConstructor(ConversionHelper.class);

        final InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof AssertionError);
    }

    @Test
    void testToRecurrenceTypeModel() {
        assertEquals(RecurrenceType.DAILY, toModel(RecurrenceTypeDTO.DAILY));
        assertEquals(RecurrenceType.WEEKLY, toModel(RecurrenceTypeDTO.WEEKLY));
        assertEquals(RecurrenceType.MONTHLY, toModel(RecurrenceTypeDTO.MONTHLY));
    }

    @Test
    void testToDeliveryModeModel() {
        assertEquals(DeliveryMode.EMAIL, toModel(DeliveryModeDTO.EMAIL));
        assertEquals(DeliveryMode.SMS, toModel(DeliveryModeDTO.SMS));
        assertEquals(DeliveryMode.NOTIFICATION, toModel(DeliveryModeDTO.NOTIFICATION));
    }

    @Test
    void testToRecurrenceTypeDto() {
        assertEquals(RecurrenceTypeDTO.DAILY, toDto(RecurrenceType.DAILY));
        assertEquals(RecurrenceTypeDTO.WEEKLY, toDto(RecurrenceType.WEEKLY));
        assertEquals(RecurrenceTypeDTO.MONTHLY, toDto(RecurrenceType.MONTHLY));
    }

    @Test
    void testToDeliveryModeDto() {
        assertEquals(DeliveryModeDTO.EMAIL, toDto(DeliveryMode.EMAIL));
        assertEquals(DeliveryModeDTO.SMS, toDto(DeliveryMode.SMS));
        assertEquals(DeliveryModeDTO.NOTIFICATION, toDto(DeliveryMode.NOTIFICATION));
    }
}
