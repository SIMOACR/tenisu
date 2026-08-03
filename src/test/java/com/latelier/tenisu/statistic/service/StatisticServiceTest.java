package com.latelier.tenisu.statistic.service;

import com.latelier.tenisu.player.dto.StatisticResponse;
import com.latelier.tenisu.player.entity.Country;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.entity.PlayerData;
import com.latelier.tenisu.player.service.PlayerService;
import com.latelier.tenisu.shared.error.StatisticsUnavailableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private StatisticService statisticService;

    @Test
    void shouldCalculateExpectedStatistics() {
        when(playerService.getAllOrderedByRank())
                .thenReturn(players());

        StatisticResponse result =
                statisticService.calculate();

        assertEquals(
                "SRB",
                result.countryWithHighestWinRatio()
        );

        assertEquals(
                23.36,
                result.averageBmi(),
                0.001
        );

        assertEquals(
                185.0,
                result.medianHeightCm(),
                0.001
        );
    }

    @Test
    void shouldCalculateMedianForEvenNumberOfPlayers() {
        Player first = player(
                1,
                "First",
                "FRA",
                80000,
                175,
                List.of(1)
        );

        Player second = player(
                2,
                "Second",
                "ESP",
                80000,
                185,
                List.of(1)
        );

        when(playerService.getAllOrderedByRank())
                .thenReturn(List.of(first, second));

        assertEquals(
                180.0,
                statisticService.calculate().medianHeightCm(),
                0.001
        );
    }

    @Test
    void shouldRejectEmptyPlayerList() {
        when(playerService.getAllOrderedByRank())
                .thenReturn(List.of());

        assertThrows(
                StatisticsUnavailableException.class,
                statisticService::calculate
        );
    }

    private List<Player> players() {
        return List.of(
                player(
                        52,
                        "Novak",
                        "SRB",
                        80000,
                        188,
                        List.of(1, 1, 1, 1, 1)
                ),
                player(
                        95,
                        "Venus",
                        "USA",
                        74000,
                        185,
                        List.of(0, 1, 0, 0, 1)
                ),
                player(
                        65,
                        "Stan",
                        "SUI",
                        81000,
                        183,
                        List.of(1, 1, 1, 0, 1)
                ),
                player(
                        102,
                        "Serena",
                        "USA",
                        72000,
                        175,
                        List.of(0, 1, 1, 1, 0)
                ),
                player(
                        17,
                        "Rafael",
                        "ESP",
                        85000,
                        185,
                        List.of(1, 0, 0, 0, 1)
                )
        );
    }

    private Player player(
            long id,
            String firstname,
            String countryCode,
            int weight,
            int height,
            List<Integer> results
    ) {
        return new Player(
                id,
                firstname,
                "Lastname",
                "S.NAM",
                "M",
                new Country(
                        "https://example.com/country.png",
                        countryCode
                ),
                "https://example.com/player.png",
                new PlayerData(
                        1,
                        1000,
                        weight,
                        height,
                        25,
                        results
                )
        );
    }
}
