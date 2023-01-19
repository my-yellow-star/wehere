package api.epilogue.wehere.nostalgia.application

import org.locationtech.jts.geom.Point

data class Location(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun of(point: Point) = Location(point.y, point.x)
    }
}
