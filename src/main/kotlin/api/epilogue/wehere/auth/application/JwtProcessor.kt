package api.epilogue.wehere.auth.application

import io.jsonwebtoken.Claims

interface JwtProcessor {
    fun generate(claims: Map<String, Any?>): String
    fun parseClaims(jwt: String): Claims
}