package api.epilogue.wehere.auth.domain

import api.epilogue.wehere.member.domain.Member
import api.epilogue.wehere.member.domain.Member.MemberGrade
import api.epilogue.wehere.member.domain.Member.MemberPlatformType
import java.util.UUID
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2Member(
    val id: UUID,
    private val name: String,
    private val grade: MemberGrade,
    private val email: String,
    private val platformUid: String,
    private val platformType: MemberPlatformType,
    private val profileImageUrl: String?
) : OAuth2User {
    companion object {
        fun of(member: Member) = OAuth2Member(
            member.id,
            member.nickname,
            member.grade,
            member.email,
            member.platformUid,
            member.platformType,
            member.profileImageUrl
        )
    }

    override fun getName(): String =
        name

    override fun <A : Any?> getAttribute(name: String): A =
        attributes[name] as A

    override fun getAttributes(): Map<String, Any?> =
        mapOf(
            "email" to email,
            "uid" to platformUid,
            "type" to platformType,
            "profileImage" to profileImageUrl
        )

    override fun getAuthorities(): Collection<GrantedAuthority> =
        setOf(SimpleGrantedAuthority(grade.name))
}