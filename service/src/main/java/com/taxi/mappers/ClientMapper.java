package com.taxi.mappers;

import dto.ClientDTO;
import io.github.frame_code.domain.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "userDTO", source = "user")
    ClientDTO toClientDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client toClient(ClientDTO clientDTO);
}
