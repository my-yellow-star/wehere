package api.epilogue.wehere.nostalgia.domain

import api.epilogue.wehere.kernel.BaseRepository
import java.util.UUID

interface NostalgiaBookmarkRepository : BaseRepository<NostalgiaBookmark> {
    fun findByMemberIdAndNostalgiaId(memberId: UUID, nostalgiaId: UUID): NostalgiaBookmark?
}