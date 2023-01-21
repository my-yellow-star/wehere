package api.epilogue.wehere.auth.controller

import api.epilogue.wehere.auth.application.MemberSessionService
import api.epilogue.wehere.auth.domain.MemberPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val sessionService: MemberSessionService
) {
    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal principal: MemberPrincipal) {
        sessionService.expireAll(principal.id)
    }
}