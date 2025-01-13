package com.e_commerce.order_service.controller;

import com.e_commerce.order_service.contants.PageableConstant;
import com.e_commerce.order_service.service.OrderService;
import com.e_commerce.order_service.viewmodel.OrderListVm;
import com.e_commerce.order_service.viewmodel.OrderPostVm;
import com.e_commerce.order_service.viewmodel.OrderVm;
import com.e_commerce.order_service.viewmodel.PaymentOrderStatusVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/storefront/orders")
    public ResponseEntity<OrderVm> createOrder(@Valid @RequestBody OrderPostVm orderPostVm) {
        return ResponseEntity.ok(orderService.createOrder(orderPostVm));
    }

    @GetMapping({"/storefront/orders", "/backoffice/orders"})
    public ResponseEntity<OrderListVm> getOrders(
            @RequestParam(value = "pageNo", defaultValue = PageableConstant.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageableConstant.DEFAULT_PAGE_SIZE) int pageSize
    ) {
        return ResponseEntity.ok(orderService.getAllOrder(pageNo, pageSize));
    }

    @PutMapping("/storefront/orders/status")
    public ResponseEntity<PaymentOrderStatusVm> updateOrderPaymentStatus(@Valid @RequestBody PaymentOrderStatusVm paymentOrderStatusVm) {
        PaymentOrderStatusVm result = orderService.updateOrderPaymentStatus(paymentOrderStatusVm);
        return ResponseEntity.ok(result);
    }

}
