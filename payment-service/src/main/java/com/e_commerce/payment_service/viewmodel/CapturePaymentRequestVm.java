package com.e_commerce.payment_service.viewmodel;

public record CapturePaymentRequestVm(
        String paymentMethod,
        String token
) {
}
