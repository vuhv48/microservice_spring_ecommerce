package com.e_commerce.payment_service.viewmodel;

import com.e_commerce.payment_service.model.enumeration.PaymentMethod;
import com.e_commerce.payment_service.model.enumeration.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class CapturedPayment {
    private Long orderId;
    private String checkoutId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String gatewayTransactionId;
    private String failureMessage;
}
