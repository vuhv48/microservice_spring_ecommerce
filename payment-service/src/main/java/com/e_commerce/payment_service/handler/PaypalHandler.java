package com.e_commerce.payment_service.handler;

import com.e_commerce.payment_service.model.InitiatedPayment;
import com.e_commerce.payment_service.model.enumeration.PaymentMethod;
import com.e_commerce.payment_service.service.PaymentHandler;
import com.e_commerce.payment_service.viewmodel.CapturePaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.CapturedPayment;
import com.e_commerce.payment_service.viewmodel.InitPaymentRequestVm;

public class PaypalHandler implements PaymentHandler {

    //private final PaypalService paypalService;

    @Override
    public String getProviderId() {
        return PaymentMethod.PAYPAL.name();
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
