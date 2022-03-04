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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.PriceTable.PLAN_PRICE_TABLE_NOT_FOUND;

@Service
public class PlanPriceTableService {
    private final PlanPriceTableValidationService validationService;
    private final PlanPriceTableRepository repository;

    public PlanPriceTableService(PlanPriceTableValidationService validationService, PlanPriceTableRepository repository) {
        this.validationService = validationService;
        this.repository = repository;
    }

    public Page<PlanPriceTableDTO> list(Long filterByID, String filterByName, Paginate paginate) {
        //Note: this method will not return the age ranges, only the info from PriceTable; if they are necessary, change
        //the query and the conversion
        Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));

        final Page<PlanPriceTable> page = repository.findByFiltersAllNullable(filterByID, filterByName, pageable);
        return page.map(model -> new PlanPriceTableDTO(model.getId(), model.getName()));
    }

    public Optional<PlanPriceTableDTO> findById(Long id) {
        return repository.findByIdWithAgeRanges(id)
                .map(this::toDto);
    }

    private PlanPriceTableDTO toDto(PriceTable model) {
        final PlanPriceTableDTO dto = new PlanPriceTableDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setAgeRanges(toAgeRangeDtos(model.getAgeRanges()));

        return dto;
    }

    private List<PriceTableAgeRangeDTO> toAgeRangeDtos(List<PriceTableAgeRange> models) {
        final List<PriceTableAgeRangeDTO> dtos = new ArrayList<>(models.size());

        for (PriceTableAgeRange model : models) {
            final PriceTableAgeRangeDTO dto = new PriceTableAgeRangeDTO();
            dto.setId(model.getId());
            dto.setStartAge(model.getStartAge());
            dto.setEndAge(model.getEndAge());
            dto.setValue(model.getValue().doubleValue());

            dtos.add(dto);
        }

        return dtos;
    }

    public PlanPriceTableDTO create(PlanPriceTableInput input) {
        validationService.validateAgeRanges(input);

        final PlanPriceTable model = toModel(input);

        final PlanPriceTable saved = repository.save(model);
        return toDto(saved);
    }

    private PlanPriceTable toModel(PlanPriceTableInput input) {
        final PlanPriceTable model = new PlanPriceTable();
        model.setName(input.getName());
        model.setAgeRanges(toAgeRangeModels(input.getAgeRanges()));

        return model;
    }

    private List<PriceTableAgeRange> toAgeRangeModels(List<PriceTableAgeRangeInput> inputs) {
        final List<PriceTableAgeRange> models = new ArrayList<>(inputs.size());

        for (PriceTableAgeRangeInput input : inputs) {
            final PriceTableAgeRange model = new PriceTableAgeRange();
            model.setStartAge(input.getStartAge());
            model.setEndAge(input.getEndAge());
            model.setValue(BigDecimal.valueOf(input.getValue()));

            models.add(model);
        }

        return models;
    }

    public PlanPriceTableDTO update(Long id, PlanPriceTableInput input) {
        validationService.validateAgeRanges(input);

        final PlanPriceTable model = mergeModel(id, input);

        final PlanPriceTable saved = repository.save(model);
        return toDto(saved);
    }

    private PlanPriceTable mergeModel(Long id, PlanPriceTableInput input) {
        final PlanPriceTable model = repository.findByIdWithAgeRanges(id)
                .orElseThrow(() -> new ErrorOnProcessingRequestException(PLAN_PRICE_TABLE_NOT_FOUND));

        model.setName(input.getName());
        mergeAgeRangeModels(model.getAgeRanges(), input.getAgeRanges());

        return model;
    }

    private void mergeAgeRangeModels(List<PriceTableAgeRange> models, List<PriceTableAgeRangeInput> inputs) {
        final Iterator<PriceTableAgeRange> modelIterator = models.iterator();
        final Iterator<PriceTableAgeRangeInput> inputIterator = inputs.iterator();

        //Existing = update
        while (modelIterator.hasNext() && inputIterator.hasNext()) {
            final PriceTableAgeRange model = modelIterator.next();
            final PriceTableAgeRangeInput input = inputIterator.next();

            model.setStartAge(input.getStartAge());
            model.setEndAge(input.getEndAge());
            model.setValue(BigDecimal.valueOf(input.getValue()));
        }

        //Remaining = delete
        while (modelIterator.hasNext()) {
            modelIterator.next();
            modelIterator.remove();
        }

        //New = create
        while (inputIterator.hasNext()) {
            final PriceTableAgeRangeInput input = inputIterator.next();

            final PriceTableAgeRange model = new PriceTableAgeRange();
            model.setStartAge(input.getStartAge());
            model.setEndAge(input.getEndAge());
            model.setValue(BigDecimal.valueOf(input.getValue()));

            models.add(model);
        }
    }
}
