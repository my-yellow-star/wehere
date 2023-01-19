package api.epilogue.wehere.auth.application

import api.epilogue.wehere.auth.domain.SessionAuthentication
import api.epilogue.wehere.auth.exception.InvalidSessionException
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.util.matcher.RequestMatcher

class SessionAuthenticationFilter(
    requestMatcher: RequestMatcher,
    authenticationManager: AuthenticationManager
) : AbstractMemberAuthenticationFilter(requestMatcher, authenticationManager) {
    override fun requiresAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Boolean =
        getSessionHeader(request) != null

    override fun getAuthentication(request: HttpServletRequest) =
        SessionAuthentication(getSessionHeader(request)!!)

    private fun getSessionHeader(request: HttpServletRequest) =
        request.getHeader("SessionId")
            .takeUnless { it.isNullOrEmpty() }
            ?.let {
                kotlin.runCatching {
                    UUID.fromString(it)
                }.getOrElse {
                    throw InvalidSessionException()
                }
            }
}