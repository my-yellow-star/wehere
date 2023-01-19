package api.epilogue.wehere.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidTokenException(override val message: String? = "invalid token"): AuthenticationException(message)