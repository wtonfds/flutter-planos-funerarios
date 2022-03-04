package br.com.monitoratec.farol.graphql.resolvers.query.category;

import br.com.monitoratec.farol.graphql.model.responses.category.CommonResponseWithCategories;
import br.com.monitoratec.farol.graphql.resolvers.base.BaseResolver;
import br.com.monitoratec.farol.sql.model.category.Category;
import br.com.monitoratec.farol.sql.repository.category.CategoryRepository;
import br.com.monitoratec.farol.utils.responses.StatusCodes;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCategoriesImpl extends BaseResolver implements GraphQLQueryResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCategoriesImpl.class);

    private final CategoryRepository categoryRepository;

    public ListCategoriesImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CommonResponseWithCategories listCategories() {
        super.logRequest(LOGGER, this);
        List<Category> categories = categoryRepository.findAll();
        return new CommonResponseWithCategories(StatusCodes.Success.Category.GOT_CATEGORIES_SUCCESSFULLY, categories);
    }

}
