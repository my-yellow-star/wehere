package api.epilogue.wehere.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(KakaoProperties::class)
class KakaoConfig

@ConstructorBinding
@ConfigurationProperties(prefix = "kakao")
data class KakaoProperties(
    val mapApiKey: String
)