package com.e_commerce.product_service.service;

import com.e_commerce.product_service.exception.BadRequestException;
import com.e_commerce.product_service.exception.NotFoundException;
import com.e_commerce.product_service.model.Brand;
import com.e_commerce.product_service.repository.BrandRepository;
import com.e_commerce.product_service.viewmodel.BrandListGetVm;
import com.e_commerce.product_service.viewmodel.BrandPostVm;
import com.e_commerce.product_service.viewmodel.BrandVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Brand create(BrandPostVm brandPostVm) {
        return brandRepository.save(brandPostVm.toModel());
    }

    public BrandListGetVm getBrands(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Brand> brandPage = brandRepository.findAll(pageable);
        List<Brand> brands = brandPage.getContent();
        List<BrandVm> brandVms = new ArrayList<>();
        for (Brand brand : brands) {
            brandVms.add(BrandVm.fromModel(brand));
        }
        return new BrandListGetVm(
                brandVms,
                pageNo,
                pageSize,
                (int) brandPage.getTotalElements(),
                brandPage.getTotalPages(),
                brandPage.isLast()
        );
    }

    public Brand update(BrandPostVm brandPostVm, Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Brand not found"));
        brand.setName(brandPostVm.name());
        brand.setSlug(brandPostVm.slug());
        brand.setPublished(brandPostVm.isPublished());
        return brandRepository.save(brand);
    }

    public void delete(Long id) throws NotFoundException, BadRequestException  {
        Brand brand = brandRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Brand not found")
        );
        if(!brand.getProduct().isEmpty()){
            throw new BadRequestException("Brand cannot be deleted because it has product");
        }
        brandRepository.deleteById(id);
    }

    public BrandVm getBrand(Long id) {
        return BrandVm.fromModel(brandRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Brand not found")
        ));
    }


}
