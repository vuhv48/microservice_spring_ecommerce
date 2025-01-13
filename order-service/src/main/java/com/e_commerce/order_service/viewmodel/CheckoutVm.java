package com.e_commerce.order_service.viewmodel;

import com.e_commerce.order_service.enumeration.CheckoutState;

import java.util.Set;

public record CheckoutVm(
        Long id,
        String email,
        String notes,
        CheckoutState checkoutState,
        Set<CheckoutItemVm> checkoutItemVms
) {
}
