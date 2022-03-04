package br.com.monitoratec.farol.service.price;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.price.PlanPriceTableInput;
import br.com.monitoratec.farol.graphql.model.input.price.PriceTableAgeRangeInput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.PriceTable.*;

@Service
public class PlanPriceTableValidationService {
    public void validateAgeRanges(PlanPriceTableInput input) {
        final List<PriceTableAgeRangeInput> ageRanges = new ArrayList<>(input.getAgeRanges());
        if (ageRanges.isEmpty()) {
            throw new ErrorOnProcessingRequestException(EMPTY_AGE_RANGE);
        }

        //Sort the list by start age and end age
        final Comparator<PriceTableAgeRangeInput> comparator = Comparator.comparing(PriceTableAgeRangeInput::getStartAge)
                .thenComparing(PriceTableAgeRangeInput::getEndAge);
        ageRanges.sort(comparator);

        final Iterator<PriceTableAgeRangeInput> iterator = ageRanges.iterator();

        //Only basic validations for the first element
        final PriceTableAgeRangeInput first = iterator.next();
        validateAgeRange(first);

        int upper = first.getEndAge();

        while (iterator.hasNext()) {
            final PriceTableAgeRangeInput ageRange = iterator.next();
            validateAgeRange(ageRange);

            //The start age must avoid overlaps and gaps with the previous end one
            final int startAge = ageRange.getStartAge();
            if (startAge <= upper) {
                throw new ErrorOnProcessingRequestException(OVERLAPPING_AGE);
            }
            if (startAge > upper + 1) {
                throw new ErrorOnProcessingRequestException(AGE_NOT_COVERED);
            }

            upper = ageRange.getEndAge();
        }
    }

    private void validateAgeRange(PriceTableAgeRangeInput ageRange) {
        final int startAge = ageRange.getStartAge();
        final int endAge = ageRange.getEndAge();

        if (startAge < 0 || endAge < 0 || startAge > endAge) {
            throw new ErrorOnProcessingRequestException(INVALID_AGE_RANGE);
        }
    }
}
