package com.e_commerce.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class InitiatedPayment {
    String status;
    String paymentId;
    String redirectUrl;
}
