package com.latelier.tenisu.player.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record PlayerDataInput(
        @NotNull @Positive Integer rank,
        @NotNull @PositiveOrZero Integer points,
        @NotNull @Positive Integer weight,
        @NotNull @Positive Integer height,
        @NotNull @Positive Integer age,
        @NotNull
        List<@NotNull @Min(0) @Max(1) Integer> last
) {
}
