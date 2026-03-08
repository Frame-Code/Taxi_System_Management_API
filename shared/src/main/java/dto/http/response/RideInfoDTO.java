package dto.http.response;

import dto.out.DistanceInfoDTO;

public record RideInfoDTO(
        DistanceInfoDTO distanceInfoDTO,
        double totalPrice
) {
}
