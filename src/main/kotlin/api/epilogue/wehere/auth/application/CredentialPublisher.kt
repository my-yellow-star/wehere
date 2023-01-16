package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Member
import org.springframework.stereotype.Service

@Service
class CredentialPublisher(
    private val refreshTokenService: RefreshTokenService,
    private val accessTokenService: AccessTokenService
) {
    fun publishToken(oAuth2Member: OAuth2Member): TokenCredential =
        TokenCredential(
            accessTokenService.create(oAuth2Member),
            refreshTokenService.create(oAuth2Member)
        )
}