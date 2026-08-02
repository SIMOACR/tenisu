package com.latelier.tenisu.player.repository;

import com.latelier.tenisu.player.dto.PlayersDocument;
import com.latelier.tenisu.player.entity.Player;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JsonPlayerRepository implements PlayerRepository {
    private final Map<Long, Player> players = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    public JsonPlayerRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.loadPlayers();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public Optional<Player> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Player save(Player player) {
        return null;
    }

    private void loadPlayers() {
        ClassPathResource playersResource = new ClassPathResource("headtohead.json");

        try (InputStream inputStream = playersResource.getInputStream()) {
            PlayersDocument playersDocument = objectMapper.readValue(
                    inputStream, PlayersDocument.class
            );

            if (playersDocument == null) {
                throw new IllegalStateException("Could not load players document");
            }

            if (playersDocument.players() == null || playersDocument.players().isEmpty()) {
                throw new IllegalStateException("The players property is missing or empty");
            }

            for (Player player : playersDocument.players()) {
                Player existing = players.putIfAbsent(player.id(), player);

                if (existing != null) {
                    throw new IllegalStateException("Duplicate Player ID in JSON : " + player.id());
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load players document", exception);
        }
    }
}
