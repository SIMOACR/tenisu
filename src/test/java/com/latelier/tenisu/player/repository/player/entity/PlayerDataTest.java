package com.latelier.tenisu.player.repository.player.entity;

import com.latelier.tenisu.player.entity.PlayerData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerDataTest {

    @Test
    void shouldCreateValidPlayerData() {
        PlayerData data = validData();

        assertEquals(1, data.rank());
        assertEquals(1000, data.points());
        assertEquals(80000, data.weight());
        assertEquals(180, data.height());
        assertEquals(25, data.age());
        assertEquals(List.of(1, 0, 1), data.last());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldRejectNonPositiveRank(int rank) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        rank,
                        1000,
                        80000,
                        180,
                        25,
                        List.of(1)
                )
        );
    }

    @Test
    void shouldRejectNegativePoints() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        -1,
                        80000,
                        180,
                        25,
                        List.of(1)
                )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldRejectNonPositiveWeight(int weight) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        1000,
                        weight,
                        180,
                        25,
                        List.of(1)
                )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldRejectNonPositiveHeight(int height) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        1000,
                        80000,
                        height,
                        25,
                        List.of(1)
                )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void shouldRejectNonPositiveAge(int age) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        1000,
                        80000,
                        180,
                        age,
                        List.of(1)
                )
        );
    }

    @Test
    void shouldRejectNullMatchHistory() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        1000,
                        80000,
                        180,
                        25,
                        null
                )
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 2, 10})
    void shouldRejectInvalidMatchResult(int result) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayerData(
                        1,
                        1000,
                        80000,
                        180,
                        25,
                        List.of(1, result)
                )
        );
    }

    @Test
    void shouldDefensivelyCopyMatchHistory() {
        List<Integer> original =
                new ArrayList<>(List.of(1, 0));

        PlayerData data = new PlayerData(
                1,
                1000,
                80000,
                180,
                25,
                original
        );

        original.add(1);

        assertEquals(List.of(1, 0), data.last());

        assertThrows(
                UnsupportedOperationException.class,
                () -> data.last().add(1)
        );
    }

    @Test
    void shouldCountWins() {
        PlayerData data = validData();

        assertEquals(2, data.numberOfWins());
    }

    @Test
    void shouldCountMatches() {
        PlayerData data = validData();

        assertEquals(3, data.numberOfMatches());
    }

    @Test
    void shouldCalculateBmi() {
        PlayerData data = validData();

        assertEquals(
                24.6914,
                data.bmi(),
                0.0001
        );
    }

    private PlayerData validData() {
        return new PlayerData(
                1,
                1000,
                80000,
                180,
                25,
                List.of(1, 0, 1)
        );
    }
}
