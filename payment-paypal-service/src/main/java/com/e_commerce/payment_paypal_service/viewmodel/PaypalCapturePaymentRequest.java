package com.e_commerce.payment_paypal_service.viewmodel;

import lombok.Builder;

@Builder
public record PaypalCapturePaymentRequest(String token, String paymentSettings)  {
}
