package com.vladputnikov.messaging_service.persistent.dto;

import com.vladputnikov.messaging_service.util.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record MessageDto(
    @Length(max = 50, message = "Receiver's name is too long, max length is 50 symbols")
    @NotNull(message = "Required field")
    @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_EMAIL, message = "invalid email")
    @Schema(defaultValue = "owsigmagamer@gmail.com", description = "Receiver's email name")
    String recipient,
    @Valid
    List<CarbonCopyDto> cc,
    @Length(max = 50, message = "Subject is too long, max length is 50 symbols")
    @Schema(defaultValue = "Test subject", description = "Subject")
    String subject,
    @Length(max = 250, message = "Text is too long, max length is 250 symbols")
    @Schema(defaultValue = "Some text", description = "A field to input some text")
    String text) {
}
