package com.pvxdv.loggerspringaop.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metric type")
public enum OrderStatus {
    CREATED,
    CANCELLED,
    AWAITING_PAYMENT,
    PAID,
    AWAITING_SHIPMENT,
    SHIPPED,
    AWAITING_PICKUP,
    COMPLETED
}
