package com.e_commerce.order_service.viewmodel;

import jakarta.validation.constraints.Positive;

public record CheckoutItemPostVm(
        Long productId,
        @Positive
        int quantity
) {
}
