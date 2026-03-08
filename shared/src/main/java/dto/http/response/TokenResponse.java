package dto.http.response;

public record TokenResponse(
        String access_token,
        String user_name
) {
}
