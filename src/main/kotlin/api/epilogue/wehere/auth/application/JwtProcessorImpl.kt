package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.exception.InvalidTokenException
import api.epilogue.wehere.auth.exception.TokenExpiredException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import java.security.KeyPair
import java.time.Instant
import java.util.Date

class JwtProcessorImpl(
    private val keyPair: KeyPair
) : JwtProcessor {
    override fun generate(claims: Map<String, Any?>): String =
        Jwts.builder()
            .setIssuer(ISSUER)
            .setIssuedAt(Date())
            .setClaims(claims)
            .signWith(keyPair.private)
            .setExpiration(Date.from(Instant.now().plusSeconds(DURATION)))
            .compact()

    override fun parseClaims(jwt: String): Claims =
        kotlin.runCatching {
            Jwts.parserBuilder()
                .setSigningKey(keyPair.public)
                .build()
                .parseClaimsJws(jwt)
                .body
        }.getOrElse {
            when (it) {
                is ExpiredJwtException -> throw TokenExpiredException()
                else -> throw InvalidTokenException(it.message)
            }
        }

    companion object {
        const val ISSUER = "wehere"
        const val DURATION = 600L
    }
}