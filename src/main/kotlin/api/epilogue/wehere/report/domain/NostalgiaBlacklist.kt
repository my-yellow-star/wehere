package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BasePersistable
import java.util.UUID
import javax.persistence.Entity

@Entity
class NostalgiaBlacklist(
    val memberId: UUID,
    val targetId: UUID
) : BasePersistable()