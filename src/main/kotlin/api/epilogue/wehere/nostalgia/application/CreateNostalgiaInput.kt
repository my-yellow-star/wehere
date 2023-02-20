package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.nostalgia.domain.MarkerColor
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.time.Instant

data class CreateNostalgiaInput(
    val title: String,
    val description: String,
    val visibility: Nostalgia.NostalgiaVisibility,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>,
    val markerColor: MarkerColor,
    val isRealLocation: Boolean?,
    val memorizedAt: Instant?
) {
    fun toNostalgia(member: Member) =
        Nostalgia(
            member,
            title,
            description,
            visibility,
            latitude,
            longitude,
            images.firstOrNull(),
            markerColor,
            isRealLocation ?: true,
            memorizedAt ?: Instant.now()
        ).apply {
            addMedia(images)
        }
}