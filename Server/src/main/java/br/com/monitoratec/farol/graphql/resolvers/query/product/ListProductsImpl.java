package br.com.monitoratec.farol.graphql.resolvers.query.product;

import br.com.monitoratec.farol.auth.model.AccessingEntity;
import br.com.monitoratec.farol.graphql.model.input.common.Paginate;
import br.com.monitoratec.farol.graphql.model.responses.product.CommonResponseWithProducts;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.graphql.utils.TimedOutHandledPromiser;
import br.com.monitoratec.farol.sql.model.product.Product;
import br.com.monitoratec.farol.sql.repository.product.ProductRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@Component
public class ListProductsImpl extends BaseResolver implements GraphQLQueryResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListProductsImpl.class);

    private final ProductRepository productRepository;

    public ListProductsImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public CompletableFuture<CommonResponseWithProducts> listProductsByAccreditedId(
            Long accreditedId, Paginate paginate, DataFetchingEnvironment dataFetchingEnvironment
    ) {
        super.logRequest(LOGGER, this);

        CompletableFuture<CommonResponseWithProducts> responsePromise = TimedOutHandledPromiser.genPromise();

        super.validateUserPromise(dataFetchingEnvironment).bindToResponsePromise(responsePromise).anyOf(Arrays.asList(AccessingEntity.values()))
                .thenAccept(cachedTrustedToken -> {

                    Pageable pageable = Paginate.getPageable(paginate, Sort.by(Sort.Direction.DESC, Product.Fields.ID));

                    Page<Product> productsPage = productRepository.findProductByAccreditedId(accreditedId, pageable);
                    responsePromise.complete(new CommonResponseWithProducts(StatusCodes.Success.Products.FOUND_PRODUCTS, productsPage));
                });
        return responsePromise;
    }
}
