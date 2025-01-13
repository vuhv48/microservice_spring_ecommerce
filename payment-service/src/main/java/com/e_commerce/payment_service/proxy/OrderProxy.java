package com.e_commerce.payment_service.proxy;

import com.e_commerce.payment_service.viewmodel.PaymentOrderStatusVm;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orders", url = "http://localhost:8281")
public interface OrderProxy {

    @PutMapping("/storefront/orders/status")
    public ResponseEntity<PaymentOrderStatusVm> updateOrderPaymentStatus(@Valid @RequestBody PaymentOrderStatusVm paymentOrderStatusVm);
}
