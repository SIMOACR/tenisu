package com.latelier.tenisu.player.mapper;

import com.latelier.tenisu.player.dto.PlayerDataInput;
import com.latelier.tenisu.player.entity.PlayerData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerDataMapper {
    PlayerData toPlayerData(PlayerDataInput playerDataInput);
}
