package com.vladputnikov.messaging_service.persistent.dto;

import com.vladputnikov.messaging_service.util.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CarbonCopyDto(
        @Length(max = 50, message = "Sender's name is too long, max length is 50 symbols")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_EMAIL, message = "invalid email")
        @Schema(defaultValue = "ccname@gmail.com", description = "Sender's email name")
        String ccName
) {
}
