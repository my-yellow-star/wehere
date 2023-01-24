package api.epilogue.wehere.config

import api.epilogue.wehere.auth.infra.GoogleOAuth2TokenAuthorizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GoogleApiProperties::class)
class GoogleApiConfig(
    private val properties: GoogleApiProperties
) {
    @Bean
    fun googleOAuth2TokenAuthorizer() = GoogleOAuth2TokenAuthorizer(properties.clientIds)
}

@ConstructorBinding
@ConfigurationProperties(prefix = "google-api")
class GoogleApiProperties(
    val clientIds: List<String>
)