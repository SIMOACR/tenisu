package com.latelier.tenisu.player.repository;

import com.latelier.tenisu.player.entity.Country;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.entity.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonPlayerRepositoryTest {

    private JsonPlayerRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JsonPlayerRepository(
                new ObjectMapper()
        );
    }

    @Test
    void shouldLoadAllPlayersFromJson() {
        assertEquals(5, repository.findAll().size());
    }

    @Test
    void shouldFindPlayerById() {
        Player player = repository.findById(52L)
                .orElseThrow();

        assertEquals("Novak", player.firstname());
        assertEquals("Djokovic", player.lastname());
        assertEquals("SRB", player.country().code());
        assertEquals(2, player.data().rank());
    }

    @Test
    void shouldReturnEmptyWhenPlayerDoesNotExist() {
        assertTrue(repository.findById(999L).isEmpty());
    }

    @Test
    void shouldInsertPlayerWhenIdDoesNotExist() {
        Player player = createPlayer(200L);

        boolean inserted =
                repository.insertIfAbsent(player);

        assertTrue(inserted);
        assertEquals(
                player,
                repository.findById(200L).orElseThrow()
        );
    }

    @Test
    void shouldNotReplaceExistingPlayer() {
        Player first = createPlayer(200L);
        Player duplicate = createPlayer(200L);

        assertTrue(repository.insertIfAbsent(first));
        assertFalse(repository.insertIfAbsent(duplicate));

        assertEquals(
                first,
                repository.findById(200L).orElseThrow()
        );
    }

    private Player createPlayer(long id) {
        return new Player(
                id,
                "Carlos",
                "Alcaraz",
                "C.ALC",
                "M",
                new Country(
                        "https://example.com/spain.png",
                        "ESP"
                ),
                "https://example.com/alcaraz.png",
                new PlayerData(
                        3,
                        7000,
                        74000,
                        183,
                        21,
                        List.of(1, 1, 0, 1, 1)
                )
        );
    }
}
