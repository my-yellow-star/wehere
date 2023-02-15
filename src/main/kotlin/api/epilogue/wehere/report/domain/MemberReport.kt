package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BasePersistable
import java.util.UUID
import javax.persistence.Entity

@Entity
class MemberReport(
    val reason: String,
    val reporterId: UUID,
    val memberId: UUID
) : BasePersistable()