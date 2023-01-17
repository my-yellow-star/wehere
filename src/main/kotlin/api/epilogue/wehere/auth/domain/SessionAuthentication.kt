package api.epilogue.wehere.auth.domain

import java.util.UUID
import org.springframework.security.authentication.AbstractAuthenticationToken

data class SessionAuthentication(
    val id: UUID
) : AbstractAuthenticationToken(emptyList()) {
    override fun getCredentials(): String = id.toString()

    override fun getPrincipal(): Any? = null
}