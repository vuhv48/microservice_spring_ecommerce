package com.e_commerce.order_service.viewmodel;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CheckoutPostVm(
        String email,
        String note,
        @NotEmpty(message = "Checkout Items must not be empty")
        List<CheckoutItemPostVm> checkoutItemPostVms
) {
}
