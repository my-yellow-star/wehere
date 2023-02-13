package api.epilogue.wehere.nostalgia.controller

import api.epilogue.wehere.nostalgia.domain.Location

data class TargetLocationParams(
    val targetLatitude: Double,
    val targetLongitude: Double
) {
    fun parse() = Location(targetLatitude, targetLongitude)
}