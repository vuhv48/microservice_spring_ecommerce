package com.e_commerce.product_service.viewmodel;

import java.util.List;

public record CategoryListGetVm(
        List<CategoryVm> categoryList,
        int pageNo,
        int pageSize,
        int totalElement,
        int totalPage,
        boolean isLastPage
) {
}
