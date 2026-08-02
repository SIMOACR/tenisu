package com.latelier.tenisu.player.mapper;

import com.latelier.tenisu.player.dto.CountryInput;
import com.latelier.tenisu.player.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    Country toCountry(CountryInput countryInput);
}
