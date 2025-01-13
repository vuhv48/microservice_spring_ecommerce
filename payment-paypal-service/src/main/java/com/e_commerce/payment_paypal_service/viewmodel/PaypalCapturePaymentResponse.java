package com.e_commerce.payment_paypal_service.viewmodel;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PaypalCapturePaymentResponse(
        String checkoutId,
        BigDecimal amount,
        BigDecimal paymentFee,
        String gatewayTransactionId,
        String paymentMethod,
        String paymentStatus,
        String failureMessage) {
}