package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member
import java.util.UUID

data class MemberListOutput(
    val id: UUID,
    val nickname: String,
    val profileImageUrl: String?
) {
    companion object {
        fun of(member: Member) = MemberListOutput(
            id = member.id,
            nickname = member.nickname,
            profileImageUrl = member.profileImageUrl
        )
    }
}