package com.e_commerce.order_service.viewmodel;

import java.math.BigDecimal;

public record OrderItemPostVm(
        Long productId,
        int quantity,
        BigDecimal productPrice
) {
}
