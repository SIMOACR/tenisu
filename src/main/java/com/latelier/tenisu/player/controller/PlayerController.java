package com.latelier.tenisu.player.controller;

import com.latelier.tenisu.player.dto.CreatePlayerRequest;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.mapper.PlayerMapper;
import com.latelier.tenisu.player.service.PlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(
            PlayerService playerService, PlayerMapper playerMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAll() {
        return ResponseEntity.ok(
                playerService.getAllOrderedByRank()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getById(
            @PathVariable("id")
            @NotNull @Positive
            Long id
    ) {
        return ResponseEntity.ok(
                playerService.getPlayerById(id)
        );
    }

    @PostMapping
    public ResponseEntity<Player> add(
            @RequestBody @NotNull @Valid CreatePlayerRequest createPlayerRequest
    ) {
        return new ResponseEntity<>(
                playerService.add(
                        playerMapper.toPlayer(createPlayerRequest)
                ),
                HttpStatus.CREATED
        );
    }
}
