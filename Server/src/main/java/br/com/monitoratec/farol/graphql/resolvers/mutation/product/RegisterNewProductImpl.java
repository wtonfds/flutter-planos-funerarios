package br.com.monitoratec.farol.graphql.resolvers.mutation.product;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.exceptions.ErrorOnProcessingRequestException;
import br.com.monitoratec.farol.graphql.model.input.product.ProductInput;
import br.com.monitoratec.farol.graphql.model.responses.product.CommonResponseForProduct;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.accredited.Accredited;
import br.com.monitoratec.farol.sql.model.product.Product;
import br.com.monitoratec.farol.sql.model.user.FarolUser;
import br.com.monitoratec.farol.sql.repository.accredited.AccreditedRepository.AccreditedRepository;
import br.com.monitoratec.farol.sql.repository.product.ProductRepository;
import br.com.monitoratec.farol.sql.repository.user.FarolUserRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RegisterNewProductImpl extends BaseResolver implements GraphQLMutationResolver {

    private final AccreditedRepository accreditedRepository;
    private final FarolUserRepository farolUserRepository;
    private final ProductRepository productRepository;

    public RegisterNewProductImpl(AccreditedRepository accreditedRepository, FarolUserRepository farolUserRepository, ProductRepository productRepository) {
        this.accreditedRepository = accreditedRepository;
        this.farolUserRepository = farolUserRepository;
        this.productRepository = productRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterNewProductImpl.class);

    public CompletableFuture<CommonResponseForProduct> registerNewProduct(ProductInput productInput, DataFetchingEnvironment dataFetchingEnvironment) {

        CompletableFuture<CommonResponseForProduct> responsePromise = TimedOutHandledPromiser.genPromise();
        super.logRequest(LOGGER, this);
        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {
                    Product product = new Product();
                    product.setName(productInput.getName());
                    product.setDiscount(productInput.getDiscount());

                    if (productInput.getDueDate() != null) {
                        product.setDueDate(productInput.getDueDate().getDate());
                    }
                    Optional<FarolUser> farolUser = farolUserRepository.findByIdAndActiveTrue(cachedTrustedToken.userCachedInfo.entityID);
                    if (farolUser.isPresent()) {
                        product.setCreatedBy(farolUser.get());
                    } else {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Product.FAROL_USER_NOT_FOUND);
                    }

                    Optional<Accredited> accredited = accreditedRepository.findById(productInput.getAccreditedId());
                    if (accredited.isPresent()) {
                        product.setAccredited(accredited.get());
                    } else {
                        throw new ErrorOnProcessingRequestException(StatusCodes.Error.Product.ACCREDITED_NOT_FOUND);
                    }

                    productRepository.save(product);
                    CommonResponseForProduct commonResponseForProduct = new CommonResponseForProduct(StatusCodes.Success.Products.REGISTERED_NEW_PRODUCT, product);
                    responsePromise.complete(commonResponseForProduct);
                });
        return responsePromise;
    }
}
