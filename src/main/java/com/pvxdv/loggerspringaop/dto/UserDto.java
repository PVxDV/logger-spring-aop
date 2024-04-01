package com.pvxdv.loggerspringaop.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "User Entity")
public record UserDto(@Schema(description = "user_id", example = "0")
                      Long id,
                      @Schema(description = "user_name", example = "ivan")
                      String name,
                      @Schema(description = "user_email", example = "ivan@mail.ru")
                      String email,
                      @Schema(description = "user_orders")
                      List<OrderDto> orders) {
}
