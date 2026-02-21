package dto.http;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class HttpBaseResponse {
    private Object response;
    private String status_code;
    private String status_message;
    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private final String thanks = "Thanks for using this API :)";
}
