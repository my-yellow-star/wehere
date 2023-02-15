package api.epilogue.wehere.report.controller

import java.util.UUID

data class BlacklistInput(
    val memberId: UUID?,
    val nostalgiaId: UUID?
)