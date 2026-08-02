package com.latelier.tenisu.player.repository;

import com.latelier.tenisu.player.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    List<Player> findAll();

    Optional<Player> findById(Long id);

    Player save(Player player);
}
