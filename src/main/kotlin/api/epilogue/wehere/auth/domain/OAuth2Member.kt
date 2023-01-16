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
    val nickname: String,
    val grade: MemberGrade,
    val email: String,
    val platformUid: String,
    val platformType: MemberPlatformType,
    val profileImageUrl: String?
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
        nickname

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