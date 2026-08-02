package com.latelier.tenisu.player.entity;

import java.util.List;

public record PlayerData(
        int rank,
        int points,
        int weight,
        int height,
        int age,
        List<Integer> last
) {

    public PlayerData {
        if (rank <= 0) {
            throw new IllegalArgumentException(
                    "Player rank must be positive"
            );
        }

        if (points < 0) {
            throw new IllegalArgumentException(
                    "Player points cannot be negative"
            );
        }

        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "Player weight must be positive"
            );
        }

        if (height <= 0) {
            throw new IllegalArgumentException(
                    "Player height must be positive"
            );
        }

        if (age <= 0) {
            throw new IllegalArgumentException(
                    "Player age must be positive"
            );
        }

        if (last == null) {
            throw new IllegalArgumentException(
                    "Player match history is required"
            );
        }

        boolean invalidResult = last.stream()
                .anyMatch(result ->
                        result == null || (result != 0 && result != 1)
                );

        if (invalidResult) {
            throw new IllegalArgumentException(
                    "Match history must contain only 0 or 1"
            );
        }

        last = List.copyOf(last);
    }

    public long numberOfWins() {
        return last.stream()
                .filter(result -> result == 1)
                .count();
    }

    public int numberOfMatches() {
        return last.size();
    }

    public double bmi() {
        double weightInKilograms = weight / 1000.0;
        double heightInMeters = height / 100.0;

        return weightInKilograms
                / (heightInMeters * heightInMeters);
    }
}
