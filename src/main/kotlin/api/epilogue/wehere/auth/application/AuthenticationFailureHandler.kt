package api.epilogue.wehere.auth.application

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler

class AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        saveException(request, exception)
        response.run {
            contentType = "application/json;charset=utf-8"
            sendError(HttpStatus.UNAUTHORIZED.value(), exception?.message)
        }
    }
}