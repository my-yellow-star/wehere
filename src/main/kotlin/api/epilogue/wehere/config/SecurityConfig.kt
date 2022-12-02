package api.epilogue.wehere.config

import api.epilogue.wehere.auth.application.OAuth2UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val oAuth2UserServiceImpl: OAuth2UserServiceImpl
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf().disable()
            .oauth2Login {
                it.userInfoEndpoint().userService(oAuth2UserServiceImpl)
                it.defaultSuccessUrl("/api/auth/token")
            }
            .build()
}