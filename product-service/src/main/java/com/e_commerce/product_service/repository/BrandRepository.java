package com.e_commerce.product_service.repository;

import com.e_commerce.product_service.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b WHERE b.name = ?1 and  (?2 is null or b.id != ?2)")
    Brand findByNameExists(String name, Long id);
}
