package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.MemberPrincipal
import org.springframework.stereotype.Component

@Component
class JwtAccessTokenService(
    private val jwtProcessor: JwtProcessor
) : AccessTokenService {
    override fun create(memberPrincipal: MemberPrincipal): String =
        jwtProcessor.generate(toClaim(memberPrincipal))

    private fun toClaim(memberPrincipal: MemberPrincipal) =
        mapOf(
            "id" to memberPrincipal.id,
            "grade" to memberPrincipal.grade.name
        )
}