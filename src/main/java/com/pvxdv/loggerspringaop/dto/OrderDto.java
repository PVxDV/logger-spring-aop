package com.pvxdv.loggerspringaop.dto;


import com.pvxdv.loggerspringaop.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Order Entity")
public record OrderDto(@Schema(description = "order_id", example = "0")
                       Long id,
                       @Schema(description = "order_description", example = "some details")
                       String description,
                       @Schema(description = "available order status: CREATED,CANCELLED,AWAITING_PAYMENT,PAID," +
                               "AWAITING_SHIPMENT,SHIPPED,AWAITING_PICKUP,COMPLETED", example = "CREATED")
                       OrderStatus orderStatus,
                       @Schema(description = "user_id", example = "0")
                       Long userId) {
}
