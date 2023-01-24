package api.epilogue.wehere.auth.infra

import api.epilogue.wehere.auth.domain.OAuth2Attribute
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthentication
import api.epilogue.wehere.auth.domain.OAuth2TokenAuthorizer.PlatformSpecificOAuth2TokenAuthorizer
import api.epilogue.wehere.auth.exception.InvalidOAuth2TokenException
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.springframework.util.Assert

class GoogleOAuth2TokenAuthorizer(
    private val clientIds: List<String>
) : PlatformSpecificOAuth2TokenAuthorizer {
    override val provider: String = "google"

    override fun authorizeInternal(authentication: OAuth2TokenAuthentication): OAuth2Attribute =
        kotlin.runCatching {
            val idToken = GoogleIdToken.parse(jsonFactory, authentication.token)
            Assert.isTrue(verifier.verify(idToken), "Invalid token payload")
            val payload = idToken.payload
            val attributes = mapOf(
                "sub" to payload.subject,
                "email" to payload.email,
                "picture" to payload["picture"]
            )
            return OAuth2Attribute.of(provider, attributes)
        }.getOrElse {
            throw InvalidOAuth2TokenException(it.message)
        }

    private val verifier by lazy {
        GoogleIdTokenPayloadVerifier.Companion
            .Builder(NetHttpTransport(), jsonFactory)
            .setCertificatesLocation(GOOGLE_CERTIFICATE_V3)
            .setAudience(clientIds)
            .build() as GoogleIdTokenPayloadVerifier
    }
    private val jsonFactory by lazy {
        GsonFactory()
    }

    companion object {
        const val GOOGLE_CERTIFICATE_V3 = "https://www.googleapis.com/oauth2/v3/certs"
    }
}