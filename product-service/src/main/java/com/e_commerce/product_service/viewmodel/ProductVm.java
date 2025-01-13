package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Brand;
import com.e_commerce.product_service.model.Category;
import com.e_commerce.product_service.model.Product;
import com.e_commerce.product_service.model.ProductImage;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public record ProductVm (
        String name,
        String description,
        String sku,
        String slug,
        Double price,
        Long stockQuantity,
        boolean isPublished

) {
    public static ProductVm fromModel(Product product) {
        return new ProductVm(
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getSlug(),
                product.getPrice(),
                product.getStockQuantity(),
                product.isPublished()
        );
    }
}
