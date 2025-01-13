package com.e_commerce.payment_paypal_service.service;

import com.braintreepayments.http.HttpResponse;
import com.e_commerce.payment_paypal_service.request.Constants;
import com.e_commerce.payment_paypal_service.viewmodel.PaypalCapturePaymentRequest;
import com.e_commerce.payment_paypal_service.viewmodel.PaypalCapturePaymentResponse;
import com.e_commerce.payment_paypal_service.viewmodel.PaypalCreatePaymentRequest;
import com.e_commerce.payment_paypal_service.viewmodel.PaypalCreatePaymentResponse;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final PayPalHttpClientInitializer payPalHttpClientInitializer;
    private final BigDecimal maxPay = BigDecimal.valueOf(1000);
    @Value("${yas.public.url}/capture")
    private String returnUrl;
    @Value("${yas.public.url}/cancel")
    private String cancelUrl;

    public PaypalCreatePaymentResponse createPayment(PaypalCreatePaymentRequest createPaymentRequest){
        PayPalHttpClient payPalHttpClient = payPalHttpClientInitializer.createPayPalHttpClient(
                createPaymentRequest.paymentSettings()
        );

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        BigDecimal totalPrice = createPaymentRequest.totalPrice();
        if(totalPrice.compareTo(maxPay) > 0){
            totalPrice = maxPay;
        }
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown().currencyCode("USD")
                .value(totalPrice.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountWithBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
        String paymentMethodReturnUrl = String.format(
                "%s?paymentMethod=%s",
                returnUrl,
                createPaymentRequest.paymentMethod()
        );
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl(paymentMethodReturnUrl)
                .cancelUrl(cancelUrl)
                .brandName(Constants.Yas.BRAND_NAME)
                .landingPage("BILLING")
                .userAction("PAY_NOW")
                .shippingPreference("NO_SHIPPING");
        orderRequest.applicationContext(applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();
            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();

            return new PaypalCreatePaymentResponse("success", order.id(), redirectUrl);
        } catch (IOException e) {
            return new PaypalCreatePaymentResponse("Error" + e.getMessage(), null, null);
        }
    }

    public PaypalCapturePaymentResponse capturePayment(PaypalCapturePaymentRequest capturePaymentRequest) {
        PayPalHttpClient payPalHttpClient = payPalHttpClientInitializer.createPayPalHttpClient(capturePaymentRequest.paymentSettings());
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(capturePaymentRequest.token());
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                Order order = httpResponse.result();
                Capture capture = order.purchaseUnits().getFirst().payments().captures().getFirst();

                String paypalFee = capture.sellerReceivableBreakdown().paypalFee().value();
                BigDecimal paymentFee = new BigDecimal(paypalFee);
                BigDecimal amount = new BigDecimal(capture.amount().value());

                return PaypalCapturePaymentResponse.builder()
                        .paymentFee(paymentFee)
                        .gatewayTransactionId(order.id())
                        .amount(amount)
                        .paymentStatus(order.status())
                        .paymentMethod("PAYPAL")
                        .build();
            }
        } catch (IOException e) {
            return PaypalCapturePaymentResponse.builder().failureMessage(e.getMessage()).build();
        }
        return PaypalCapturePaymentResponse.builder().failureMessage("Something Wrong!").build();
    }

}
