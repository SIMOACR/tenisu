package com.latelier.tenisu.player.service;

import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.repository.PlayerRepository;
import com.latelier.tenisu.shared.error.DuplicatePlayerException;
import com.latelier.tenisu.shared.error.PlayerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllOrderedByRank() {
        return playerRepository.findAll().stream()
                .sorted(
                        Comparator.comparingInt(
                                player -> player.data().rank()
                        )
                ).toList();
    }

    public Player getPlayerById(long id) {
        return playerRepository.findById(id).orElseThrow(
                () -> new PlayerNotFoundException(id)
        );
    }

    public Player add(Player player) {
        boolean inserted = playerRepository.insertIfAbsent(player);

        if (!inserted) {
            throw new DuplicatePlayerException(player.id());
        }

        return player;
    }
}
