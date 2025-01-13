package com.e_commerce.order_service.viewmodel;

import com.e_commerce.order_service.enumeration.OrderStatus;
import com.e_commerce.order_service.enumeration.PaymentStatus;
import com.e_commerce.order_service.model.Order;
import lombok.Builder;

@Builder
public record OrderBriefVm(
        Long id,
        String email,
        String notes,
        OrderAddressVm orderAddressVm,
        OrderStatus orderStatus,
        PaymentStatus paymentStatus
) {
    public static OrderBriefVm fromModel(Order order) {
        return OrderBriefVm.builder()
                .id(order.getId())
                .email(order.getEmail())
                .notes(order.getNotes())
                .orderAddressVm(OrderAddressVm.fromModel(order.getOrderAddress()))
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }
}
