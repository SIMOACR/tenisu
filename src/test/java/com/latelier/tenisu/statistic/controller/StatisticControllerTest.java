package com.latelier.tenisu.statistic.controller;

import com.latelier.tenisu.player.dto.StatisticResponse;
import com.latelier.tenisu.shared.error.StatisticsUnavailableException;
import com.latelier.tenisu.statistic.service.StatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StatisticService statisticService;

    @Test
    void shouldReturnStatistics() throws Exception {
        when(statisticService.calculate())
                .thenReturn(
                        new StatisticResponse(
                                "SRB",
                                23.36,
                                185.0
                        )
                );

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.countryWithHighestWinRatio")
                                .value("SRB")
                )
                .andExpect(
                        jsonPath("$.averageBmi")
                                .value(23.36)
                )
                .andExpect(
                        jsonPath("$.medianHeightCm")
                                .value(185.0)
                );
    }

    @Test
    void shouldReturnUnprocessableEntity()
            throws Exception {

        when(statisticService.calculate())
                .thenThrow(
                        new StatisticsUnavailableException(
                                "Statistics cannot be calculated without players"
                        )
                );

        mockMvc.perform(get("/statistics"))
                .andExpect(status().isUnprocessableEntity());
    }
}
