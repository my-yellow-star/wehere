package api.epilogue.wehere.member.application

data class UpdateMemberInput(
    val nickname: String?,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val description: String?
)