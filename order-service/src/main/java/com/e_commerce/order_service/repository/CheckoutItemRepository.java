package com.e_commerce.order_service.repository;

import com.e_commerce.order_service.model.CheckoutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutItemRepository extends JpaRepository<CheckoutItem, Long> {
}
