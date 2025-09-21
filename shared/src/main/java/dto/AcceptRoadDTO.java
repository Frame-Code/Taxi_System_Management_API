package dto;

public record AcceptRoadDTO(
        FullCoordinatesDTO coordinatesDTO,
        Long idTaxi,
        Long idPayment,
        Long idCityOrigin,
        Long idCityDestiny
) {
}
