package com.latelier.tenisu.player.dto;

public record StatisticResponse(
        String countryWithHighestWinRatio,
        double averageBmi,
        double medianHeightCm
) {
}
