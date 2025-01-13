package com.e_commerce.order_service.model;

import com.e_commerce.order_service.enumeration.CheckoutState;
import com.e_commerce.order_service.enumeration.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checkout")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String notes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CheckoutState checkoutState;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CheckoutItem> checkoutItems = new ArrayList<>();

}
