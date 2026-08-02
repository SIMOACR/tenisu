package com.latelier.tenisu.player.mapper;

import com.latelier.tenisu.player.dto.CreatePlayerRequest;
import com.latelier.tenisu.player.entity.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CountryMapper.class, PlayerDataMapper.class})
public interface PlayerMapper {
    Player toPlayer(CreatePlayerRequest createPlayerRequest);
}
