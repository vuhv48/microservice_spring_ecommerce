package com.e_commerce.payment_service.service;


import com.e_commerce.payment_service.model.Payment;
import com.e_commerce.payment_service.proxy.OrderProxy;
import com.e_commerce.payment_service.repository.PaymentRepository;
import com.e_commerce.payment_service.viewmodel.CapturePaymentRequestVm;
import com.e_commerce.payment_service.viewmodel.CapturePaymentResponseVm;
import com.e_commerce.payment_service.viewmodel.CapturedPayment;
import com.e_commerce.payment_service.viewmodel.PaymentOrderStatusVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final Map<String, PaymentHandler> providers = new HashMap<>();
    private final List<PaymentHandler> paymentHandlers;
    private final OrderProxy orderProxy;

    public CapturePaymentResponseVm capturePayment(CapturePaymentRequestVm capturePaymentRequestVm) {
        PaymentHandler paymentHandler = getPaymentHandler(capturePaymentRequestVm.paymentMethod());
        CapturedPayment capturedPayment = paymentHandler.capturePayment(capturePaymentRequestVm);

        Payment payment = createPayment(capturedPayment);

        PaymentOrderStatusVm paymentOrderStatusVm = PaymentOrderStatusVm.builder()
                .orderId(payment.getOrderId())
                .paymentStatus(payment.getPaymentStatus().name())
                .paymentId(payment.getId())
                .build();
        orderProxy.updateOrderPaymentStatus(paymentOrderStatusVm);

        return CapturePaymentResponseVm.builder()
                .orderId(capturedPayment.getOrderId())
                .checkoutId(capturedPayment.getCheckoutId())
                .amount(capturedPayment.getAmount())
                .gatewayTransactionId(capturedPayment.getGatewayTransactionId())
                .paymentMethod(capturedPayment.getPaymentMethod())
                .paymentStatus(capturedPayment.getPaymentStatus())
                .failureMessage(capturedPayment.getFailureMessage())
                .build();
    }
    private PaymentHandler getPaymentHandler(String providerName) {
        PaymentHandler handler = providers.get(providerName);
        if (handler == null) {
            throw new IllegalArgumentException("No payment handler found for provider: " + providerName);
        }
        return handler;
    }

    private Payment createPayment(CapturedPayment capturedPayment) {
        Payment payment = Payment.builder()
                .orderId(capturedPayment.getOrderId())
                .paymentMethod(capturedPayment.getPaymentMethod())
                .amount(capturedPayment.getAmount())
                .paymentStatus(capturedPayment.getPaymentStatus())
                .failureMessage(capturedPayment.getFailureMessage())
                .gatewayTransactionId(capturedPayment.getGatewayTransactionId())
                .build();
        return paymentRepository.save(payment);
    }
}
