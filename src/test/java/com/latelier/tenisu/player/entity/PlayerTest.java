package com.latelier.tenisu.player.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void shouldCreateValidPlayer() {
        Player player = validPlayer();

        assertEquals(52, player.id());
        assertEquals("Novak", player.firstname());
        assertEquals("Djokovic", player.lastname());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -1})
    void shouldRejectNonPositiveId(long id) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        id,
                        "Novak",
                        "Djokovic",
                        "N.DJO",
                        "M",
                        validCountry(),
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldRejectInvalidFirstname(String firstname) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        firstname,
                        "Djokovic",
                        "N.DJO",
                        "M",
                        validCountry(),
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldRejectInvalidLastname(String lastname) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        lastname,
                        "N.DJO",
                        "M",
                        validCountry(),
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldRejectInvalidShortname(String shortname) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        "Djokovic",
                        shortname,
                        "M",
                        validCountry(),
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "X", "m", "f"})
    void shouldRejectInvalidSex(String sex) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        "Djokovic",
                        "N.DJO",
                        sex,
                        validCountry(),
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @Test
    void shouldRejectNullCountry() {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        "Djokovic",
                        "N.DJO",
                        "M",
                        null,
                        "https://example.com/player.png",
                        validData()
                )
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  "})
    void shouldRejectInvalidPicture(String picture) {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        "Djokovic",
                        "N.DJO",
                        "M",
                        validCountry(),
                        picture,
                        validData()
                )
        );
    }

    @Test
    void shouldRejectNullData() {
        assertThrows(
                IllegalArgumentException.class,
                () -> player(
                        52,
                        "Novak",
                        "Djokovic",
                        "N.DJO",
                        "M",
                        validCountry(),
                        "https://example.com/player.png",
                        null
                )
        );
    }

    private Player validPlayer() {
        return player(
                52,
                "Novak",
                "Djokovic",
                "N.DJO",
                "M",
                validCountry(),
                "https://example.com/player.png",
                validData()
        );
    }

    private Player player(
            long id,
            String firstname,
            String lastname,
            String shortname,
            String sex,
            Country country,
            String picture,
            PlayerData data
    ) {
        return new Player(
                id,
                firstname,
                lastname,
                shortname,
                sex,
                country,
                picture,
                data
        );
    }

    private Country validCountry() {
        return new Country(
                "https://example.com/serbia.png",
                "SRB"
        );
    }

    private PlayerData validData() {
        return new PlayerData(
                2,
                2542,
                80000,
                188,
                31,
                List.of(1, 1, 1, 1, 1)
        );
    }
}
