package com.latelier.tenisu.player.service;

import com.latelier.tenisu.player.entity.Country;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.entity.PlayerData;
import com.latelier.tenisu.player.repository.PlayerRepository;
import com.latelier.tenisu.shared.error.DuplicatePlayerException;
import com.latelier.tenisu.shared.error.PlayerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void shouldOrderPlayersByAscendingRank() {
        Player venus = player(95, "Venus", 52);
        Player nadal = player(17, "Rafael", 1);
        Player serena = player(102, "Serena", 10);

        when(playerRepository.findAll())
                .thenReturn(List.of(venus, nadal, serena));

        List<Player> result =
                playerService.getAllOrderedByRank();

        assertEquals(
                List.of(nadal, serena, venus),
                result
        );
    }

    @Test
    void shouldFindPlayerById() {
        Player novak = player(52, "Novak", 2);

        when(playerRepository.findById(52L))
                .thenReturn(Optional.of(novak));

        assertEquals(
                novak,
                playerService.getPlayerById(52)
        );
    }

    @Test
    void shouldRejectUnknownPlayer() {
        when(playerRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                PlayerNotFoundException.class,
                () -> playerService.getPlayerById(999)
        );
    }

    @Test
    void shouldAddPlayer() {
        Player player = player(200, "Carlos", 3);

        when(playerRepository.insertIfAbsent(player))
                .thenReturn(true);

        assertEquals(player, playerService.add(player));
    }

    @Test
    void shouldRejectDuplicatePlayer() {
        Player player = player(200, "Carlos", 3);

        when(playerRepository.insertIfAbsent(player))
                .thenReturn(false);

        assertThrows(
                DuplicatePlayerException.class,
                () -> playerService.add(player)
        );
    }

    private Player player(
            long id,
            String firstname,
            int rank
    ) {
        return new Player(
                id,
                firstname,
                "Lastname",
                "S.NAM",
                "M",
                new Country(
                        "https://example.com/country.png",
                        "ESP"
                ),
                "https://example.com/player.png",
                new PlayerData(
                        rank,
                        1000,
                        80000,
                        180,
                        25,
                        List.of(1, 0)
                )
        );
    }
}
