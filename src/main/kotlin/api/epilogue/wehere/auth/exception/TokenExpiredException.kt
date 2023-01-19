package api.epilogue.wehere.auth.exception

import org.springframework.security.core.AuthenticationException

class TokenExpiredException : AuthenticationException("token expired")