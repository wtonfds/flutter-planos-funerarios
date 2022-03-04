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
public class RegisterNewAccreditedImpl extends BaseResolver implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewAccreditedImpl.class);

    private final AccreditedRepository accreditedRepository;
    private final AddressRepository addressRepository;
    private final S3Service s3Service;

    public RegisterNewAccreditedImpl(
            AccreditedRepository accreditedRepository,
            AddressRepository addressRepository,
            S3Service s3Service) {
        this.accreditedRepository = accreditedRepository;
        this.addressRepository = addressRepository;
        this.s3Service = s3Service;
    }

    public CompletableFuture<CommonResponseWithAccreditedInformation> registerNewAccredited(AccreditedInfo accreditedInfo, AddressInput addressInput, DataFetchingEnvironment dataFetchingEnvironment) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithAccreditedInformation> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Accredited accredited = new Accredited();
                    accredited.setActive(accreditedInfo.getActive());
                    Address address = addressInput.toModel(new Address());
                    accredited.setAddress(address);
                    accredited.setCNPJ(accreditedInfo.getCNPJ());
                    accredited.setCategory(accreditedInfo.getCategory());
                    accredited.setSubcategory(accreditedInfo.getSubcategory());
                    if (accreditedInfo.getCode() != null) {
                        accredited.setCode(accreditedInfo.getCode());
                    }
                    accredited.setCouponOrigin(accreditedInfo.getCouponOrigin());

                    accredited.setSite(accreditedInfo.getSite());

                    Optional<Accredited> accreditedOptional = accreditedRepository.findByCNPJAndActiveTrue(accreditedInfo.getCNPJ());
                    if (accreditedOptional.isPresent()) {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Accredited.CNPJ_ALREADY_USED);
                    }

                    final List<Byte> logo = accreditedInfo.getLogo();
                    if (!logo.isEmpty()) {
                        byte[] bytes = new byte[logo.size()];
                        for (ListIterator<Byte> it = logo.listIterator(); it.hasNext(); ) {
                            bytes[it.nextIndex()] = it.next();
                        }
                        String imageUrl = s3Service.uploadImage(bytes);
                        accredited.setLogo(imageUrl);
                    }

                    accredited.setName(accreditedInfo.getName());
                    accredited.setTelephone(accreditedInfo.getTelephone());

                    accredited.setAddress(addressRepository.save(address));
                    accredited = accreditedRepository.save(accredited);

                    CommonResponseWithAccreditedInformation response = new CommonResponseWithAccreditedInformation(StatusCodes.Success.Accredited.REGISTERED_NEW_ACCREDITED, accredited);

                    responsePromise.complete(response);
                });
        return responsePromise;
    }
}
