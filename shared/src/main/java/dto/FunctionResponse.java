package dto;

import java.util.Optional;

public record FunctionResponse<T>(
        boolean isSuccess,
        String message,
        Optional<T> objectResponse
)
{}
