package api.epilogue.wehere.config

import api.epilogue.wehere.auth.application.JwtProcessor
import api.epilogue.wehere.auth.application.JwtProcessorImpl
import java.security.KeyFactory
import java.security.KeyPair
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig(
    private val properties: JwtProperties
) {
    @Bean
    fun jwtProcessor(): JwtProcessor =
        JwtProcessorImpl(KeyFactory.getInstance("RSA").getKeyPair(properties))

    private fun KeyFactory.getKeyPair(properties: JwtProperties) =
        KeyPair(
            generatePublic(
                X509EncodedKeySpec(
                    Base64.getDecoder().decode(properties.publicKey.toByteArray())
                )
            ),
            generatePrivate(
                PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(properties.privateKey.toByteArray())
                )
            )
        )
}

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val publicKey: String,
    val privateKey: String
)