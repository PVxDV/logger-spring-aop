package com.pvxdv.loggerspringaop.dto;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "User Entity")
public record CreateOrUpdateUserDto(
                      @Schema(description = "user_name", example = "ivan")
                      String name,
                      @Schema(description = "user_email", example = "ivan@mail.ru")
                      String email) {
}