package dto.entities;

public record LocationDTO(
    Long idCity,
    String city, 
    String province,
    String road
    ) {}
