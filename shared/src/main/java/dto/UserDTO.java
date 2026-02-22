package dto;

public record UserDTO(
        String names,
        String lastNames,
        String bornDate,
        String email,
        String age
) {
    public String getInfo() {
        return names + " " + lastNames + ", email: " + email;
    }
}
