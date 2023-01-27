package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.member.application.MemberListOutput
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.time.Instant
import java.util.UUID

data class NostalgiaListOutput(
    val id: UUID,
    val member: MemberListOutput,
    val title: String,
    val description: String,
    val location: Location,
    val distance: Int?,
    val thumbnail: String?,
    val createdAt: Instant
) {
    companion object {
        fun of(nostalgia: Nostalgia, current: Location?) = NostalgiaListOutput(
            id = nostalgia.id,
            member = MemberListOutput.of(nostalgia.member),
            title = nostalgia.title,
            description = nostalgia.description,
            location = Location.of(nostalgia.location),
            distance = current?.let { Location.of(nostalgia.location).distance(it).toInt() },
            thumbnail = nostalgia.thumbnailUrl,
            createdAt = nostalgia.createdAt,
        )
    }
}