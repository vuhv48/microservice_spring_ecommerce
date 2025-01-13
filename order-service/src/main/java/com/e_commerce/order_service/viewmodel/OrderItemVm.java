package com.e_commerce.order_service.viewmodel;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemVm (
        Long id,
        Long productId,
        int quantity,
        BigDecimal productPrice,
        Long orderId
){
}
