package api.epilogue.wehere.auth.application

import java.util.UUID
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationProvider(
    private val jwtProcessor: JwtProcessor
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val claims = jwtProcessor.parseClaims((authentication as JwtAuthentication).credentials)
        return MemberAuthentication(
            UUID.fromString(claims.get("id", String::class.java)),
            claims.get("grade", String::class.java)
        )
    }

    override fun supports(authentication: Class<*>) =
        authentication.isAssignableFrom(JwtAuthentication::class.java)
}