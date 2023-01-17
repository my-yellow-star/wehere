package api.epilogue.wehere.auth.application

import api.epilogue.wehere.member.domain.Member.MemberGrade
import java.util.UUID
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class MemberAuthentication(
    private val id: UUID,
    private val grade: String
) : Authentication {
    override fun getName(): String = ""

    override fun getAuthorities() =
        listOf(SimpleGrantedAuthority(grade))

    override fun getCredentials() = null

    override fun getDetails() =
        MemberDetail(
            id,
            MemberGrade.valueOf(grade)
        )

    override fun getPrincipal() = id

    override fun isAuthenticated() = true

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    data class MemberDetail(
        val id: UUID,
        val grade: MemberGrade
    )
}