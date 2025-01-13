package com.e_commerce.order_service.viewmodel;

import com.e_commerce.order_service.model.OrderAddress;
import lombok.Builder;

@Builder
public record OrderAddressVm (String contactName, String phone, String city) {
    public static OrderAddressVm fromModel(OrderAddress model) {
        return OrderAddressVm.builder()
                .contactName(model.getContactName())
                .phone(model.getPhone())
                .city(model.getCity())
                .build();
    }
}
