package br.com.monitoratec.farol.service.price;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.price.PlanPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.dtos.price.PriceTableAgeRangeDTO;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.input.price.PlanPriceTableInput;
import br.com.monitoratec.farol.graphql.model.input.price.PriceTableAgeRangeInput;
import br.com.monitoratec.farol.sql.model.price.PlanPriceTable;
import br.com.monitoratec.farol.sql.model.price.PriceTable;
import br.com.monitoratec.farol.sql.model.price.PriceTableAgeRange;
import br.com.monitoratec.farol.sql.repository.price.PlanPriceTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static br.com.monitoratec.farol.util.TestUtils.assertIterablesMatch;
import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.PriceTable.PLAN_PRICE_TABLE_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class PlanPriceTableServiceTest {
    private PlanPriceTableService service;

    @Mock
    private PlanPriceTableValidationService validationService;

    @Autowired
    private PlanPriceTableRepository repository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setup() {
        service = new PlanPriceTableService(validationService, repository);
    }

    @Test
    void testListWithoutFilters() {
        final Page<PlanPriceTableDTO> page = service.list(null, null, null);

        final List<PlanPriceTableDTO> expectedList = List.of(
                new PlanPriceTableDTO(4L, "Yet another table"),
                new PlanPriceTableDTO(3L, "Another table"),
                new PlanPriceTableDTO(2L, "Table 2"),
                new PlanPriceTableDTO(1L, "Table 1")
        );

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final PageImpl<PlanPriceTableDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 4L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithFilterById() {
        final Page<PlanPriceTableDTO> page = service.list(2L, null, null);

        final List<PlanPriceTableDTO> expectedList = List.of(new PlanPriceTableDTO(2L, "Table 2"));

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final PageImpl<PlanPriceTableDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 1L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithFilterByName() {
        final Page<PlanPriceTableDTO> page = service.list(null, "Other", null);

        final List<PlanPriceTableDTO> expectedList = List.of(
                new PlanPriceTableDTO(4L, "Yet another table"),
                new PlanPriceTableDTO(3L, "Another table")
        );

        final Pageable expectedPageable = Paginate.getPageable(null, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final PageImpl<PlanPriceTableDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 2L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testListWithPaginate() {
        final Paginate paginate = new Paginate(2, 1);
        final Page<PlanPriceTableDTO> page = service.list(null, null, paginate);

        final List<PlanPriceTableDTO> expectedList = List.of(
                new PlanPriceTableDTO(2L, "Table 2"),
                new PlanPriceTableDTO(1L, "Table 1")
        );

        final Pageable expectedPageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final PageImpl<PlanPriceTableDTO> expectedPage = new PageImpl<>(expectedList, expectedPageable, 4L);

        assertEquals(expectedPage, page);
    }

    @Test
    void testFindByIdNonExisting() {
        final Optional<PlanPriceTableDTO> optional = service.findById(100L);
        assertTrue(optional.isEmpty());
    }

    @Test
    void testFindByIdExisting() {
        final Optional<PlanPriceTableDTO> optional = service.findById(2L);

        final PlanPriceTableDTO expected = new PlanPriceTableDTO(2L, "Table 2");

        final List<PriceTableAgeRangeDTO> expectedAgeRanges = List.of(
                new PriceTableAgeRangeDTO(3, 15, 60, 13_000d),
                new PriceTableAgeRangeDTO(4, 61, 89, 26_000d)
        );
        expected.setAgeRanges(expectedAgeRanges);

        assertEquals(Optional.of(expected), optional);
    }

    @Test
    void testCreate() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 30, 50_000d);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(31, 100, 111_111.11);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table 1", List.of(ageInput1, ageInput2));

        final PlanPriceTableDTO dto = service.create(input);
        assertMatches(input, dto);

        final PlanPriceTable saved = em.find(PlanPriceTable.class, dto.getId());
        assertMatches(dto, saved);

        verify(validationService).validateAgeRanges(input);
    }

    private void assertMatches(PlanPriceTableInput input, PlanPriceTableDTO saved) {
        assertEquals(input.getName(), saved.getName());
        assertIterablesMatch(input.getAgeRanges(), saved.getAgeRanges(), this::matches);
    }

    private void matches(PriceTableAgeRangeInput input, PriceTableAgeRangeDTO dto) {
        assertEquals(input.getStartAge(), dto.getStartAge());
        assertEquals(input.getEndAge(), dto.getEndAge());
        assertEquals(input.getValue(), dto.getValue());
    }

    private void assertMatches(PlanPriceTableDTO dto, PlanPriceTable entity) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertIterablesMatch(dto.getAgeRanges(), entity.getAgeRanges(), this::matches);
    }

    private void matches(PriceTableAgeRangeDTO dto, PriceTableAgeRange entity) {
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getStartAge(), entity.getStartAge());
        assertEquals(dto.getEndAge(), entity.getEndAge());
        assertEquals(dto.getValue(), entity.getValue().doubleValue());
    }

    @Test
    void testUpdateNonExisting() {
        final PriceTableAgeRangeInput ageInput = new PriceTableAgeRangeInput(0, 99, 50_000d);

        final Long id = 50L;
        final PlanPriceTableInput input = new PlanPriceTableInput("Non existing", List.of(ageInput));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.update(id, input));
        assertEquals(PLAN_PRICE_TABLE_NOT_FOUND, exception.getCommonResponse());
    }

    @Test
    void testUpdateWithLessRanges() {
        final PriceTableAgeRangeInput ageInput = new PriceTableAgeRangeInput(0, 100, 111_111.11);

        final Long id = 1L;
        final PlanPriceTableInput input = new PlanPriceTableInput("Table 1 updated", List.of(ageInput));

        final PlanPriceTableDTO dto = service.update(id, input);
        assertMatches(input, dto);

        final PlanPriceTable saved = em.find(PlanPriceTable.class, id);
        assertMatches(dto, saved);

        verify(validationService).validateAgeRanges(input);
    }

    @Test
    void testUpdateWithEqualRangeSize() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(45, 80, 654_312.98);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(0, 44, 46_460.46);

        final Long id = 1L;
        final PlanPriceTableInput input = new PlanPriceTableInput("Table 1 updated", List.of(ageInput1, ageInput2));

        final PlanPriceTableDTO dto = service.update(id, input);
        assertMatches(input, dto);

        final PlanPriceTable saved = em.find(PlanPriceTable.class, id);
        assertMatches(dto, saved);

        verify(validationService).validateAgeRanges(input);
    }

    @Test
    void testUpdateWithMoreRanges() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 44, 46_460.46);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(45, 80, 100_100d);
        final PriceTableAgeRangeInput ageInput3 = new PriceTableAgeRangeInput(81, 99, 654_312.98);

        final Long id = 1L;
        final PlanPriceTableInput input = new PlanPriceTableInput("Table 1 updated", List.of(ageInput1, ageInput2, ageInput3));

        final PlanPriceTableDTO dto = service.update(id, input);
        assertMatches(input, dto);

        final PlanPriceTable saved = em.find(PlanPriceTable.class, id);
        assertMatches(dto, saved);

        verify(validationService).validateAgeRanges(input);
    }
}
