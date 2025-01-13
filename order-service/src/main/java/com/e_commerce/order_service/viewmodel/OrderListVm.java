package com.e_commerce.order_service.viewmodel;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderListVm(
    List<OrderBriefVm> orderBriefVms,
    int pageNo,
    int pageSize,
    int totalElement,
    int totalPage,
    boolean isLastPage
) {
}
