package api.epilogue.wehere.auth.controller

import api.epilogue.wehere.auth.application.CredentialPublisher
import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.auth.domain.OAuth2Member
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/session")
class SessionController(
    private val credentialPublisher: CredentialPublisher
) {
    @PostMapping("/refresh")
    fun refresh(@AuthenticationPrincipal principal: MemberPrincipal) =
        credentialPublisher.publishToken(principal)
}