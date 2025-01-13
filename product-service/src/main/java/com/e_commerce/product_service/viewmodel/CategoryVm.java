package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Category;

public record CategoryVm (Long id,
                          String name,
                          String description,
                          String slug
){
    public static CategoryVm fromModel(Category category) {
        return new CategoryVm(category.getId(), category.getName(), category.getDescription(), category.getSlug());
    }
}
