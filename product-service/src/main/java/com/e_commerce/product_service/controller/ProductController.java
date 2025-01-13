package com.e_commerce.product_service.controller;

import com.e_commerce.product_service.constants.PageableConstant;
import com.e_commerce.product_service.service.ProductService;
import com.e_commerce.product_service.viewmodel.ProductListGetVm;
import com.e_commerce.product_service.viewmodel.ProductPostVm;
import com.e_commerce.product_service.viewmodel.ProductQuantityPutVm;
import com.e_commerce.product_service.viewmodel.ProductVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/backoffice/products")
    public ResponseEntity<ProductVm> createProduct(@Valid @RequestBody ProductPostVm productPostVm) {
        ProductVm productVm = productService.createProduct(productPostVm);
        return new ResponseEntity<>(productVm, HttpStatus.CREATED);
    }

    @GetMapping("/backoffice/products")
    public ResponseEntity<ProductListGetVm> getProducts(
            @RequestParam(value = "pageNo", defaultValue = PageableConstant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageableConstant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "product-name", defaultValue = "") String productName,
            @RequestParam(value = "brand-name", defaultValue = "") String brandName
    ) {
        ProductListGetVm productListGetVm =
                productService.getProductsWithFilter(pageNo, pageSize, productName, brandName);
        return ResponseEntity.ok(productListGetVm);
    }

    @PutMapping("/backoffice/products/subtract-quantity")
    public ResponseEntity<Void> subtractProductQuantity(@Valid @RequestBody List<ProductQuantityPutVm> productQuantityPutVms) {
        productService.subtractStockQuantity(productQuantityPutVms);
        return ResponseEntity.noContent().build();
    }
}
