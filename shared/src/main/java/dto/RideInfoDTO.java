package dto;

public record RideInfoDTO(
        DistanceInfoDTO distanceInfoDTO,
        double totalPrice
) {
}
