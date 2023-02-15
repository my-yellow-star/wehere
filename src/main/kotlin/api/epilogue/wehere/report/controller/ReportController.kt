package api.epilogue.wehere.report.controller

import api.epilogue.wehere.auth.domain.MemberPrincipal
import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.report.application.ReportService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/report")
class ReportController(
    private val reportService: ReportService
) {
    @PostMapping
    fun report(
        @AuthenticationPrincipal principal: MemberPrincipal,
        @RequestBody input: ReportInput
    ) {
        when {
            input.memberId != null -> reportService.createMember(principal.id, input.memberId, input.reason)
            input.nostalgiaId != null -> reportService.createNostalgia(principal.id, input.nostalgiaId, input.reason)
            else -> throw ApiError(ErrorCause.INVALID_REQUEST)
        }
    }
}