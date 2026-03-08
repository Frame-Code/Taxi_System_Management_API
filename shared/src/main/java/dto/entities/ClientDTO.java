package dto.entities;

import com.google.gson.Gson;
import dto.in.CoordinatesDTO;

public record ClientDTO(
        Long id,
        UserDTO userDTO,
        CoordinatesDTO coordinatesDTO) {
    public String getInfoJSON() {
        return new Gson().toJson(this);
    }
    public String getInfoAuditory() {
        return "id: " + id + "username: " + userDTO.names() + " " + userDTO.names() + ", email: " + userDTO.email();
    }
}
