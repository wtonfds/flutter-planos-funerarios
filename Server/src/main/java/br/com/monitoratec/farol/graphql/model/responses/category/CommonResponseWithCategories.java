package br.com.monitoratec.farol.graphql.model.responses.category;

import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.category.Category;

import java.util.List;

public class CommonResponseWithCategories {
    private final CommonResponse commonResponse;
    private final List<Category> categories;

    public CommonResponseWithCategories(CommonResponse commonResponse, List<Category> categories) {
        this.commonResponse = commonResponse;
        this.categories = categories;
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public List<Category> getCategories() {
        return categories;
    }
    
}
