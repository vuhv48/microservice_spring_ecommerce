package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Brand;

public record BrandPostVm(String name, String slug, boolean isPublished) {
    public Brand toModel() {
        Brand brand = new Brand();
        brand.setName(name);
        brand.setSlug(slug);
        brand.setPublished(isPublished);
        return brand;
    }
}
