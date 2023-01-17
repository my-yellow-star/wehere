package api.epilogue.wehere.auth.domain

import api.epilogue.wehere.member.domain.Member.MemberGrade
import java.util.UUID

data class MemberPrincipal(
    val id: UUID,
    val grade: MemberGrade
) {
    companion object {
        fun of(authentication: MemberAuthentication) =
            MemberPrincipal(
                authentication.id,
                MemberGrade.valueOf(authentication.grade)
            )

        fun of(oAuth2Member: OAuth2Member) =
            MemberPrincipal(
                oAuth2Member.id,
                oAuth2Member.grade
            )
    }
}