package api.epilogue.wehere.kernel

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.locationtech.jts.util.GeometricShapeFactory

object LocationUtils {
    fun toPoint(latitude: Double, longitude: Double) =
        WKTReader().read("Point($longitude $latitude)") as Point

    fun createCircle(latitude: Double, longitude: Double, radius: Double): Geometry =
        GeometricShapeFactory()
            .apply {
                setNumPoints(32)
                setCentre(Coordinate(longitude, latitude))
                setSize(radius * 2)
            }
            .createCircle()

    /**
     * @return distance in meter
     */
    fun calculateDistance(
        latitude: Double,
        longitude: Double,
        latitude2: Double,
        longitude2: Double
    ) =
        rad2deg(
            acos(
                sin(deg2rad(latitude)) * sin(deg2rad(latitude2))
                        + cos(deg2rad(latitude)) * cos(deg2rad(latitude2)) * cos(deg2rad(longitude - longitude2))
            )
        ) * 60 * 1151.5 * 1.609344

    private fun deg2rad(deg: Double) = deg * Math.PI / 180.0

    private fun rad2deg(rad: Double) = rad * 180.0 / Math.PI
}