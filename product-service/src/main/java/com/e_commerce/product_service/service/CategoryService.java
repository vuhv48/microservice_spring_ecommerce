package com.e_commerce.product_service.service;

import com.e_commerce.product_service.exception.BadRequestException;
import com.e_commerce.product_service.exception.NotFoundException;
import com.e_commerce.product_service.model.Category;
import com.e_commerce.product_service.repository.CategoryRepository;
import com.e_commerce.product_service.viewmodel.BrandListGetVm;
import com.e_commerce.product_service.viewmodel.CategoryListGetVm;
import com.e_commerce.product_service.viewmodel.CategoryPostVm;
import com.e_commerce.product_service.viewmodel.CategoryVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(CategoryPostVm categoryPostVm){
        Category category = CategoryPostVm.toModel(categoryPostVm);
        return categoryRepository.save(category);
    }

    public Category update(CategoryPostVm categoryPostVm, Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
        Category categoryToUpdate = CategoryPostVm.toModel(categoryPostVm);
        categoryToUpdate.setId(id);
        return categoryRepository.save(categoryToUpdate);
    }

    public Category delete(Long id) throws NotFoundException, BadRequestException {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not found")
        );
        if(!category.getProducts().isEmpty()){
            throw new BadRequestException("Category already exists");
        }
        categoryRepository.delete(category);
        return category;
    }

    public CategoryListGetVm getPageableCategory(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categoryList = categoryPage.getContent();
        List<CategoryVm> categoryVms = Optional.ofNullable(categoryList)
                .orElse(Collections.emptyList())
                .stream()
                .map(CategoryVm::fromModel)
                .collect(Collectors.toList());
        return new CategoryListGetVm(
                categoryVms,
                categoryPage.getNumber(),
                categoryPage.getSize(),
                (int) categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),
                categoryPage.isLast()
        );
    }
}
