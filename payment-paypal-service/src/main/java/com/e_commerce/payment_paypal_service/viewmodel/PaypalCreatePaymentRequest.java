package com.e_commerce.payment_paypal_service.viewmodel;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PaypalCreatePaymentRequest(
        BigDecimal totalPrice,
        String orderId,
        String paymentMethod,
        String paymentSettings
) {
}
