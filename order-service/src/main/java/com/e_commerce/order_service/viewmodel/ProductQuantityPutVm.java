package com.e_commerce.order_service.viewmodel;

import lombok.Builder;

@Builder
public record ProductQuantityPutVm(
        Long productId,
        int stockQuantity
) {
}