package com.e_commerce.product_service.controller;

import com.e_commerce.product_service.constants.PageableConstant;
import com.e_commerce.product_service.exception.BadRequestException;
import com.e_commerce.product_service.exception.NotFoundException;
import com.e_commerce.product_service.model.Brand;
import com.e_commerce.product_service.service.BrandService;
import com.e_commerce.product_service.viewmodel.BrandListGetVm;
import com.e_commerce.product_service.viewmodel.BrandPostVm;
import com.e_commerce.product_service.viewmodel.BrandVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/backoffice/hello")
    public  ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping({"/backoffice/brands/paging", "/storefront/brands/paging"})
    public ResponseEntity<BrandListGetVm> getPageableBrands(
            @RequestParam(value = "pageNo", defaultValue = PageableConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageableConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize
            ) {
        return ResponseEntity.ok(brandService.getBrands(pageNo, pageSize));

    }

    public ResponseEntity<BrandVm> getBrandById(@PathVariable(value = "brandId", required = true) Long brandId) {
        return ResponseEntity.ok(brandService.getBrand(brandId));
    }

    @PostMapping("/backoffice/brands")
    public ResponseEntity<BrandVm> createBrand(@Valid @RequestBody BrandPostVm brandPostVm,
                                               UriComponentsBuilder uriBuilder) {
        Brand brand = brandService.create(brandPostVm);
        return ResponseEntity.created(uriBuilder.replacePath("/brands/{id}")
                .buildAndExpand(brand.getId()).toUri()).body(BrandVm.fromModel(brand));
    }

    @PutMapping("/backoffice/brands/{id}")
    public ResponseEntity<BrandVm> updateBrand(@Valid @RequestBody BrandPostVm brandPostVm, @PathVariable Long id) {
        Brand brand = brandService.update(brandPostVm, id);
        return ResponseEntity.ok(BrandVm.fromModel(brand));
    }

    @DeleteMapping("/backoffice/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        try{
            brandService.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }




}