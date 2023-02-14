package api.epilogue.wehere.member.application

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import api.epilogue.wehere.member.domain.NicknameFactory

data class CreateMemberInput(
    val nickname: String?,
    val email: String,
    val platformUid: String,
    val platformType: MemberPlatformType,
    val profileImageUrl: String?,
    val password: String? = null
) {
    fun toMember() = Member(
        nickname = nickname ?: NicknameFactory.random(),
        email = email,
        platformUid = platformUid,
        platformType = platformType,
        profileImageUrl = profileImageUrl,
        password = password
    )
}