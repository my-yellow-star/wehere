package api.epilogue.wehere.nostalgia.domain

import org.locationtech.jts.geom.Point
import org.locationtech.jts.io.WKTReader

object LocationFactory {
    fun of(latitude: Double, longitude: Double) =
        WKTReader().read("Point($latitude $longitude)") as Point
}