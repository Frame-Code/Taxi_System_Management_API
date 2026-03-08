package dto;

import java.util.Optional;

public record GeneralResponse<T>(
        boolean isSuccess,
        String message,
        Optional<T> objectResponse
)
{}
