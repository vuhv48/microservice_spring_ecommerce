package com.e_commerce.product_service.repository;

import com.e_commerce.product_service.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepsitory extends JpaRepository<Media, Long> {

}
