package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.kernel.LocationUtils
import org.locationtech.jts.geom.Point

data class Location(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun of(point: Point) = Location(point.y, point.x)
    }

    fun toPoint() = LocationUtils.toPoint(latitude, longitude)

    fun distance(compare: Location) =
        LocationUtils.calculateDistance(latitude, longitude, compare.latitude, compare.longitude)
}
