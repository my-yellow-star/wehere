package api.epilogue.wehere.report.application

import api.epilogue.wehere.error.ApiError
import api.epilogue.wehere.error.ErrorCause
import api.epilogue.wehere.report.domain.MemberReport
import api.epilogue.wehere.report.domain.MemberReportRepository
import api.epilogue.wehere.report.domain.NostalgiaReport
import api.epilogue.wehere.report.domain.NostalgiaReportRepository
import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReportService(
    private val nostalgiaReportRepository: NostalgiaReportRepository,
    private val memberReportRepository: MemberReportRepository
) {
    @Transactional
    fun createMember(reporterId: UUID, memberId: UUID, reason: String) {
        if (reporterId == memberId)
            throw ApiError(ErrorCause.INVALID_REQUEST)
        memberReportRepository.save(
            MemberReport(reason, reporterId, memberId)
        )
    }

    @Transactional
    fun createNostalgia(reporterId: UUID, nostalgiaId: UUID, reason: String) {
        nostalgiaReportRepository.save(
            NostalgiaReport(reason, reporterId, nostalgiaId)
        )
    }
}