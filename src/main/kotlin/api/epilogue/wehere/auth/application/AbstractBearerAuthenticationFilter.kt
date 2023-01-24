package api.epilogue.wehere.auth.application

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.util.matcher.RequestMatcher

abstract class AbstractBearerAuthenticationFilter(
    requestMatcher: RequestMatcher,
    authenticationManager: AuthenticationManager
) : AbstractMemberAuthenticationFilter(requestMatcher, authenticationManager) {
    override fun requiresAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Boolean =
        getAuthorizationHeader(request).isNullOrEmpty().not()

    protected fun getAuthorizationHeader(request: HttpServletRequest) =
        request.getHeader("Authorization")?.replace(BEARER_PREFIX, "")

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }
}