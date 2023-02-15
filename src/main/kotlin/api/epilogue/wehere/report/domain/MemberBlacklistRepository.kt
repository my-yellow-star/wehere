package api.epilogue.wehere.report.domain

import api.epilogue.wehere.kernel.BaseRepository
import java.util.UUID

interface MemberBlacklistRepository : BaseRepository<MemberBlacklist> {
    fun findByMemberIdAndTargetId(memberId: UUID, targetId: UUID): MemberBlacklist?
}