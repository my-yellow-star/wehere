package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.JwtAuthentication
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class JwtAuthenticationFilter(
    requestMatcher: AntPathRequestMatcher
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
        authenticationManager.authenticate(JwtAuthentication(getAuthorizationHeader(request)!!))

    override fun requiresAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Boolean =
        getAuthorizationHeader(request).isNullOrEmpty().not()

    fun setup(authenticationManager: AuthenticationManager) =
        apply {
            setContinueChainBeforeSuccessfulAuthentication(true)
            setAuthenticationManager(authenticationManager)
            setAuthenticationSuccessHandler(EmptyHandler)
            setAuthenticationFailureHandler(AuthenticationFailureHandler())
        }

    private fun getAuthorizationHeader(request: HttpServletRequest) =
        request.getHeader("Authorization")?.replace(BEARER_PREFIX, "")

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }
}