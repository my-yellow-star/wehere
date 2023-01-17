package api.epilogue.wehere.auth.exception

import javax.naming.AuthenticationException

class InvalidTokenException(override val message: String? = "invalid token"): AuthenticationException(message)