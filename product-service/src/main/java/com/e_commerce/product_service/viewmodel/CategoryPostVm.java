package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryPostVm(
        @NotBlank String name,
        String description,
        @NotBlank String slug
) {
    public static Category toModel(CategoryPostVm categoryPostVm) {
        Category category = new Category();
        category.setName(categoryPostVm.name);
        category.setDescription(categoryPostVm.description);
        category.setSlug(categoryPostVm.slug);
        return category;
    }
}
