package dto.ws;

import java.time.LocalDateTime;

public record WsResponse(
    LocalDateTime timeStamp,
    String message
) { }
