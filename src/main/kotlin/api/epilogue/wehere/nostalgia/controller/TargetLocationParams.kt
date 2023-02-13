package api.epilogue.wehere.nostalgia.controller

import api.epilogue.wehere.nostalgia.domain.Location
import org.springframework.web.bind.annotation.RequestParam

data class TargetLocationParams(
    @RequestParam(required = false)
    val targetLatitude: Double?,
    @RequestParam(required = false)
    val targetLongitude: Double?
) {
    fun parse() =
        if (targetLatitude != null && targetLongitude != null)
            Location(targetLatitude, targetLongitude)
        else null
}