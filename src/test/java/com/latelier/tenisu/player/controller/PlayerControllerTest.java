package com.latelier.tenisu.player.controller;

import com.latelier.tenisu.player.dto.CreatePlayerRequest;
import com.latelier.tenisu.player.entity.Country;
import com.latelier.tenisu.player.entity.Player;
import com.latelier.tenisu.player.entity.PlayerData;
import com.latelier.tenisu.player.mapper.PlayerMapper;
import com.latelier.tenisu.player.service.PlayerService;
import com.latelier.tenisu.shared.error.ApiExceptionHandler;
import com.latelier.tenisu.shared.error.DuplicatePlayerException;
import com.latelier.tenisu.shared.error.PlayerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
@Import(ApiExceptionHandler.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;
    @MockitoBean
    private PlayerMapper playerMapper;

    @Test
    void shouldReturnPlayers() throws Exception {
        when(playerService.getAllOrderedByRank())
                .thenReturn(
                        List.of(
                                player(17, "Rafael", 1),
                                player(52, "Novak", 2)
                        )
                );

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(17))
                .andExpect(jsonPath("$[1].id").value(52));
    }

    @Test
    void shouldReturnPlayerById() throws Exception {
        when(playerService.getPlayerById(52))
                .thenReturn(player(52, "Novak", 2));

        mockMvc.perform(get("/players/52"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(52))
                .andExpect(jsonPath("$.firstname").value("Novak"));
    }

    @Test
    void shouldReturnNotFoundForUnknownPlayer()
            throws Exception {

        when(playerService.getPlayerById(999))
                .thenThrow(new PlayerNotFoundException(999));

        mockMvc.perform(get("/players/999"))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.detail")
                                .value("Player with ID 999 was not found")
                );
    }

    @Test
    void shouldCreatePlayer() throws Exception {
        when(playerService.add(any(Player.class)))
                .thenReturn(player(200, "Carlos", 3));
        when(playerMapper.toPlayer(any(CreatePlayerRequest.class)))
                .thenReturn(player(200, "Carlos", 3));

        mockMvc.perform(
                        post("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validRequest())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(200));
    }

    @Test
    void shouldReturnConflictForDuplicatePlayer()
            throws Exception {

        when(playerService.add(any(Player.class)))
                .thenThrow(new DuplicatePlayerException(200));
        when(playerMapper.toPlayer(any(CreatePlayerRequest.class)))
                .thenReturn(player(200, "Carlos", 3));

        mockMvc.perform(
                        post("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validRequest())
                )
                .andExpect(status().isConflict());
    }

    @Test
    void shouldRejectInvalidRequest() throws Exception {
        String request = """
            {
              "id": -1,
              "firstname": "",
              "lastname": "Alcaraz",
              "shortname": "C.ALC",
              "sex": "X",
              "country": {
                "picture": "",
                "code": "spain"
              },
              "picture": "",
              "data": {
                "rank": 0,
                "points": -1,
                "weight": 0,
                "height": 0,
                "age": 0,
                "last": [1, 5]
              }
            }
            """;

        mockMvc.perform(
                        post("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                )
                .andExpect(status().isBadRequest());

        verify(playerService, never())
                .add(any(Player.class));
    }

    private String validRequest() {
        return """
            {
              "id": 200,
              "firstname": "Carlos",
              "lastname": "Alcaraz",
              "shortname": "C.ALC",
              "sex": "M",
              "country": {
                "picture": "https://example.com/spain.png",
                "code": "ESP"
              },
              "picture": "https://example.com/alcaraz.png",
              "data": {
                "rank": 3,
                "points": 7000,
                "weight": 74000,
                "height": 183,
                "age": 21,
                "last": [1, 1, 0, 1, 1]
              }
            }
            """;
    }

    private Player player(
            long id,
            String firstname,
            int rank
    ) {
        return new Player(
                id,
                firstname,
                "Lastname",
                "S.NAM",
                "M",
                new Country(
                        "https://example.com/country.png",
                        "ESP"
                ),
                "https://example.com/player.png",
                new PlayerData(
                        rank,
                        1000,
                        80000,
                        180,
                        25,
                        List.of(1, 0)
                )
        );
    }
}
