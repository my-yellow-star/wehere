package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.OAuth2TokenAuthentication
import javax.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.util.matcher.RequestMatcher

class OAuth2TokenAuthenticationFilter(
    requestMatcher: RequestMatcher,
    authenticationManager: AuthenticationManager
) : AbstractBearerAuthenticationFilter(requestMatcher, authenticationManager) {
    override fun getAuthentication(request: HttpServletRequest): Authentication =
        OAuth2TokenAuthentication(getAuthorizationHeader(request)!!, getPlatformProvider(request))

    private fun getPlatformProvider(request: HttpServletRequest) =
        request.getHeader("Provider")
}