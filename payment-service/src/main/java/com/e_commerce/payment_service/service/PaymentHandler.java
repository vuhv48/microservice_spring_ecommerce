package com.e_commerce.payment_service.service;

import com.e_commerce.payment_service.model.InitiatedPayment;
import com.e_commerce.payment_service.viewmodel.CapturePaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.CapturedPayment;
import com.e_commerce.payment_service.viewmodel.InitPaymentRequestVm;

public interface PaymentHandler {
    String getProviderId();

    InitiatedPayment initPayment(InitPaymentRequestVm initPaymentRequestVm);

    CapturedPayment capturePayment(CapturePaymentRequestVm capturePaymentRequestVm);
}
