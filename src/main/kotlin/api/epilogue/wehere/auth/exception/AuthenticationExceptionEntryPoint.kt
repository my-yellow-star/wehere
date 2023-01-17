package api.epilogue.wehere.auth.exception

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class AuthenticationExceptionEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        response.run {
            contentType = "application/json;charset=utf-8"
            sendError(HttpStatus.UNAUTHORIZED.value(), exception?.message)
        }
    }
}