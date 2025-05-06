package dto;

import com.google.gson.Gson;

public record ClientDTO(
        Long id,
        UserDTO userDTO,
        CoordinatesDTO coordinatesDTO) {
    public String getInfoJSON() {
        return new Gson().toJson(this);
    }
}
