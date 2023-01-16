package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2Member
import io.jsonwebtoken.Jwts
import javax.crypto.SecretKey
import org.springframework.stereotype.Component

@Component
class JwtService(
    private val secretKey: SecretKey
) : AccessTokenService {
    override fun create(oAuth2Member: OAuth2Member): String =
        Jwts.builder()
            .setId("wehere")
            .setClaims(toClaim(oAuth2Member))
            .signWith(secretKey)
            .compact()

    private fun toClaim(oAuth2Member: OAuth2Member) =
        mapOf(
            "id" to oAuth2Member.id,
            "grade" to oAuth2Member.grade.name
        )
}