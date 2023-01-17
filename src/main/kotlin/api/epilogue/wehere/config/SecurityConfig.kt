package api.epilogue.wehere.config

import api.epilogue.wehere.auth.application.JwtAuthenticationFilter
import api.epilogue.wehere.auth.application.JwtAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.web.servlet.HttpSecurityDsl
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http {
            csrf { disable() }
            headers {
                cacheControl { disable() }
                xssProtection {
                    block = true
                    xssProtectionEnabled = true
                }
            }
            setupAuthenticationManager(http)
            addJwtAuthenticationFilter()
            oauth2Login {
                userInfoEndpoint {
                    userService = oAuth2UserService
                }
                defaultSuccessUrl("/api/auth/token", true)
            }
        }
        return http.build()
    }

    private fun HttpSecurityDsl.setupAuthenticationManager(http: HttpSecurity) {
        authenticationManager = http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(jwtAuthenticationProvider)
            .build()
    }

    private fun HttpSecurityDsl.addJwtAuthenticationFilter() =
        addFilterAfter<AbstractPreAuthenticatedProcessingFilter>(
            JwtAuthenticationFilter(AntPathRequestMatcher("api/**/*"))
                .setup(authenticationManager!!)
        )
}