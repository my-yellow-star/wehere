package api.epilogue.wehere.config

import api.epilogue.wehere.auth.infra.AppleClient
import api.epilogue.wehere.auth.infra.AppleIdTokenPayloadVerifier
import api.epilogue.wehere.auth.infra.AppleOAuth2TokenAuthorizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AppleProperties::class)
class AppleConfig(
    private val properties: AppleProperties
) {
    @Bean
    fun appleOAuth2TokenAuthorizer(appleClient: AppleClient) =
        AppleOAuth2TokenAuthorizer(
            appleClient,
            AppleIdTokenPayloadVerifier(properties)
        )
}

@ConstructorBinding
@ConfigurationProperties(prefix = "apple")
class AppleProperties(
    val issuer: String,
    val clientId: String
)