package com.e_commerce.payment_service.controller;

import com.e_commerce.payment_service.service.PaymentService;
import com.e_commerce.payment_service.viewmodel.CapturePaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.CapturePaymentResponseVm;
import com.e_commerce.payment_service.viewmodel.InitPaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.InitPaymentResponseVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    public InitPaymentResponseVm initPayment(@Valid @RequestBody InitPaymentRequestVm initPaymentRequestVm) {
        return null;
    }

    @PostMapping(value = "/capture")
    public CapturePaymentResponseVm capturePayment(
            @Valid @RequestBody CapturePaymentRequestVm capturePaymentRequestVm) {
        return paymentService.capturePayment(capturePaymentRequestVm);
    }
}
