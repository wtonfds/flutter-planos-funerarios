package br.com.monitoratec.farol.graphql.model.responses.product;

import br.com.monitoratec.farol.graphql.model.dtos.product.ProductDTO;
import br.com.monitoratec.farol.graphql.model.responses.common.CommonResponse;
import br.com.monitoratec.farol.sql.model.product.Product;

public class CommonResponseForProduct {
    private CommonResponse commonResponse;
    private ProductDTO productDTO;

    public CommonResponseForProduct(CommonResponse commonResponse, Product product) {
        this.commonResponse = commonResponse;
        this.productDTO = new ProductDTO(product);
    }

    public CommonResponse getCommonResponse() {
        return commonResponse;
    }

    public void setCommonResponse(CommonResponse commonResponse) {
        this.commonResponse = commonResponse;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }
}
