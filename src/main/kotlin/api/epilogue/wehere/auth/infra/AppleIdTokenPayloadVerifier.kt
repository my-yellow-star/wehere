package api.epilogue.wehere.auth.infra

import api.epilogue.wehere.config.AppleProperties
import io.jsonwebtoken.Claims

class AppleIdTokenPayloadVerifier(
    private val properties: AppleProperties
) {
    fun verify(payload: Claims): Boolean {
        if (!payload.issuer.contains(properties.issuer))
            return false
        if (payload.audience != properties.clientId)
            return false
        return true
    }
}