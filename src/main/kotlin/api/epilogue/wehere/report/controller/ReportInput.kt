package api.epilogue.wehere.report.controller

import java.util.UUID

data class ReportInput(
    val memberId: UUID?,
    val nostalgiaId: UUID?,
    val reason: String
)