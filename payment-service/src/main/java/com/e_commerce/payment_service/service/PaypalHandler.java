package com.e_commerce.payment_service.service;

import com.e_commerce.payment_service.model.InitiatedPayment;
import com.e_commerce.payment_service.viewmodel.CapturePaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.CapturedPayment;
import com.e_commerce.payment_service.viewmodel.InitPaymentRequestVm;

public class PaypalHandler implements PaymentHandler {
    @Override
    public String getProviderId() {
        return "";
    }

    @Override
    public InitiatedPayment initPayment(InitPaymentRequestVm initPaymentRequestVm) {
        return null;
    }

    @Override
    public CapturedPayment capturePayment(CapturePaymentRequestVm capturePaymentRequestVm) {
        return null;
    }
}
