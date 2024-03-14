package com.study.ecommerce.product;

import com.study.ecommerce.product.dto.DetailCreateRequest;
import com.study.ecommerce.product.dto.OptionCreateRequest;
import com.study.ecommerce.product.dto.ProductCreateRequest;

import java.util.List;

public class ProductFixture {
    public static ProductCreateRequest defaultProductInfoCreateRequest() {
        List<OptionCreateRequest> options = List.of(colorOptionCreateRequest(), sizeOptionCreateRequest());
        return new ProductCreateRequest("상품", 1000, 100, options);
    }

    private static OptionCreateRequest colorOptionCreateRequest() {
        List<DetailCreateRequest> details = List.of(
                new DetailCreateRequest("갈색"),
                new DetailCreateRequest("검정색"),
                new DetailCreateRequest("흰색")
        );

        return new OptionCreateRequest("색상", details);
    }

    private static OptionCreateRequest sizeOptionCreateRequest() {
        List<DetailCreateRequest> details = List.of(
                new DetailCreateRequest("S"),
                new DetailCreateRequest("M"),
                new DetailCreateRequest("L")
        );

        return new OptionCreateRequest("사이즈", details);
    }
}
