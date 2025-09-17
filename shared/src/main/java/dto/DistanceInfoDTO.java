package dto;


public record DistanceInfoDTO(
        double approxDistance,
        double approxSeconds
) {
    public double getApproxMinutes() {
        return approxSeconds / 60;
    }
}
