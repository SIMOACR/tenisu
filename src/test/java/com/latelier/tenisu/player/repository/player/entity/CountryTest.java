package com.latelier.tenisu.player.repository.player.entity;

import com.latelier.tenisu.player.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryTest {

    @Test
    void shouldCreateValidCountry() {
        Country country = new Country(
                "https://example.com/spain.png",
                "ESP"
        );

        assertEquals("ESP", country.code());
        assertEquals(
                "https://example.com/spain.png",
                country.picture()
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldRejectInvalidPicture(String picture) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Country(picture, "ESP")
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "ES",
            "ESPA",
            "esp",
            "E1P"
    })
    void shouldRejectInvalidCountryCode(String code) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Country(
                        "https://example.com/spain.png",
                        code
                )
        );
    }
}
