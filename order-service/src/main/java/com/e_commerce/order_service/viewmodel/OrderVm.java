package com.e_commerce.order_service.viewmodel;

import com.e_commerce.order_service.enumeration.OrderStatus;
import com.e_commerce.order_service.enumeration.PaymentStatus;
import com.e_commerce.order_service.model.Order;
import com.e_commerce.order_service.model.OrderAddress;
import com.e_commerce.order_service.model.OrderItem;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record OrderVm(
        Long id,
        String email,
        String notes,
        String checkoutId,
        OrderAddressVm orderAddressVm,
        OrderStatus orderStatus,
        Set<OrderItemVm> orderItemVms,
        Long paymentId,
        PaymentStatus paymentStatus
) {
    public static OrderVm fromModel(Order order, Set<OrderItem> orderItems) {
        Set<OrderItemVm> orderItemVms = orderItems.stream()
                .map(item -> OrderItemVm.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .productPrice(item.getProductPrice())
                        .orderId(item.getOrder().getId())
                        .build()
                ).collect(Collectors.toSet());
        return OrderVm.builder()
                .id(order.getId())
                .email(order.getEmail())
                .notes(order.getNotes())
                .checkoutId(order.getCheckoutId())
                .orderAddressVm(OrderAddressVm.fromModel(order.getOrderAddress()))
                .orderStatus(order.getOrderStatus())
                .paymentId(order.getPaymentId())
                .paymentStatus(order.getPaymentStatus())
                .orderItemVms(orderItemVms)
                .build();
    }
}
