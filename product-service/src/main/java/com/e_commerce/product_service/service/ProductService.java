package com.e_commerce.product_service.service;

import com.e_commerce.product_service.exception.NotFoundException;
import com.e_commerce.product_service.model.*;
import com.e_commerce.product_service.repository.*;
import com.e_commerce.product_service.viewmodel.ProductListGetVm;
import com.e_commerce.product_service.viewmodel.ProductPostVm;
import com.e_commerce.product_service.viewmodel.ProductQuantityPutVm;
import com.e_commerce.product_service.viewmodel.ProductVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;



    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepsitory mediaRepository;

    public ProductVm createProduct(ProductPostVm productPostVm) {
        Product product = ProductPostVm.toModel(productPostVm);
        setProductBrand(productPostVm.brandId(), product);
        setProductCategory(productPostVm.categoryId(), product);

        List<ProductImage> productImages = setProductImage(productPostVm.productImageIds(), product);
        for (ProductImage productImage : productImages) {
            productImage.setProduct(product);
        }
        Product savedProduct = productRepository.save(product);
        return ProductVm.fromModel(savedProduct);
    }
    public void setProductBrand(Long brandId, Product product) {
        if(brandId != null) {
            Brand brand = brandRepository.findById(brandId)
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);
        }
    }

    public void setProductCategory(Long categoryId, Product product) {
        if(categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
    }

    public List<ProductImage> setProductImage(List<Long> productImageIds, Product product) {
        List<ProductImage> productImages = new ArrayList<>();
        if(CollectionUtils.isEmpty(productImageIds)) {
            return productImages;
        }
        for(Long productImageId : productImageIds) {
            Media media = mediaRepository.findById(productImageId)
                    .orElseThrow(() -> new RuntimeException("Media not found"));
            ProductImage productImage = ProductImage.builder()
                    .product(product)
                    .media(media)
                    .build();
        }
        return productImages;
    }

    public ProductListGetVm getProductsWithFilter(int pageNo, int pageSize, String productName, String brandName) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> products = productPage.getContent();
        List<ProductVm> productVms = Optional.ofNullable(products)
                .orElse(Collections.emptyList())
                .stream().map(ProductVm::fromModel)
                .collect(Collectors.toList());

        return new ProductListGetVm(
                productVms,
                productPage.getNumber(),
                productPage.getSize(),
                (int) productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public void subtractStockQuantity(List<ProductQuantityPutVm> productQuantityPutVms) {
        List<Long> productIds = productQuantityPutVms.stream()
                .map(ProductQuantityPutVm::productId)
                .collect(Collectors.toList());

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        productQuantityPutVms.forEach(productQuantityPutVm -> {
            Product product = productMap.get(productQuantityPutVm.productId());

            if (product == null) {
                throw new IllegalArgumentException("Product not found for productId: " + productQuantityPutVm.productId());
            }

            if (productQuantityPutVm.stockQuantity() > product.getStockQuantity()) {
                throw new IllegalArgumentException("Quantity in productQuantityPutVm cannot be greater than product quantity.");
            }

            product.setStockQuantity(product.getStockQuantity() - productQuantityPutVm.stockQuantity());
        });

        productRepository.saveAll(products);

    }
}
