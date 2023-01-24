package api.epilogue.wehere.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidOAuth2TokenException(message: String?) : AuthenticationException("Invalid OAuth2 Token: $message")