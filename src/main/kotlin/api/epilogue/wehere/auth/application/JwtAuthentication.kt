package api.epilogue.wehere.auth.application

import org.springframework.security.authentication.AbstractAuthenticationToken

data class JwtAuthentication(
    private val token: String
) : AbstractAuthenticationToken(emptyList()) {
    override fun getCredentials(): String = token

    override fun getPrincipal(): Any? = null
}