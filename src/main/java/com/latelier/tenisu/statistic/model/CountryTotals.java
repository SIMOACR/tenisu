package com.latelier.tenisu.statistic.model;

public class CountryTotals {

    private long wins;
    private long matches;

    public void add(long newWins, long newMatches) {
        this.wins += newWins;
        this.matches += newMatches;
    }

    public double ratio() {
        if (this.matches == 0) return 0.0;
        return (double) wins / (double) matches;
    }
}
