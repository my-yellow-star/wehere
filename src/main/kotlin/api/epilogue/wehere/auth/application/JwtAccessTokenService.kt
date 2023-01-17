package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Member
import org.springframework.stereotype.Component

@Component
class JwtAccessTokenService(
    private val jwtProcessor: JwtProcessor
) : AccessTokenService {
    override fun create(oAuth2Member: OAuth2Member): String =
        jwtProcessor.generate(toClaim(oAuth2Member))

    private fun toClaim(oAuth2Member: OAuth2Member) =
        mapOf(
            "id" to oAuth2Member.id,
            "grade" to oAuth2Member.grade.name
        )
}