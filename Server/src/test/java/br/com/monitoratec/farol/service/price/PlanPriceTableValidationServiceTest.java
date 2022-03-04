package br.com.monitoratec.farol.service.price;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.price.PlanPriceTableInput;
import br.com.monitoratec.farol.graphql.model.input.price.PriceTableAgeRangeInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.PriceTable.*;
import static org.junit.jupiter.api.Assertions.*;

class PlanPriceTableValidationServiceTest {
    private PlanPriceTableValidationService service;

    @BeforeEach
    void setup() {
        service = new PlanPriceTableValidationService();
    }

    @Test
    void testValidateAgeRangesWithEmptyRanges() {
        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of());

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(EMPTY_AGE_RANGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithOverlappingRanges() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 42, 50_000d);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(41, 80, 79_999.99);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput1, ageInput2));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(OVERLAPPING_AGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithEqualRangeEdges() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 50, 50_000d);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(50, 80, 79_999.99);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput1, ageInput2));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(OVERLAPPING_AGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithGap() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 30, 50_000d);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(32, 80, 79_999.99);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput1, ageInput2));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(AGE_NOT_COVERED, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithInvalidStartAge() {
        final PriceTableAgeRangeInput ageInput = new PriceTableAgeRangeInput(-1, 70, 50_000d);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(INVALID_AGE_RANGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithInvalidEndAge() {
        final PriceTableAgeRangeInput ageInput = new PriceTableAgeRangeInput(10, -1, 50_000d);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(INVALID_AGE_RANGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithEndAgeGreaterThanStartAge() {
        final PriceTableAgeRangeInput ageInput = new PriceTableAgeRangeInput(50, 49, 50_000d);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table", List.of(ageInput));

        final ErrorOnProcessingRequestException exception = assertThrows(ErrorOnProcessingRequestException.class, () -> service.validateAgeRanges(input));
        assertEquals(INVALID_AGE_RANGE, exception.getCommonResponse());
    }

    @Test
    void testValidateAgeRangesWithValidRanges() {
        final PriceTableAgeRangeInput ageInput1 = new PriceTableAgeRangeInput(0, 30, 50_000d);
        final PriceTableAgeRangeInput ageInput2 = new PriceTableAgeRangeInput(60, 100, 111_111.11);
        final PriceTableAgeRangeInput ageInput3 = new PriceTableAgeRangeInput(31, 59, 123_456.78);

        final PlanPriceTableInput input = new PlanPriceTableInput("Table 1", List.of(ageInput1, ageInput2, ageInput3));
        assertDoesNotThrow(() -> service.validateAgeRanges(input));
    }
}
