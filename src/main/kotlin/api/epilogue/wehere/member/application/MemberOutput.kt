package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member
import java.time.Instant
import java.util.UUID

data class MemberOutput(
    val id: UUID,
    val nickname: String,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val description: String?,
    val email: String,
    val platformType: Member.MemberPlatformType,
    val grade: Member.MemberGrade,
    val createdAt: Instant
) {
    companion object {
        fun of(member: Member) = MemberOutput(
            id = member.id,
            nickname = member.nickname,
            profileImageUrl = member.profileImageUrl,
            backgroundImageUrl = member.backgroundImageUrl,
            description = member.description,
            email = member.email,
            platformType = member.platformType,
            grade = member.grade,
            createdAt = member.createdAt
        )
    }
}