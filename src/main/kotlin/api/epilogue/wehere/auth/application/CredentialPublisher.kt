package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import org.springframework.stereotype.Service

@Service
class CredentialPublisher(
    private val memberSessionService: MemberSessionService,
    private val accessTokenService: AccessTokenService
) {
    fun publishToken(memberPrincipal: MemberPrincipal): TokenCredential =
        TokenCredential(
            accessTokenService.create(memberPrincipal),
            memberSessionService.create(memberPrincipal)
        )
}