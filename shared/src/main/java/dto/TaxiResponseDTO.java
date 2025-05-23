package dto;

public record TaxiResponseDTO(
        boolean isAccepted,
        boolean isRejected,
        boolean isTimeOut,
        boolean isPending
) {
}
