package api.epilogue.wehere.auth.application

import java.util.UUID

data class TokenCredential(
    val accessToken: String,
    val refreshToken: UUID
)