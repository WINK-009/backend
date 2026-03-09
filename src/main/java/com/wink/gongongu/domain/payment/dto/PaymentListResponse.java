package com.wink.gongongu.domain.payment.dto;

import java.util.List;

public record PaymentListResponse(
    List<PaymentListItemResponse> payments,
    boolean hasNext,
    int page,
    int size
) {
}
