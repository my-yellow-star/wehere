package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Member
import java.util.UUID

interface RefreshTokenService {
    fun create(oAuth2Member: OAuth2Member): UUID
}