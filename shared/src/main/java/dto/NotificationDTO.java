package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter @Setter
public class NotificationDTO{
    private Long id;
    private String title;
    private String message;
    private ClientDTO clientDTO;
    private TaxiDTO taxiDTO;
}
