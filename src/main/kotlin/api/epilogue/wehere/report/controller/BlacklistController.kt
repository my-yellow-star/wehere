package api.epilogue.wehere.report.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.report.application.BlacklistService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blacklists")
class BlacklistController(
    private val blacklistService: BlacklistService
) {
    @PostMapping
    fun create(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody input: BlacklistInput
    ) {
        when {
            input.memberId != null -> blacklistService.createMember(principal.id, input.memberId)
            input.nostalgiaId != null -> blacklistService.createNostalgia(principal.id, input.nostalgiaId)
            else -> throw ApiError(ErrorCause.INVALID_REQUEST)
        }
    }

    @DeleteMapping
    fun delete(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody input: BlacklistInput
    ) {
        when {
            input.memberId != null -> blacklistService.deleteMember(principal.id, input.memberId)
            input.nostalgiaId != null -> blacklistService.deleteNostalgia(principal.id, input.nostalgiaId)
            else -> throw ApiError(ErrorCause.INVALID_REQUEST)
        }
    }
}