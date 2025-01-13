package com.e_commerce.payment_service.viewmodel;

import com.e_commerce.payment_service.model.enumeration.PaymentMethod;
import com.e_commerce.payment_service.model.enumeration.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CapturePaymentResponseVm(
        Long orderId,
        String checkoutId,
        BigDecimal amount,
        String gatewayTransactionId,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String failureMessage
) {
}
