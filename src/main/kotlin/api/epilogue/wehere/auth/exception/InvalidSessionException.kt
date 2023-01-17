package api.epilogue.wehere.auth.exception

import org.springframework.security.core.AuthenticationException

class InvalidSessionException : AuthenticationException("Invalid session")