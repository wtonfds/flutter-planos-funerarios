package br.com.monitoratec.farol.service.campaign;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import br.com.monitoratec.farol.graphql.customTypes.Time;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.dtos.campaign.CampaignDTO;
import br.com.monitoratec.farol.graphql.model.input.campaign.CampaignInput;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.sql.model.campaign.Campaign;
import br.com.monitoratec.farol.sql.repository.campaign.CampaignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.monitoratec.farol.service.campaign.ConversionHelper.toDto;
import static br.com.monitoratec.farol.service.campaign.ConversionHelper.toModel;
import static br.com.monitoratec.farol.utils.responses.StatusCodes.Error.Campaign.CAMPAIGN_NOT_FOUND;

@Service
public class CampaignService {
    private final CampaignRepository repository;

    public CampaignService(CampaignRepository repository) {
        this.repository = repository;
    }

    public Page<CampaignDTO> list(Long filterByID, String filterByName, Paginate paginate) {
        final Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, Campaign.Fields.ID));

        final Page<Campaign> page = repository.findByFiltersAllNullable(filterByID, filterByName, pageable);
        return page.map(this::toCampaignDto);
    }

    private CampaignDTO toCampaignDto(Campaign model) {
        final CampaignDTO dto = new CampaignDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setStartDate(new Date(model.getStartDate()));
        dto.setEndDate(new Date(model.getEndDate()));
        dto.setRecurrenceType(toDto(model.getRecurrenceType()));
        dto.setRecurrence(model.getRecurrence());
        dto.setTimeToSend(new Time(model.getTimeToSend()));
        dto.setMessage(model.getMessage());
        dto.setDeliveryMode(toDto(model.getDeliveryMode()));
        dto.setInactiveClients(model.isInactiveClients());
        dto.setBirthdayClients(model.isBirthdayClients());
        dto.setChildrenWithAge(model.isChildrenWithAge());
        dto.setExpiringContracts(model.isExpiringContracts());
        dto.setWithoutCoupons(model.isWithoutCoupons());
        dto.setWithoutTem(model.isWithoutTem());
        dto.setWithoutFuneralAssistance(model.isWithoutFuneralAssistance());

        return dto;
    }

    public Optional<CampaignDTO> findById(Long id) {
        return repository.findByIdAndActiveTrue(id)
                .map(this::toCampaignDto);
    }

    public CampaignDTO create(CampaignInput input) {
        final Campaign model = toCampaignModel(input);

        final Campaign saved = repository.save(model);
        return toCampaignDto(saved);
    }

    private Campaign toCampaignModel(CampaignInput input) {
        final Campaign campaign = new Campaign();
        campaign.setActive(true);
        mergeModel(campaign, input);

        return campaign;
    }

    private void mergeModel(Campaign campaign, CampaignInput input) {
        campaign.setName(input.getName());
        campaign.setStartDate(input.getStartDate().getDate());
        campaign.setEndDate(input.getEndDate().getDate());
        campaign.setRecurrenceType(toModel(input.getRecurrenceType()));
        campaign.setRecurrence(input.getRecurrence());
        campaign.setTimeToSend(input.getTimeToSend().getTime());
        campaign.setMessage(input.getMessage());
        campaign.setDeliveryMode(toModel(input.getDeliveryMode()));
        campaign.setInactiveClients(input.isInactiveClients());
        campaign.setBirthdayClients(input.isBirthdayClients());
        campaign.setChildrenWithAge(input.isChildrenWithAge());
        campaign.setExpiringContracts(input.isExpiringContracts());
        campaign.setWithoutCoupons(input.isWithoutCoupons());
        campaign.setWithoutTem(input.isWithoutTem());
        campaign.setWithoutFuneralAssistance(input.isWithoutFuneralAssistance());
    }

    public CampaignDTO update(Long id, CampaignInput input) {
        final Campaign model = repository.findById(id)
                .orElseThrow(() -> new ErrorOnProcessingRequestException(CAMPAIGN_NOT_FOUND));

        mergeModel(model, input);

        final Campaign saved = repository.save(model);
        return toCampaignDto(saved);
    }
}
