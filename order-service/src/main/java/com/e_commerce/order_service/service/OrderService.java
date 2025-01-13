package com.e_commerce.order_service.service;

import com.e_commerce.order_service.enumeration.OrderStatus;
import com.e_commerce.order_service.enumeration.PaymentStatus;
import com.e_commerce.order_service.model.Order;
import com.e_commerce.order_service.model.OrderAddress;
import com.e_commerce.order_service.model.OrderItem;
import com.e_commerce.order_service.proxy.ProductProxy;
import com.e_commerce.order_service.repository.OrderAddressRepository;
import com.e_commerce.order_service.repository.OrderItemRepository;
import com.e_commerce.order_service.repository.OrderRepository;
import com.e_commerce.order_service.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductProxy productProxy;
    private final OrderAddressRepository orderAddressRepository;

    public OrderVm createOrder(OrderPostVm orderPostVm){
        OrderAddressVm orderAddressVm = orderPostVm.orderAddressVm();
        OrderAddress orderAddress = OrderAddress.builder()
                .contactName(orderAddressVm.contactName())
                .city(orderAddressVm.city())
                .phone(orderAddressVm.phone())
                .build();
        Order order = Order.builder()
                .email(orderPostVm.email())
                .notes(orderPostVm.notes())
                .orderAddress(orderAddress)
                .orderStatus(orderPostVm.orderStatus())
                .paymentId(orderPostVm.paymentId())
                .paymentStatus(orderPostVm.paymentStatus())
                .checkoutId(orderPostVm.checkoutId())
                .build();
        orderAddressRepository.save(orderAddress);
        orderRepository.save(order);
        Set<OrderItem> orderItems = orderPostVm.orderItemPostVms()
                .stream().map((item) -> OrderItem
                        .builder()
                        .productId(item.productId())
                        .quantity(item.quantity())
                        .productPrice(item.productPrice())
                        .order(order)
                        .build()
                ).collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        OrderVm orderVm = OrderVm.fromModel(order, orderItems);
        List<ProductQuantityPutVm> productQuantityPutVmList = orderVm.orderItemVms().stream()
                        .map(orderItem -> ProductQuantityPutVm.builder()
                                .productId(orderItem.productId())
                                .stockQuantity(orderItem.quantity())
                                .build())
                                .collect(Collectors.toList());
       productProxy.subtractProductQuantity(productQuantityPutVmList);
       return orderVm;
    }

    public OrderListVm getAllOrder(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        List<Order> orders = orderPage.getContent();
        List<OrderBriefVm> orderVmList = orders.stream()
                .map(OrderBriefVm::fromModel)
                .collect(Collectors.toList());
        return new OrderListVm(
                orderVmList,
                orderPage.getNumber(),
                orderPage.getSize(),
                (int) orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.isLast()
        );
    }

    public PaymentOrderStatusVm updateOrderPaymentStatus(PaymentOrderStatusVm paymentOrderStatusVm){
        var order = orderRepository.findById(paymentOrderStatusVm.orderId())
                        .orElseThrow(() -> new RuntimeException("Order not found for id: " + paymentOrderStatusVm.orderId()));
        order.setOrderStatus(OrderStatus.valueOf(paymentOrderStatusVm.orderStatus()));
        order.setPaymentId(paymentOrderStatusVm.paymentId());
        order.setPaymentStatus(order.getPaymentStatus());
        if(PaymentStatus.COMPLETED.name().equals(paymentOrderStatusVm.paymentStatus())){
            order.setOrderStatus(OrderStatus.PAID);
        }
        Order result = orderRepository.save(order);
        return  PaymentOrderStatusVm.builder()
                .orderId(result.getId())
                .orderStatus(String.valueOf(result.getOrderStatus()))
                .paymentStatus(String.valueOf(result.getPaymentStatus()))
                .build();
    }
}
