package com.e_commerce.order_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "checkout_item")
@Data
@NoArgsConstructor @AllArgsConstructor
public class CheckoutItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private int quantity;

    @Column(name = "price")
    private BigDecimal productPrice;

    @ManyToOne
    @JoinColumn(name = "checkout_id", updatable = false, nullable = false)
    private Checkout checkout;

}
