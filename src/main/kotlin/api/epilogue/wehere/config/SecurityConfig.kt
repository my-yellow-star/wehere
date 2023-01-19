package api.epilogue.wehere.config

import api.epilogue.wehere.auth.application.AbstractMemberAuthenticationFilter
import api.epilogue.wehere.auth.application.JwtAuthenticationFilter
import api.epilogue.wehere.auth.application.JwtAuthenticationProvider
import api.epilogue.wehere.auth.application.SessionAuthenticationFilter
import api.epilogue.wehere.auth.application.SessionAuthenticationProvider
import api.epilogue.wehere.auth.exception.AuthenticationExceptionEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.HttpSecurityDsl
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.util.matcher.AnyRequestMatcher

@EnableWebSecurity
class SecurityConfig {
    @Order(1)
    @Configuration
    class SessionConfig(
        private val sessionAuthenticationProvider: SessionAuthenticationProvider
    ) {
        @Bean
        fun sessionFilterChain(http: HttpSecurity): SecurityFilterChain? {
            http {
                setup()
                headers {
                    cacheControl { disable() }
                    xssProtection {
                        block = true
                        xssProtectionEnabled = true
                    }
                }
                securityMatcher("/session/**/*")
                sessionManagement {
                    sessionCreationPolicy = SessionCreationPolicy.STATELESS
                }
                authorizeRequests { authorize(anyRequest, permitAll) }
                setupAuthenticationManager(http, sessionAuthenticationProvider)
                addAuthenticationFilter(
                    SessionAuthenticationFilter(
                        AnyRequestMatcher.INSTANCE,
                        authenticationManager!!
                    )
                )
                httpBasic {
                    authenticationEntryPoint = AuthenticationExceptionEntryPoint()
                }
            }
            return http.build()
        }
    }

    @Order(2)
    @Configuration
    class OAuth2Config(
        private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    ) {
        @Bean
        fun oAuth2FilterChain(http: HttpSecurity): SecurityFilterChain? {
            http {
                setup()
                securityMatcher("/oauth2/**/*", "/login/oauth2/**/*")
                httpBasic {
                    authenticationEntryPoint = AuthenticationExceptionEntryPoint()
                }
                oauth2Login {
                    userInfoEndpoint {
                        userService = oAuth2UserService
                    }
                    defaultSuccessUrl("/oauth2/credential", true)
                }
            }
            return http.build()
        }
    }

    @Order(3)
    @Configuration
    class ApiConfig(
        private val jwtAuthenticationProvider: JwtAuthenticationProvider
    ) {
        @Bean
        fun jwtFilterChain(http: HttpSecurity): SecurityFilterChain? {
            http {
                setup()
                securityMatcher("/api/**/*")
                sessionManagement {
                    sessionCreationPolicy = SessionCreationPolicy.STATELESS
                }
                authorizeRequests { authorize(anyRequest) }
                setupAuthenticationManager(http, jwtAuthenticationProvider)
                addAuthenticationFilter(JwtAuthenticationFilter(AnyRequestMatcher.INSTANCE, authenticationManager!!))
                httpBasic {
                    authenticationEntryPoint = AuthenticationExceptionEntryPoint()
                }
            }
            return http.build()
        }
    }
}

private fun HttpSecurityDsl.setup() =
    apply {
        csrf { disable() }
        headers {
            cacheControl { disable() }
            xssProtection {
                block = true
                xssProtectionEnabled = true
            }
        }
    }

private fun HttpSecurityDsl.setupAuthenticationManager(http: HttpSecurity, provider: AuthenticationProvider) {
    authenticationManager = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        .authenticationProvider(provider)
        .build()
}

private fun HttpSecurityDsl.addAuthenticationFilter(filter: AbstractMemberAuthenticationFilter) =
    addFilterAfter<AbstractPreAuthenticatedProcessingFilter>(filter.setup(authenticationManager!!))