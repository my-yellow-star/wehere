package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberAuthentication
import api.epilogue.wehere.auth.domain.SessionAuthentication
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class SessionAuthenticationProvider(
    private val sessionService: MemberSessionService
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?) =
        sessionService.findUser((authentication as SessionAuthentication).id)
            .let {
                MemberAuthentication(
                    it.id,
                    it.grade.name
                )
            }

    override fun supports(authentication: Class<*>) =
        authentication.isAssignableFrom(SessionAuthentication::class.java)
}