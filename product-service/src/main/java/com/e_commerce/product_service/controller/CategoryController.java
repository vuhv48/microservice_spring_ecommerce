package com.e_commerce.product_service.controller;

import com.e_commerce.product_service.constants.PageableConstant;
import com.e_commerce.product_service.exception.BadRequestException;
import com.e_commerce.product_service.exception.NotFoundException;
import com.e_commerce.product_service.model.Category;
import com.e_commerce.product_service.service.CategoryService;
import com.e_commerce.product_service.viewmodel.CategoryListGetVm;
import com.e_commerce.product_service.viewmodel.CategoryPostVm;
import com.e_commerce.product_service.viewmodel.CategoryVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping({"/backoffice/categories/paging", "/storefront/categories/paging"})
    public ResponseEntity<CategoryListGetVm> getPageableCategory(
            @RequestParam(value = "pageNo", defaultValue = PageableConstant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageableConstant.DEFAULT_PAGE_SIZE) int pageSize) {
        return ResponseEntity.ok(categoryService.getPageableCategory(pageNo, pageSize));
    }

    @PostMapping("/backoffice/category")
    public ResponseEntity<CategoryVm> createCategory(@Valid @RequestBody CategoryPostVm categoryPostVm,
                                                     UriComponentsBuilder uriBuilder
    ) {
        Category category = categoryService.create(categoryPostVm);
        return ResponseEntity.created(uriBuilder.replacePath("/backoffice/category/{id}")
                .buildAndExpand(category.getId()).toUri())
                .body(CategoryVm.fromModel(category));
    }

    @PutMapping("/backoffice/category/{id}")
    public ResponseEntity<CategoryVm> updateCategory(@Valid @RequestBody CategoryPostVm categoryPostVm,
                                                     @PathVariable Long id) {
        Category category = categoryService.update(categoryPostVm, id);
        return ResponseEntity.ok(CategoryVm.fromModel(category));
    }

    @DeleteMapping("/backoffice/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
