package com.latelier.tenisu.player.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreatePlayerRequest(
        @NotNull @Positive Long id,
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank String shortname,
        @NotBlank
        @Pattern(regexp = "M|F", message = "Sex must be M or F")
        String sex,
        @NotNull @Valid CountryInput country,
        @NotBlank String picture,
        @NotNull @Valid PlayerDataInput data
) {
}
