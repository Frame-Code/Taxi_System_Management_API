package dto;

public record FullCoordinatesDTO(
        CoordinatesDTO origin,
        CoordinatesDTO destiny
) {
    public String getOrigin(String splitter) {
        return origin.longitude() + splitter + origin.latitude();
    }
    public String getDestiny(String splitter) {
        return origin.longitude() + splitter + origin.latitude();
    }
}
