package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BasePersistable
import java.util.UUID
import javax.persistence.Entity

@Entity
class NostalgiaReport(
    val reason: String,
    val reporterId: UUID,
    val nostalgiaId: UUID
) : BasePersistable()