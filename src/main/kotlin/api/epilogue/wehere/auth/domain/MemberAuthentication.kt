package api.epilogue.wehere.auth.domain

import java.util.UUID
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class MemberAuthentication(
    val id: UUID,
    val grade: String
) : Authentication {
    override fun getName(): String = ""

    override fun getAuthorities() =
        listOf(SimpleGrantedAuthority(grade))

    override fun getCredentials() = null

    override fun getDetails() = null

    override fun getPrincipal() = MemberPrincipal.of(this)

    override fun isAuthenticated() = true

    override fun setAuthenticated(isAuthenticated: Boolean) {}
}