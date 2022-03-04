package br.com.monitoratec.farol.graphql.model.responses.product;

import br.com.monitoratec.farol.graphql.model.dtos.product.ProductDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.graphql.model.responses.common.PaginationInfo;
import br.com.monitoratec.farol.sql.model.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResponseWithProducts {

    private CommonResponse commonResponse;
    private PaginationInfo paginationInfo;
    private List<ProductDTO> productList;

    public CommonResponseWithProducts(CommonResponse commonResponse, Page<Product> productsPage) {
        this.commonResponse = commonResponse;
        this.paginationInfo = new PaginationInfo(productsPage);
        this.productList = productsPage.getContent().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public PaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public void setPaginationInfo(PaginationInfo paginationInfo) {
        this.paginationInfo = paginationInfo;
    }

    public List<ProductDTO> getAccreditedUsers() {
        return productList;
    }

    public void setAccreditedUsers(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
