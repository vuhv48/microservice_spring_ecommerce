package com.e_commerce.payment_service.viewmodel;

import lombok.Builder;

@Builder
public record InitPaymentResponseVm(String status, String paymentId, String redirectUrl) {
}

