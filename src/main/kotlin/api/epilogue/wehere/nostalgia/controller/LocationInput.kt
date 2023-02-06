package api.epilogue.wehere.nostalgia.controller

import api.epilogue.wehere.nostalgia.domain.Location

data class LocationInput(
    val latitude: Double?,
    val longitude: Double?
) {
    fun toLocation() =
        if (latitude != null && longitude != null)
            Location(latitude, longitude)
        else null
}