package dto;

public record NotificationDTO(
    String title,
    String message,
    ClientDTO clientDTO,
    TaxiDTO taxiDTO
) {

}
