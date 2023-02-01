package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.member.application.MemberListOutput
import api.epilogue.wehere.nostalgia.domain.Location
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.time.Instant
import java.util.UUID

data class NostalgiaOutput(
    val id: UUID,
    val member: MemberListOutput,
    val title: String,
    val description: String,
    val location: Location,
    val distance: Int?,
    val thumbnail: String?,
    val images: List<String>,
    val createdAt: Instant,
    val visibility: Nostalgia.NostalgiaVisibility,
) {
    companion object {
        fun of(nostalgia: Nostalgia, current: Location?) = NostalgiaOutput(
            id = nostalgia.id,
            member = MemberListOutput.of(nostalgia.member),
            title = nostalgia.title,
            description = nostalgia.description,
            location = Location.of(nostalgia.location),
            distance = current?.let { Location.of(nostalgia.location).distance(it).toInt() },
            thumbnail = nostalgia.thumbnailUrl,
            images = nostalgia.media.map { it.url },
            createdAt = nostalgia.createdAt,
            visibility = nostalgia.visibility
        )
    }
}