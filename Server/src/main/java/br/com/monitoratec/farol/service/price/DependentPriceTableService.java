package br.com.monitoratec.farol.service.price;

import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.price.DependentPriceTableDTO;
import br.com.monitoratec.farol.graphql.model.dtos.price.PriceTableAgeRangeDTO;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.input.price.DependentPriceTableInput;
import br.com.monitoratec.farol.graphql.model.input.price.PriceTableAgeRangeInput;
import br.com.monitoratec.farol.sql.model.price.DependentPriceTable;
import br.com.monitoratec.farol.sql.model.price.PriceTable;
import br.com.monitoratec.farol.sql.model.price.PriceTableAgeRange;
import br.com.monitoratec.farol.sql.repository.price.DependentPriceTableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.PriceTable.DEPENDENT_PRICE_TABLE_NOT_FOUND;

@Service
public class DependentPriceTableService {
    private final DependentPriceTableValidationService validationService;
    private final DependentPriceTableRepository repository;

    public  DependentPriceTableService(DependentPriceTableValidationService validationService, DependentPriceTableRepository repository) {
        this.validationService = validationService;
        this.repository = repository;
    }

    public Page<DependentPriceTableDTO> list(Long filterByID, String filterByName, Paginate paginate) {
        //Note: this method will not return the age ranges, only the info from PriceTable; if they are necessary, change
        //the query and the conversion
        Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, PriceTable.Fields.ID));
        final Page<DependentPriceTable>  page = repository.findByFiltersAllNullable(filterByID, filterByName, pageable);
        return page.map(model -> new DependentPriceTableDTO(model.getId(), model.getName()));
    }

    public Optional<DependentPriceTableDTO> findById(Long id) {
        return repository.findByIdWithAgeRanges(id)
                .map(this::toDto);
    }

    private DependentPriceTableDTO toDto(PriceTable model) {
        final DependentPriceTableDTO dto = new DependentPriceTableDTO();
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

    public DependentPriceTableDTO create(DependentPriceTableInput input) {
        validationService.validateAgeRanges(input);

        final DependentPriceTable model = toModel(input);

        final DependentPriceTable saved = repository.save(model);
        return toDto(saved);
    }

    private DependentPriceTable toModel(DependentPriceTableInput input) {
        final DependentPriceTable model = new DependentPriceTable();
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

    public DependentPriceTableDTO update(Long id, DependentPriceTableInput input) {
        validationService.validateAgeRanges(input);

        final DependentPriceTable model = mergeModel(id, input);

        final DependentPriceTable saved = repository.save(model);
        return toDto(saved);
    }

    private DependentPriceTable mergeModel(Long id, DependentPriceTableInput input) {
        final DependentPriceTable model = repository.findByIdWithAgeRanges(id)
                .orElseThrow(() -> new ErrorOnProcessingRequestException(DEPENDENT_PRICE_TABLE_NOT_FOUND));

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
