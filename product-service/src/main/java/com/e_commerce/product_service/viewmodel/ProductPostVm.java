package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductPostVm(
        @NotBlank String name,
        String description,
        String sku,
        String slug,
        Double price,
        Long stockQuantity,
        boolean isPublished,
        Long thumbnailMediaId,
        Long brandId,
        Long categoryId,
        List<Long> productImageIds
) {
    public static Product toModel(ProductPostVm productPostVm){
        Product product = new Product();
        product.setName(productPostVm.name);
        product.setDescription(productPostVm.description);
        product.setSlug(productPostVm.slug);
        product.setPrice(productPostVm.price);
        product.setStockQuantity(productPostVm.stockQuantity);
        product.setPublished(productPostVm.isPublished);
        product.setThumbnailMediaId(productPostVm.thumbnailMediaId);
        return product;
    }

}
