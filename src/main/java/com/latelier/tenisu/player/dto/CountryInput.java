package com.latelier.tenisu.player.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CountryInput(
        @NotNull @NotBlank String picture,
        @NotNull @NotBlank
        @Pattern(
                regexp = "[A-Z]{3}",
                message = "country code must contain three uppercase letters"
        )
        String code
) {
}
