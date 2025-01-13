package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Brand;

import java.util.List;

public record BrandListGetVm(
        List<BrandVm> brandContent,
        int pageNo,
        int pageSize,
        int totalElement,
        int totalPage,
        boolean isLastPage
) {
}
