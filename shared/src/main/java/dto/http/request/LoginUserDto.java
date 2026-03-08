package dto.http.request;

public record LoginUserDto(
    String email,
    String password
)
{ }
