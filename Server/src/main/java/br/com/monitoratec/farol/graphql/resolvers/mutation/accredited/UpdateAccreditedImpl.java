package br.com.monitoratec.farol.graphql.resolvers.mutation.accredited;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.accredited.AccreditedInfo;
import br.com.monitoratec.farol.graphql.model.input.common.AddressInput;
import br.com.monitoratec.farol.graphql.model.responses.accredited.CommonResponseWithAccreditedInformation;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.service.s3.S3Service;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import br.com.monitoratec.farol.sql.model.location.Address;
import br.com.monitoratec.farol.sql.repository.accredited.AccreditedRepository.AccreditedRepository;
import br.com.monitoratec.farol.sql.repository.location.AddressRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class UpdateAccreditedImpl extends BaseResolver implements GraphQLMutationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAccreditedImpl.class);

    private final AccreditedRepository accreditedRepository;
    private final AddressRepository addressRepository;
    private final S3Service s3Service;

    public UpdateAccreditedImpl(
            AccreditedRepository accreditedRepository,
            AddressRepository addressRepository,
            S3Service s3Service) {
        this.accreditedRepository = accreditedRepository;
        this.addressRepository = addressRepository;
        this.s3Service = s3Service;
    }

    public CompletableFuture<CommonResponseWithAccreditedInformation> updateAccreditedInfo (
            long id,
            AccreditedInfo accreditedInfo,
            AddressInput addressInput,
            DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithAccreditedInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Optional<Accredited> accreditedOptional = accreditedRepository.findById(id);
                    if (accreditedOptional.isEmpty()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Accredited.ACCREDITED_NOT_FOUND);
                    }

                    Accredited accredited = accreditedOptional.get();

                    if (accreditedInfo.getCNPJ() != null) {
                        accredited.setCNPJ(accreditedInfo.getCNPJ());
                    }

                    if (accreditedInfo.getName() != null) {
                        accredited.setName(accreditedInfo.getName());
                    }

                    if (accreditedInfo.getCategory() != null) {
                        accredited.setCategory(accreditedInfo.getCategory());
                    }

                    if (accreditedInfo.getSubcategory() != null) {
                        accredited.setSubcategory(accreditedInfo.getSubcategory());
                    }

                    if (accreditedInfo.getCouponOrigin() != null) {
                        accredited.setCouponOrigin(accreditedInfo.getCouponOrigin());
                    }

                    if (accreditedInfo.getCode() != null) {
                        accredited.setCode(accreditedInfo.getCode());
                    }

                    if (accreditedInfo.getLogo() != null) {
                        final List<Byte> logo = accreditedInfo.getLogo();
                        if (!logo.isEmpty()) {
                            byte[] bytes = new byte[logo.size()];
                            for (ListIterator<Byte> it = logo.listIterator(); it.hasNext(); ) {
                                bytes[it.nextIndex()] = it.next();
                            }
                            String imageUrl = s3Service.uploadImage(bytes);
                            accredited.setLogo(imageUrl);
                        }
                    }

                    if (accreditedInfo.getSite() != null) {
                        accredited.setSite(accreditedInfo.getSite());
                    }

                    if (accreditedInfo.getTelephone() != null) {
                        accredited.setTelephone(accreditedInfo.getTelephone());
                    }

                    if (accreditedInfo.getActive() != null) {
                        accredited.setActive(accreditedInfo.getActive());
                    }

                    if (addressInput != null) {
                        Address address = addressRepository.save(addressInput.toModel(accredited.getAddress()));
                        accredited.setAddress(address);
                    }

                    accredited = accreditedRepository.save(accredited);

                    CommonResponseWithAccreditedInformation commonResponseWithAccreditedInformation = new CommonResponseWithAccreditedInformation(StatusCodes.Success.Accredited.GOT_ACCREDITED, accredited);
                    responsePromise.complete(commonResponseWithAccreditedInformation);
                });
        return responsePromise;
    }
}
