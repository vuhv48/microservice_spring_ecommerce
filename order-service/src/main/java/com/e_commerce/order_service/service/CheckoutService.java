package com.e_commerce.order_service.service;

import com.e_commerce.order_service.repository.CheckoutRepository;
import com.e_commerce.order_service.viewmodel.CheckoutPostVm;
import com.e_commerce.order_service.viewmodel.CheckoutVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Repository
public class CheckoutService {


    public CheckoutVm createCheckout(CheckoutPostVm checkoutPostVm) {
        return null;
    }
}
