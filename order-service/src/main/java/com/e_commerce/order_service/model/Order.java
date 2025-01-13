package com.e_commerce.order_service.model;

import com.e_commerce.order_service.enumeration.OrderStatus;
import com.e_commerce.order_service.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String notes;
    private String checkoutId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private OrderAddress orderAddress;

    @Column(name = "status")
    private OrderStatus orderStatus;

    private Long paymentId;

    @SuppressWarnings("unused")
    private String paymentMethodId;

    private PaymentStatus paymentStatus;
}
