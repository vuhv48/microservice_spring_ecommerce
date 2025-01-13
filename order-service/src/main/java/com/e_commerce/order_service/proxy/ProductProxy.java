package com.e_commerce.order_service.proxy;

import com.e_commerce.order_service.viewmodel.ProductQuantityPutVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product", url = "http://localhost:8280")
public interface ProductProxy {
    @PutMapping("/backoffice/products/subtract-quantity")
    public ResponseEntity<Void> subtractProductQuantity(@RequestBody List<ProductQuantityPutVm> productQuantityPutVms);
}
