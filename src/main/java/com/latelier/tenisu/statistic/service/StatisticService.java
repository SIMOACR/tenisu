package com.latelier.tenisu.statistic.service;

import com.latelier.tenisu.player.dto.StatisticResponse;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.service.PlayerService;
import com.latelier.tenisu.shared.error.StatisticsUnavailableException;
import com.latelier.tenisu.statistic.model.CountryTotals;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {
    private final PlayerService playerService;

    public StatisticService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public StatisticResponse calculate() {
        List<Player> players = playerService.getAllOrderedByRank();

        return new StatisticResponse(
                findCountryWithHighestWinRatio(players),
                round(
                        calculateAverageBmi(players)
                ),
                round(
                        calculateMedianHeight(players)
                )
        );
    }

    private String findCountryWithHighestWinRatio(
            List<Player> players
    ) {
        Map<String, CountryTotals> countryStats = new HashMap<>();

        for (Player player : players) {
            CountryTotals totals = countryStats
                    .computeIfAbsent(
                            player.country().code(),
                            ignored -> new CountryTotals()
                    );
            totals.add(
                    player.data().numberOfWins(), player.data().numberOfMatches()
            );
        }

        String countryWithHighestWinRatio = null;
        double highestWinRatio = -1.0;
        for (Map.Entry<String, CountryTotals> entry : countryStats.entrySet()) {
            double ratio = entry.getValue().ratio();

            if (ratio > highestWinRatio) {
                highestWinRatio = ratio;
                countryWithHighestWinRatio = entry.getKey();
            }
        }

        if (countryWithHighestWinRatio == null) {
            throw new StatisticsUnavailableException("No match history is available");
        }

        return countryWithHighestWinRatio;
    }

    private double calculateAverageBmi(List<Player> players) {
        return players.stream()
                .mapToDouble(player -> player.data().bmi())
                .average()
                .orElseThrow(
                        () -> new StatisticsUnavailableException("Average BMI cannot be calculated")
                );
    }

    private double calculateMedianHeight(List<Player> players) {
        List<Integer> heights = players.stream()
                .map(player -> player.data().height())
                .sorted()
                .toList();

        boolean isOdd = heights.size() % 2 == 0;
        int middle = heights.size() / 2;

        if (isOdd) {
            return ( heights.get(middle) + heights.get(middle - 1) ) / 2.0;
        }

        return heights.get(middle);
    }

    private double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
