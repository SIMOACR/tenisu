package com.latelier.tenisu.player.dto;

import com.latelier.tenisu.player.entity.Player;

import java.util.List;

public record PlayersDocument(
        List<Player> players
) {
}
