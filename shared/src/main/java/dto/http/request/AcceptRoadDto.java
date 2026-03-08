package dto.http.request;

public record AcceptRoadDto(
        FullCoordinatesDTO coordinatesDTO,
        Long idTaxi,
        Long idPayment,
        Long idCityOrigin,
        Long idCityDestiny
) {
}
