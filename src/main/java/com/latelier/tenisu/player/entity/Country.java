package com.latelier.tenisu.player.entity;

public record Country(
        String picture,
        String code
) {

    public Country {
        if (picture == null || picture.isBlank()) {
            throw new IllegalArgumentException(
                    "Country picture is required"
            );
        }

        if (code == null || !code.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException(
                    "Country code must contain three uppercase letters"
            );
        }
    }
}
