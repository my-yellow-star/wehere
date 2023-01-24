package api.epilogue.wehere.auth.domain

import api.epilogue.wehere.member.domain.Member.MemberPlatformType

data class OAuth2Attribute(
    val type: MemberPlatformType,
    val uid: String,
    val email: String,
    val profileImage: String?
) {
    companion object {
        private const val GOOGLE_UID_KEY = "sub"
        private const val GOOGLE_EMAIL_KEY = "email"
        private const val GOOGLE_PROFILE_IMAGE_KEY = "picture"

        fun of(registrationId: String, attributes: Map<String, Any?>) =
            when (registrationId) {
                "google" -> ofGoogle(attributes)
                else -> TODO()
            }

        private fun ofGoogle(attributes: Map<String, Any?>) =
            OAuth2Attribute(
                type = MemberPlatformType.GOOGLE,
                uid = attributes[GOOGLE_UID_KEY].toString(),
                email = attributes[GOOGLE_EMAIL_KEY].toString(),
                profileImage = attributes[GOOGLE_PROFILE_IMAGE_KEY]?.toString()
            )
    }
}