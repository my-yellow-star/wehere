package api.epilogue.wehere.kernel

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader
import org.locationtech.jts.util.GeometricShapeFactory

object LocationUtils {
    fun toPoint(latitude: Double, longitude: Double) =
        WKTReader().read("Point($latitude $longitude)") as Point

    fun createCircle(latitude: Double, longitude: Double, radius: Double): Geometry =
        GeometricShapeFactory()
            .apply {
                setNumPoints(32)
                setCentre(Coordinate(latitude, longitude))
                setSize(radius * 2)
            }
            .createCircle()
}