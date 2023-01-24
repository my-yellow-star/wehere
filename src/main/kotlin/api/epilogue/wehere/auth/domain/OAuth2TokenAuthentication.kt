package api.epilogue.wehere.auth.domain

import org.springframework.security.authentication.AbstractAuthenticationToken

class OAuth2TokenAuthentication(
    val token: String,
    val provider: String
) : AbstractAuthenticationToken(emptyList()) {
    override fun getCredentials() = null

    override fun getPrincipal() = null
}