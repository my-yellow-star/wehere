package api.epilogue.wehere.member.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.member.application.MemberGetter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val getter: MemberGetter
) {
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal principal: MemberPrincipal) =
        getter.get(principal.id)
}