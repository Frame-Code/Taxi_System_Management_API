package dto;

import java.util.concurrent.TimeUnit;

public record RideInfoDTO(
        double approxDistance,
        double approxTime
) {
    public double getApproxTime() {
        return (TimeUnit.SECONDS.toMinutes((long) approxTime)
                - (TimeUnit.SECONDS.toHours((long) approxTime) * 60));
    }
}
