package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.MarkerColor
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.time.Instant

data class UpdateNostalgiaInput(
    val title: String?,
    val description: String?,
    val visibility: Nostalgia.NostalgiaVisibility?,
    val images: List<String>?,
    val markerColor: MarkerColor?,
    val location: Location?,
    val isRealLocation: Boolean?,
    val memorizedAt: Instant?
)
