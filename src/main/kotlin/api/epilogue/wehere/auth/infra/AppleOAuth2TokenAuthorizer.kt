package api.epilogue.wehere.auth.infra

import api.epilogue.wehere.auth.domain.OAuth2Attribute
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthentication
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthorizer
import api.epilogue.wehere.auth.exception.InvalidOAuth2TokenException
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

class AppleOAuth2TokenAuthorizer(
    private val appleClient: AppleClient,
    private val tokenPayloadVerifier: AppleIdTokenPayloadVerifier
) : OAuth2TokenAuthorizer.PlatformSpecificOAuth2TokenAuthorizer {
    override val provider: String = "apple"
    private val mapper = ObjectMapper()

    override fun authorizeInternal(authentication: OAuth2TokenAuthentication): OAuth2Attribute {
        val header = parseHeader(authentication.token)
        val publicKey = appleClient
            .getPublicKeys
            ?.getKeyMatched(header["kid"].toString(), header["alg"].toString())
            ?.let { toRSAPublicKey(it) }
            ?: throw InvalidOAuth2TokenException("public key not found")
        val payload = Jwts
            .parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(authentication.token)
            .body
        if (!tokenPayloadVerifier.verify(payload))
            throw InvalidOAuth2TokenException("invalid token payload")
        val attributes = mapOf(
            "sub" to payload.subject,
            "email" to payload["email"],
            "picture" to payload["picture"]
        )
        return OAuth2Attribute.of(provider, attributes)
    }

    private fun parseHeader(token: String) =
        token
            .substring(0, token.indexOf("."))
            .let { decodeToUTF8(it) }

    private fun decodeToUTF8(value: String) =
        mapper.readValue(String(Base64.getDecoder().decode(value), Charsets.UTF_8), Map::class.java)

    private fun toRSAPublicKey(key: ApplePublicKey): PublicKey {
        val decoder = Base64.getUrlDecoder()
        val n = BigInteger(1, decoder.decode(key.n))
        val e = BigInteger(1, decoder.decode(key.e))
        val publicKeySpec = RSAPublicKeySpec(n, e)
        val keyFactory = KeyFactory.getInstance(key.kty)
        return keyFactory.generatePublic(publicKeySpec)
    }
}