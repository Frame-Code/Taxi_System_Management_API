package utils;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKTWriter;

public class GeolocationUtils {
    private static final GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Point createPoint(double latitude, double longitude) {
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

    public static String pointToWKT(Point point) {
        return new WKTWriter().write(point);
    }

    public static String coordinatesToWKT(double latitude, double longitude) {
        return pointToWKT(createPoint(latitude, longitude));
    }
}
