package api.epilogue.wehere.config

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import javax.crypto.SecretKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf().disable()
            .oauth2Login {
                it.userInfoEndpoint().userService(oAuth2UserService)
                it.defaultSuccessUrl("/api/auth/token")
            }
            .build()

    @Bean
    fun jwtSecretKey(): SecretKey =
        Keys.secretKeyFor(SignatureAlgorithm.HS256)
}