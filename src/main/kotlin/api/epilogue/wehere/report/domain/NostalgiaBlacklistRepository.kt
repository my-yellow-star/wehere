package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BaseRepository
import java.util.UUID

interface NostalgiaBlacklistRepository : BaseRepository<NostalgiaBlacklist> {
    fun findByMemberIdAndNostalgiaId(memberId: UUID, nostalgiaId: UUID): NostalgiaBlacklist?
}