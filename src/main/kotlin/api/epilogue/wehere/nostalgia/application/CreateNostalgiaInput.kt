package api.epilogue.wehere.nostalgia.application

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.nostalgia.domain.Nostalgia

data class CreateNostalgiaInput(
    val title: String,
    val description: String,
    val visibility: Nostalgia.NostalgiaVisibility,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>
) {
    fun toNostalgia(member: Member) =
        Nostalgia(
            member,
            title,
            description,
            visibility,
            latitude,
            longitude,
            images.firstOrNull()
        )
}