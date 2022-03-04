package br.com.monitoratec.farol.service.plan;

import br.com.monitoratec.farol.graphql.model.dtos.user.ClientTypeDTO;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.sql.model.plan.Plan;
import br.com.monitoratec.farol.sql.model.price.DependentPriceTable;
import br.com.monitoratec.farol.sql.model.price.PlanPriceTable;
import br.com.monitoratec.farol.sql.model.price.PriceTableAgeRange;
import br.com.monitoratec.farol.sql.model.price.UpgradePriceTable;
import br.com.monitoratec.farol.sql.model.user.Client;
import br.com.monitoratec.farol.sql.repository.price.DependentPriceTableRepository;
import br.com.monitoratec.farol.sql.repository.price.PlanPriceTableRepository;
import br.com.monitoratec.farol.sql.repository.price.UpgradePriceTableRepository;
import br.com.monitoratec.farol.utils.data.AgeUtils;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {
    private final UpgradePriceTableRepository upgradePriceTableRepository;
    private final PlanPriceTableRepository planPriceTableRepository;
    private final DependentPriceTableRepository dependentPriceTableRepository;
    private final S3Service s3Service;

    public PlanService(UpgradePriceTableRepository upgradePriceTableRepository, PlanPriceTableRepository planPriceTableRepository,
                       DependentPriceTableRepository dependentPriceTableRepository, S3Service s3Service) {
        this.upgradePriceTableRepository = upgradePriceTableRepository;
        this.planPriceTableRepository = planPriceTableRepository;
        this.dependentPriceTableRepository = dependentPriceTableRepository;
        this.s3Service = s3Service;
    }

    public Plan buildPlanWithAgeRanges(Plan plan) {
        Optional<PlanPriceTable> planPriceTable = planPriceTableRepository.findByIdWithAgeRanges(plan.getPlanPriceTable().getId());
        planPriceTable.ifPresent(plan::setPlanPriceTable);

        Optional<UpgradePriceTable> upgradePriceTable = upgradePriceTableRepository.findByIdWithAgeRanges(plan.getUpgradePriceTable().getId());
        upgradePriceTable.ifPresent(plan::setUpgradePriceTable);

        Optional<DependentPriceTable> dependentPriceTable = dependentPriceTableRepository.findByIdWithAgeRanges(plan.getDependentPriceTable().getId());
        dependentPriceTable.ifPresent(plan::setDependentPriceTable);

        return plan;
    }

    public String generateContract(String html) throws IOException, DocumentException {
        final byte[] pdfData;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            pdfData = outputStream.toByteArray();
        }

        return s3Service.uploadPdf(pdfData);
    }

    private double getUserPriceByAge(LocalDate birthday, List<PriceTableAgeRange> ageRanges) {
        int age = AgeUtils.getAge(birthday);

        for (PriceTableAgeRange p : ageRanges) {
            if (age >= p.getStartAge() && age <= p.getEndAge()) {
                return p.getValue().doubleValue();
            }
        }
        // if user is above the max age range then it will have the last value
        return ageRanges.get(ageRanges.size() - 1).getValue().doubleValue();
    }

    // Children, Spouse and Dependents will not be charged.
    // Holder and extra-dependents will be charged based on their ages.
    // Extra dependents will use their table
    // Holder will use default price table
    // If is an upgrade, ageRanges will be the upgrade plan table
    public double calculatePrice(List<Client> clients, List<PriceTableAgeRange> ageRanges, List<PriceTableAgeRange> extraDependentAgeRange) {
        double basePrice = 0D;
        for (Client client : clients) {
            if (client.getClientType().equals(ClientTypeDTO.EXTRA_DEPENDENT.name())) {
                basePrice += getUserPriceByAge(client.getBirthDay(), extraDependentAgeRange);
            } else if (client.getClientType().equals(ClientTypeDTO.HOLDER.name())) {
                basePrice += getUserPriceByAge(client.getBirthDay(), ageRanges);
            }
        }
        return basePrice;
    }
}
