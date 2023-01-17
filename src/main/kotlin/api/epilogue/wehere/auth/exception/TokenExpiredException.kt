package api.epilogue.wehere.auth.exception

import javax.naming.AuthenticationException

class TokenExpiredException : AuthenticationException("token expired")