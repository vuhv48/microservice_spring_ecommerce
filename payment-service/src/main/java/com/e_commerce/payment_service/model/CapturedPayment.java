package com.e_commerce.payment_service.model;

import java.math.BigDecimal;

import com.e_commerce.payment_service.model.enumeration.PaymentMethod;
import com.e_commerce.payment_service.model.enumeration.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class CapturedPayment {
    Long orderId;
    String checkoutId;
    BigDecimal amount;
    BigDecimal paymentFee;
    String gatewayTransactionId;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    String failureMessage;
}
