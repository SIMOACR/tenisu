package com.latelier.tenisu.statistic.controller;

import com.latelier.tenisu.player.dto.StatisticResponse;
import com.latelier.tenisu.statistic.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(
            StatisticService statisticService
    ) {
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<StatisticResponse> getStatistics() {
        return ResponseEntity.ok(
                statisticService.calculate()
        );
    }
}
