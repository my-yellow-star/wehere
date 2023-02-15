package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BasePersistable
import api.epilogue.wehere.nostalgia.domain.Nostalgia
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class NostalgiaBlacklist(
    val memberId: UUID,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId")
    val nostalgia: Nostalgia
) : BasePersistable()