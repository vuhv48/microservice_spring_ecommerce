package com.e_commerce.order_service.viewmodel;

import com.e_commerce.order_service.enumeration.OrderStatus;
import com.e_commerce.order_service.enumeration.PaymentStatus;
import com.e_commerce.order_service.model.Order;
import com.e_commerce.order_service.model.OrderAddress;
import jakarta.persistence.*;

import java.util.List;

public record OrderPostVm (
        String email,
        String notes,
        String checkoutId,
        OrderAddressVm orderAddressVm,
        OrderStatus orderStatus,
        Long paymentId,
        PaymentStatus paymentStatus,
        List<OrderItemPostVm> orderItemPostVms
){
    public static OrderVm fromModel(Order order) {
        return OrderVm.builder()
                .email(order.getEmail())
                .notes(order.getNotes())
                .checkoutId(order.getCheckoutId())
                .orderAddressVm(OrderAddressVm.fromModel(order.getOrderAddress()))
                .orderStatus(order.getOrderStatus())
                .paymentId(order.getPaymentId())
                .paymentStatus(order.getPaymentStatus())
                .build();
    }
}
