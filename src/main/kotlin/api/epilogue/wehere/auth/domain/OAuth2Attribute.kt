package api.epilogue.wehere.auth.domain

import api.epilogue.wehere.member.domain.Member.MemberPlatformType

data class OAuth2Attribute(
    val type: MemberPlatformType,
    val uid: String,
    val email: String,
    val profileImage: String?
) {
    companion object {
        fun of(registrationId: String, attributes: Map<String, Any>) =
            when (registrationId) {
                "google" -> ofGoogle(attributes)
                else -> TODO()
            }

        private fun ofGoogle(attributes: Map<String, Any>) =
            OAuth2Attribute(
                type = MemberPlatformType.GOOGLE,
                uid = attributes["sub"].toString(),
                email = attributes["email"].toString(),
                profileImage = attributes["picture"]?.toString()
            )
    }
}