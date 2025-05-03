package DTO;

public record ClientDTO(
        Long id,
        UserDTO userDTO,
        CoordinatesDTO coordinatesDTO
) {
}
