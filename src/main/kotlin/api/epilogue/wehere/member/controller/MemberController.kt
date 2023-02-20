package api.epilogue.wehere.member.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.client.PageRequest
import api.epilogue.wehere.member.application.GetBookmarksUseCase
import api.epilogue.wehere.member.application.MemberGetter
import api.epilogue.wehere.member.application.MemberService
import api.epilogue.wehere.member.application.UpdateMemberInput
import api.epilogue.wehere.nostalgia.domain.Location
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
    private val service: MemberService,
    private val getBookmarksUseCase: GetBookmarksUseCase
) {
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal principal: MemberPrincipal) =
        getter.get(principal.id)

    @GetMapping("/me/bookmarks")
    fun getBookmarks(
        @AuthenticationPrincipal principal: MemberPrincipal,
        pageRequest: PageRequest,
        current: Location
    ) =
        getBookmarksUseCase.execute(principal.id, principal.id, current, pageRequest.toPageable())

    @GetMapping("/{memberId}")
    fun getOther(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable memberId: UUID
    ) =
        getter.getOther(principal.id, memberId)

    @GetMapping("/{memberId}/bookmarks")
    fun getBookmarksOther(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @PathVariable memberId: UUID,
        pageRequest: PageRequest,
        current: Location
    ) =
        getBookmarksUseCase.execute(principal.id, memberId, current, pageRequest.toPageable())

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