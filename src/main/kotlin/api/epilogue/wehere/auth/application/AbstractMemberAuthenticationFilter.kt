package api.epilogue.wehere.auth.application

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.RequestMatcher

abstract class AbstractMemberAuthenticationFilter(
    requestMatcher: RequestMatcher
) : AbstractAuthenticationProcessingFilter(requestMatcher) {
    object EmptyHandler : AuthenticationSuccessHandler {
        override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
        }
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication =
        authenticationManager
            .authenticate(getAuthentication(request))
            .also {
                SecurityContextHolder.getContext().authentication = it
            }

    abstract fun getAuthentication(request: HttpServletRequest): Authentication

    fun setup(authenticationManager: AuthenticationManager) =
        apply {
            setContinueChainBeforeSuccessfulAuthentication(true)
            setAuthenticationManager(authenticationManager)
            setAuthenticationSuccessHandler(EmptyHandler)
            setAuthenticationFailureHandler(AuthenticationFailureHandler())
        }
}