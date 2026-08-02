package com.latelier.tenisu.player.entity;

import com.latelier.tenisu.shared.util.StringUtil;

import java.util.Set;

public record Player(
        long id,
        String firstname,
        String lastname,
        String shortname,
        String sex,
        Country country,
        String picture,
        PlayerData data
) {

    private static final Set<String> ACCEPTED_SEX_VALUES = Set.of("M", "F");

    public Player {
        if (id <= 0) {
            throw new IllegalArgumentException("Player ID must be positive");
        }

        if (StringUtil.isNullOrBlank(firstname)) {
            throw new IllegalArgumentException("Player Firstname is required");
        }

        if (StringUtil.isNullOrBlank(lastname)) {
            throw new IllegalArgumentException("Player Lastname is required");
        }

        if (StringUtil.isNullOrBlank(shortname)) {
            throw new IllegalArgumentException("Player Shortname is required");
        }

        if (!ACCEPTED_SEX_VALUES.contains(sex)) {
            throw new IllegalArgumentException("Player sex must be M or F");
        }

        if (country == null) {
            throw new IllegalArgumentException("Player country is required");
        }

        if (StringUtil.isNullOrBlank(picture)) {
            throw new IllegalArgumentException("Player picture is required");
        }

        if (data == null) {
            throw new IllegalArgumentException("Player data is required");
        }
    }
}
