package api.epilogue.wehere.auth.controller

import api.epilogue.wehere.auth.application.CredentialPublisher
import api.epilogue.wehere.auth.domain.OAuth2Member
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val credentialPublisher: CredentialPublisher
) {
    @GetMapping("/token")
    fun getToken(@AuthenticationPrincipal oAuth2Member: OAuth2Member) =
        credentialPublisher.publishToken(oAuth2Member)
}