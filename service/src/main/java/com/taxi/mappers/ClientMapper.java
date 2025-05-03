package com.taxi.mappers;

import DTO.ClientDTO;
import io.github.frame_code.domain.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "userDTO", source = "user")
    ClientDTO toClientDTO(Client client);
}
