package br.com.monitoratec.farol.factory;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Time;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.DeliveryModeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.RecurrenceTypeDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import static br.com.monitoratec.farol.graphql.model.dtos.campaign.DeliveryModeDTO.*;
import static br.com.monitoratec.farol.graphql.model.dtos.campaign.RecurrenceTypeDTO.*;

public final class CampaignDTOFatory {
    private CampaignDTOFatory() {
        throw new AssertionError();
    }

    public static CampaignDTO getCampaign1() {
        return getCampaign(1, "Campaign 1", LocalDate.of(2017, 1, 1), LocalDate.of(2025, 1, 1),
                MONTHLY, 2, LocalTime.of(15, 0), "Example message 1", EMAIL, false, false,
                true, true, false, false, true);
    }

    private static CampaignDTO getCampaign(long id, String name, LocalDate startDate, LocalDate endDate,
                                           RecurrenceTypeDTO recurrenceType, int recurrence, LocalTime timeToSend,
                                           String message, DeliveryModeDTO deliveryMode, boolean inactiveClients,
                                           boolean birthdayClients, boolean childrenWithAge, boolean expiringContracts,
                                           boolean withoutCoupons, boolean withoutTem, boolean withoutFuneralAssistance) {
        final CampaignDTO expected = new CampaignDTO();
        expected.setId(id);
        expected.setName(name);
        expected.setStartDate(new Date(startDate));
        expected.setEndDate(new Date(endDate));
        expected.setRecurrenceType(recurrenceType);
        expected.setRecurrence(recurrence);
        expected.setTimeToSend(new Time(timeToSend));
        expected.setMessage(message);
        expected.setDeliveryMode(deliveryMode);
        expected.setInactiveClients(inactiveClients);
        expected.setBirthdayClients(birthdayClients);
        expected.setChildrenWithAge(childrenWithAge);
        expected.setExpiringContracts(expiringContracts);
        expected.setWithoutCoupons(withoutCoupons);
        expected.setWithoutTem(withoutTem);
        expected.setWithoutFuneralAssistance(withoutFuneralAssistance);

        return expected;
    }

    public static CampaignDTO getCampaign2() {
        return getCampaign(2, "Campaign 2", LocalDate.of(2018, 6, 30), LocalDate.of(2020, 4, 18),
                WEEKLY, 5, LocalTime.of(9, 0), "Some other message", SMS, true, true,
                false, false, true, true, false);
    }

    public static CampaignDTO getCampaign4() {
        return getCampaign(4, "Yet another campaign", LocalDate.of(2020, 1, 1), LocalDate.of(2024, 5, 31),
                DAILY, 3, LocalTime.of(13, 0), "Yet another message", NOTIFICATION, false, false,
                false, true, false, true, false);
    }
}
