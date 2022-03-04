package br.com.monitoratec.farol.service.campaign;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Time;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.input.campaign.CampaignInput;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.DeliveryModeDTO;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.RecurrenceTypeDTO;
import br.com.monitoratec.farol.sql.model.campaign.Campaign;
import br.com.monitoratec.farol.sql.model.price.PriceTable;
import br.com.monitoratec.farol.sql.repository.campaign.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static br.com.monitoratec.farol.factory.CampaignDTOFatory.*;
import static br.com.monitoratec.farol.service.campaign.ConversionHelper.toModel;
import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.Campaign.CAMPAIGN_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CampaignServiceTest {
    private CampaignService service;

    @Autowired
    private CampaignRepository repository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setup() {
        service = new CampaignService(repository);
    }

    @Test
    void testListWithoutFilters() {
        final Page<CampaignDTO> page = service.list(null, null, null);

        final List<CampaignDTO> expectedList = List.of(
                getCampaign4(),
                getCampaign2(),
                getCampaign1()
        );

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, Campaign.Fields.ID));
        final PageImpl<CampaignDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 3L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithFilterById() {
        final Page<CampaignDTO> page = service.list(4L, null, null);

        final List<CampaignDTO> expectedList = List.of(getCampaign4());

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, Campaign.Fields.ID));
        final PageImpl<CampaignDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 1L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithFilterByName() {
        final Page<CampaignDTO> page = service.list(null, "Other", null);

        final List<CampaignDTO> expectedList = List.of(getCampaign4());

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, Campaign.Fields.ID));
        final PageImpl<CampaignDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 1L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithPaginate() {
        final Paginate paginate = new Paginate(2, 0);
        final Page<CampaignDTO> page = service.list(null, null, paginate);

        final List<CampaignDTO> expectedList = List.of(
                getCampaign4(),
                getCampaign2()
        );

        final Pageable expectedPageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final PageImpl<CampaignDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 3L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testFindByIdNonExisting() {
        final Optional<CampaignDTO> optional = service.findById(100L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void testFindByIdInactive() {
        final Optional<CampaignDTO> optional = service.findById(3L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void testFindByIdExisting() {
        final Optional<CampaignDTO> optional = service.findById(2L);

        final CampaignDTO expected = getCampaign2();
        assertEquals(Optional.of(expected), optional);
    }

    @Test
    void testCreate() {
        final CampaignInput input = new CampaignInput();
        input.setName("New campaign");
        input.setStartDate(new Date(LocalDate.of(2019, 1, 1)));
        input.setEndDate(new Date(LocalDate.of(2023, 12, 31)));
        input.setRecurrenceType(RecurrenceTypeDTO.WEEKLY);
        input.setRecurrence(3);
        input.setTimeToSend(new Time(LocalTime.of(15, 0)));
        input.setMessage("Example message");
        input.setDeliveryMode(DeliveryModeDTO.EMAIL);
        input.setInactiveClients(true);
        input.setBirthdayClients(false);
        input.setChildrenWithAge(true);
        input.setExpiringContracts(false);
        input.setWithoutCoupons(true);
        input.setWithoutTem(false);
        input.setWithoutFuneralAssistance(true);

        final CampaignDTO dto = service.create(input);
        assertMatches(input, dto);

        final Campaign saved = em.find(Campaign.class, dto.getId());
        assertMatches(dto, saved);
    }

    private void assertMatches(CampaignInput input, CampaignDTO dto) {
        assertEquals(input.getName(), dto.getName());
        assertEquals(input.getStartDate(), dto.getStartDate());
        assertEquals(input.getEndDate(), dto.getEndDate());
        assertEquals(input.getRecurrenceType(), dto.getRecurrenceType());
        assertEquals(input.getRecurrence(), dto.getRecurrence());
        assertEquals(input.getTimeToSend(), dto.getTimeToSend());
        assertEquals(input.getMessage(), dto.getMessage());
        assertEquals(input.getDeliveryMode(), dto.getDeliveryMode());
        assertEquals(input.isInactiveClients(), dto.isInactiveClients());
        assertEquals(input.isBirthdayClients(), dto.isBirthdayClients());
        assertEquals(input.isChildrenWithAge(), dto.isChildrenWithAge());
        assertEquals(input.isExpiringContracts(), dto.isExpiringContracts());
        assertEquals(input.isWithoutCoupons(), dto.isWithoutCoupons());
        assertEquals(input.isWithoutTem(), dto.isWithoutTem());
        assertEquals(input.isWithoutFuneralAssistance(), dto.isWithoutFuneralAssistance());
    }

    private void assertMatches(CampaignDTO dto, Campaign entity) {
        assertEquals(dto.getId(), entity.getId());
        assertTrue(entity.isActive());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getStartDate().getDate(), entity.getStartDate());
        assertEquals(dto.getEndDate().getDate(), entity.getEndDate());
        assertEquals(toModel(dto.getRecurrenceType()), entity.getRecurrenceType());
        assertEquals(dto.getRecurrence(), entity.getRecurrence());
        assertEquals(dto.getTimeToSend().getTime(), entity.getTimeToSend());
        assertEquals(dto.getMessage(), entity.getMessage());
        assertEquals(toModel(dto.getDeliveryMode()), entity.getDeliveryMode());
        assertEquals(dto.isInactiveClients(), entity.isInactiveClients());
        assertEquals(dto.isBirthdayClients(), entity.isBirthdayClients());
        assertEquals(dto.isChildrenWithAge(), entity.isChildrenWithAge());
        assertEquals(dto.isExpiringContracts(), entity.isExpiringContracts());
        assertEquals(dto.isWithoutCoupons(), entity.isWithoutCoupons());
        assertEquals(dto.isWithoutTem(), entity.isWithoutTem());
        assertEquals(dto.isWithoutFuneralAssistance(), entity.isWithoutFuneralAssistance());
    }

    @Test
    void testUpdateNonExisting() {
        final Long id = 35L;

        final CampaignInput input = new CampaignInput();
        input.setName("Campaign to be updated");
        input.setStartDate(new Date(LocalDate.of(2018, 12, 21)));
        input.setEndDate(new Date(LocalDate.of(2021, 12, 15)));
        input.setRecurrenceType(RecurrenceTypeDTO.DAILY);
        input.setRecurrence(1);
        input.setTimeToSend(new Time(LocalTime.of(20, 15)));
        input.setMessage("Example message");
        input.setDeliveryMode(DeliveryModeDTO.SMS);
        input.setInactiveClients(true);
        input.setBirthdayClients(true);
        input.setChildrenWithAge(false);
        input.setExpiringContracts(false);
        input.setWithoutCoupons(false);
        input.setWithoutTem(true);
        input.setWithoutFuneralAssistance(true);

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.update(id, input));
        assertEquals(CAMPAIGN_NOT_FOUND, exception.getCommonResponse());
    }

    @Test
    void testUpdateExisting() {
        final Long id = 2L;

        final CampaignInput input = new CampaignInput();
        input.setName("Campaign to be updated");
        input.setStartDate(new Date(LocalDate.of(2018, 4, 19)));
        input.setEndDate(new Date(LocalDate.of(2021, 9, 26)));
        input.setRecurrenceType(RecurrenceTypeDTO.DAILY);
        input.setRecurrence(3);
        input.setTimeToSend(new Time(LocalTime.of(16, 16)));
        input.setMessage("Example message");
        input.setDeliveryMode(DeliveryModeDTO.NOTIFICATION);
        input.setInactiveClients(false);
        input.setBirthdayClients(false);
        input.setChildrenWithAge(true);
        input.setExpiringContracts(true);
        input.setWithoutCoupons(false);
        input.setWithoutTem(false);
        input.setWithoutFuneralAssistance(true);

        final CampaignDTO dto = service.update(id, input);
        assertMatches(input, dto);

        final Campaign saved = em.find(Campaign.class, id);
        assertMatches(dto, saved);
    }
}
