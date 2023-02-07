package api.epilogue.wehere.member.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.member.application.MemberGetter
import api.epilogue.wehere.member.application.MemberService
import api.epilogue.wehere.member.application.UpdateMemberInput
import java.util.UUID
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val getter: MemberGetter,
    private val service: MemberService
) {
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal principal: MemberPrincipal) =
        getter.get(principal.id)

    @GetMapping("/{memberId}")
    fun getOther(@PathVariable memberId: UUID) =
        getter.get(memberId)

    @PatchMapping("/me")
    fun update(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody input: UpdateMemberInput
    ) {
        service.update(principal.id, input)
    }

    @DeleteMapping("/me")
    fun delete(@AuthenticationPrincipal principal: MemberPrincipal) {
        service.delete(principal.id)
    }
}